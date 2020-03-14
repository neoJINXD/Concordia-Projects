#include "Mesh.h"
#include <iostream>
#include <algorithm>

Mesh::Mesh(coloredVertex* _shape, int _size, glm::vec3 _color)
{
	shape = _shape;
	size = _size;
	color = _color;

	initVAO();
}

Mesh::~Mesh()
{
	shape = nullptr;
}

void Mesh::Bind()
{
	glBindVertexArray(VAO);
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
}

void Mesh::Unbind()
{
	glBindVertexArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glUseProgram(0);
}

void Mesh::draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP)
{
	sh.use();

	sh.setVec4("uColor", glm::vec4(color, 1.0f));

	this->Bind();

	sh.setMat4("worldMatrix", MVP);

	//draw
	glDrawArrays(type, start, count);

	//clean
	this->Unbind();
}

void Mesh::draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP, glm::vec3 colorOverride)
{
	sh.use();

	sh.setVec4("uColor", glm::vec4(colorOverride, 1.0f));

	this->Bind();

	sh.setMat4("worldMatrix", MVP);

	//draw
	glDrawArrays(type, start, count);

	//clean
	this->Unbind();
}

void Mesh::initVAO()
{
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);

	glGenBuffers(1, &VBO);
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, size, shape, GL_STATIC_DRAW);

	glVertexAttribPointer(0,
		3,
		GL_FLOAT,
		GL_FALSE,
		sizeof(coloredVertex),
		(void*)0
	);
	glEnableVertexAttribArray(0);

	glVertexAttribPointer(1,
		3,
		GL_FLOAT,
		GL_FALSE,
		sizeof(coloredVertex),
		(void*)sizeof(glm::vec3)
	);
	glEnableVertexAttribArray(1);

}



MeshEBO::MeshEBO(
	std::vector<coloredVertex> _vertices,
	std::vector<unsigned int> _indices,
	glm::vec3 _position,
	glm::vec3 _rotation,
	glm::vec3 _scale,
	glm::vec3 _origin,
	glm::vec3 _color
)
{
	for (size_t i = 0; i < _vertices.size(); i++) {
		vertices.push_back(_vertices[i]);
	}
	
	for (size_t i = 0; i < _indices.size(); i++) {
		indices.push_back(_indices[i]);
	}

	type = GL_TRIANGLES;

	position = _position;
	rotation = _rotation;
	scale = _scale;
	origin = _origin;
	color = _color;


	initVAO();
}

MeshEBO::~MeshEBO()
{
	glDeleteVertexArrays(1, &VAO);
	glDeleteBuffers(1, &VBO);
	glDeleteBuffers(1, &EBO);
	
}

void MeshEBO::Draw(Shader* sh)
{
	sh->use();

	sh->setVec4("uColor", glm::vec4(color, 1.0f));

	updateModelMatrix();

	sh->setMat4("worldMatrix", ModelMatrix);

	this->Bind();
	//draw
	glDrawElements(type, indices.size(), GL_UNSIGNED_INT, 0);

	//clean
	this->Unbind();

	for (auto* i : children) {
		i->Draw(sh);
	}
}

void MeshEBO::changeType(unsigned int newType)
{
	type = newType;
	for (auto* i : children) {
		i->changeType(newType);
	}
}

void MeshEBO::initVAO()
{
	// VAO
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);
	// VBO
	glGenBuffers(1, &VBO);
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(coloredVertex), &vertices[0], GL_STATIC_DRAW);
	// EBO
	glGenBuffers(1, &EBO);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.size() * sizeof(unsigned int), &indices[0], GL_STATIC_DRAW);

	// vertices
	glVertexAttribPointer(0,
		3,
		GL_FLOAT,
		GL_FALSE,
		sizeof(coloredVertex),
		(void*)0
	);
	glEnableVertexAttribArray(0);

	// normals
	glVertexAttribPointer(1,
		3,
		GL_FLOAT,
		GL_FALSE,
		sizeof(coloredVertex),
		(void*)sizeof(glm::vec3)
	);
	glEnableVertexAttribArray(1);

	// unbind the mesh by default
	glBindVertexArray(0);

}

void MeshEBO::Bind()
{
	glBindVertexArray(VAO);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
}

void MeshEBO::Unbind()
{
	glBindVertexArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glUseProgram(0);
}


void MeshEBO::updateModelMatrix() {
	//resetting the model matrix
	ModelMatrix = glm::mat4(1.f);

	//applying offset for where to rotate from
	ModelMatrix = glm::translate(ModelMatrix, origin);

	//applying the rotations
	ModelMatrix = glm::rotate(ModelMatrix, glm::radians(rotation.x), glm::vec3(1.f, 0.f, 0.f));
	ModelMatrix = glm::rotate(ModelMatrix, glm::radians(rotation.y), glm::vec3(0.f, 1.f, 0.f));
	ModelMatrix = glm::rotate(ModelMatrix, glm::radians(rotation.z), glm::vec3(0.f, 0.f, 1.f));

	//translating the mesh
	ModelMatrix = glm::translate(ModelMatrix, position - origin);

	//scaling the mesh
	ModelMatrix = glm::scale(ModelMatrix, scale);
}


void MeshEBO::rotate(float x, float y, float z) {
	rotation.x += x;
	rotation.y += y;
	rotation.z += z;
	for (auto* i : children) {
		//i->oldOrigin = i->origin;
		//i->origin = this->origin - i->position;
		i->rotate(x, y, z);
		//i->origin = i->oldOrigin;
	}

}

void MeshEBO::scaleUpDown(float x) {
	scale.x += x;
	scale.y += x;
	scale.z += x;
	/*if (scale.x < 0.f) {
		scale.x = 0.f;
	}
	if (scale.y < 0.f) {
		scale.y = 0.f;
	}
	if (scale.z < 0.f) {
		scale.z = 0.f;
	}*/
	for (auto* i : children) {
		i->scaleUpDown(x);
		glm::vec3 normalized = glm::normalize(i->position);
		i->absMoveBy(2 * x * normalized.x, 2 * x * normalized.y, 2 * x * normalized.z);
	}
	
}

void MeshEBO::absMoveBy(float x, float y, float z) {
	position.x += x;
	position.y += y;
	position.z += z;

}

void MeshEBO::moveBy(float x, float y, float z) {
	position.x += x;
	position.y += y;
	position.z += z;

	
	//parents position is the rotation point
	origin = position;
	for (auto* i : children) {
		i->moveBy(x, y, z);
		i->origin = this->position;
	}
}

void MeshEBO::addChild(MeshEBO* child) {
	child->origin = this->position;
	children.push_back(child);
	//child->parent = this;
}

void MeshEBO::randomizePos() {
	float newX = 0;
	do
	{
		newX = (std::rand() % 100) - 50;
	} while (position.x + newX > 50 || position.x + newX < -50);
	
	float newZ = 0;
	do
	{
		newZ = (std::rand() % 100) - 50;
	} while (position.z + newZ > 50 || position.z + newZ < -50);

	//std::cout << "new x: " << newX << " new z: " << newZ << std::endl;
	moveBy(newX, position.y, newZ);
}

glm::vec3 MeshEBO::getRotation() {
	return rotation;
}
glm::vec3 MeshEBO::getPosition() {
	return position;
}