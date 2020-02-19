#include "Mesh.h"
#include <GL/glew.h>
#include <iostream>

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
	glUseProgram(sh.shaderProgram);

	int colorInfo = glGetUniformLocation(sh.shaderProgram, "uColor");
	glUniform4f(colorInfo, color.x, color.y, color.z, 1.0f);

	this->Bind();

	unsigned int worldMatrixLocation = glGetUniformLocation(sh.shaderProgram, "worldMatrix");

	//draw
	glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &MVP[0][0]);
	glDrawArrays(type, start, count);

	//clean
	this->Unbind();
}

void Mesh::draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP, glm::vec3 colorOverride)
{
	glUseProgram(sh.shaderProgram);

	int colorInfo = glGetUniformLocation(sh.shaderProgram, "uColor");
	glUniform4f(colorInfo, colorOverride.x, colorOverride.y, colorOverride.z, 1.0f);

	this->Bind();

	unsigned int worldMatrixLocation = glGetUniformLocation(sh.shaderProgram, "worldMatrix");

	//draw
	glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &MVP[0][0]);
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



//TODO maybe have model matrix be made in here


//should pass nb of vertices and indices to create local copy
// or set it up as pointer
MeshEBO::MeshEBO(std::vector<coloredVertex> _vertices, std::vector<unsigned int> _indices, MeshEBO* _parent, glm::vec3 _position, glm::vec3 _rotation, glm::vec3 _scale, glm::vec3 _conection)
{
	for (size_t i = 0; i < _vertices.size(); i++) {
		vertices.push_back(_vertices[i]);
	}
	
	for (size_t i = 0; i < _indices.size(); i++) {
		indices.push_back(_indices[i]);
	}

	//vertices = _vertices;
	//indices = _indices;
	type = GL_TRIANGLES;

	parent = _parent;
	position = _position;
	rotation = _rotation;
	scale = _scale;

	//if root, there is no offset
	if (!parent) {
		conection = glm::vec3(0.f);
	}
	else {
		position = parent->position;
		conection = _conection;
		position += conection;
	}

	initVAO();
	//updateModelMatrix();
}

MeshEBO::~MeshEBO()
{
	glDeleteVertexArrays(1, &VAO);
	glDeleteBuffers(1, &VBO);
	glDeleteBuffers(1, &EBO);
	if (!parent) {
		delete parent;
		parent = nullptr;
	}
}

void MeshEBO::Draw(Shader* sh, glm::vec3 color)
{

	//if (!parent){
	//	apply, movement from root
	//}

	glUseProgram(sh->shaderProgram);

	int colorInfo = glGetUniformLocation(sh->shaderProgram, "uColor");
	glUniform4f(colorInfo, color.x, color.y, color.z, 1.0f);

	updateModelMatrix();
	this->Bind();

	unsigned int worldMatrixLocation = glGetUniformLocation(sh->shaderProgram, "worldMatrix");

	//draw
	glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &ModelMatrix[0][0]);
	glDrawElements(type, indices.size(), GL_UNSIGNED_INT, 0);


	//clean
	this->Unbind();

}

void MeshEBO::changeType(unsigned int newType)
{
	type = newType;

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
	//apply offset for where to rotate from, doesnt do anything if the mesh is the root
	glm::mat4 OffsetMatrix = glm::translate(glm::mat4(1.f), conection);
	//std::cout << conection.x << std::endl;

	//rotate on all the axis
	glm::mat4 RotationXMatrix = glm::rotate(glm::mat4(1.f), glm::radians(rotation.x), glm::vec3(1.f, 0.f, 0.f));
	glm::mat4 RotationYMatrix = glm::rotate(glm::mat4(1.f), glm::radians(rotation.y), glm::vec3(0.f, 1.f, 0.f));
	glm::mat4 RotationZMatrix = glm::rotate(glm::mat4(1.f), glm::radians(rotation.z), glm::vec3(0.f, 0.f, 1.f));

	//apply translation for position of the mesh
	glm::mat4 TranslateMatrix = glm::translate(glm::mat4(1.f), position-conection);

	//apply scale transformations
	glm::mat4 ScaleMatrix = glm::scale(glm::mat4(1.f), scale);

	ModelMatrix = TranslateMatrix * RotationZMatrix * RotationYMatrix * RotationXMatrix * OffsetMatrix * ScaleMatrix;


	////resetting the model matrix
	//ModelMatrix = glm::mat4(1.f);

	////apply offset for where to rotate from, doesnt do anything if the mesh is the root
	//ModelMatrix = glm::translate(ModelMatrix, conection);

	////rotate on all the axis
	//ModelMatrix = glm::rotate(ModelMatrix, glm::radians(rotation.x), glm::vec3(1.f, 0.f, 0.f));
	//ModelMatrix = glm::rotate(ModelMatrix, glm::radians(rotation.y), glm::vec3(0.f, 1.f, 0.f));
	//ModelMatrix = glm::rotate(ModelMatrix, glm::radians(rotation.z), glm::vec3(0.f, 0.f, 1.f));

	////apply translation for position of the mesh
	//ModelMatrix = glm::translate(ModelMatrix, position);

	////apply scale transformations
	//ModelMatrix = glm::scale(ModelMatrix, scale);


}


void MeshEBO::rotate(float x, float y, float z) {
	rotation.x += x;
	rotation.y += y;
	rotation.z += z;
}

void MeshEBO::scaleUp(float x, float y, float z) {
	scale.x += x;
	scale.y += y;
	scale.z += z;
	//needs to fix position of childs
}

void MeshEBO::moveBy(float x, float y, float z) {
	position.x += x;
	position.y += y;
	position.z += z;
}