#include "Model.h"
#include <GL/glew.h>


Model::Model(coloredVertex* _shape, int _size, glm::vec3 _color)
{
	shape = _shape;
	size = _size;
	color = _color;

	initVAO();
}

Model::~Model()
{
}

void Model::Bind()
{
	glBindVertexArray(VAO);
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
}

void Model::Unbind()
{
	glBindVertexArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glUseProgram(0);
}

void Model::draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP)
{
	glUseProgram(sh.shaderProgram);

	int colorInfo = glGetUniformLocation(sh.shaderProgram, "uColor");
	glUniform4f(colorInfo, color.x, color.y, color.z, 1.0f);

	this->Bind();

	GLuint worldMatrixLocation = glGetUniformLocation(sh.shaderProgram, "worldMatrix");

	//draw
	glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &MVP[0][0]);
	glDrawArrays(type, start, count);

	//clean
	this->Unbind();
}

void Model::draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP, glm::vec3 colorOverride)
{
	glUseProgram(sh.shaderProgram);

	int colorInfo = glGetUniformLocation(sh.shaderProgram, "uColor");
	glUniform4f(colorInfo, colorOverride.x, colorOverride.y, colorOverride.z, 1.0f);

	this->Bind();

	GLuint worldMatrixLocation = glGetUniformLocation(sh.shaderProgram, "worldMatrix");

	//draw
	glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &MVP[0][0]);
	glDrawArrays(type, start, count);

	//clean
	this->Unbind();
}

void Model::initVAO()
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
