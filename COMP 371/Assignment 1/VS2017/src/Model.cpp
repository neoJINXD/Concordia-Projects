#include "Model.h"
#include <GL/glew.h>

Model::Model(coloredVertex* _shape, glm::vec3 _position)
{
	position = _position;
	shape = _shape;

	this->initVAO();
}

Model::~Model()
{
}

void Model::Bind()
{
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
}

void Model::Unbind()
{
	glBindVertexArray(0);
	glUseProgram(0);
}

void Model::initVAO()
{
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO);

	glGenBuffers(1, &VBO);
	glBindBuffer(GL_ARRAY_BUFFER, VBO);
	glBufferData(GL_ARRAY_BUFFER, sizeof(shape), shape, GL_STATIC_DRAW);

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

void Model::draw(Shader sh, unsigned int type, int start, int count) {
	glUseProgram(sh.shaderProgram);

	this->Bind();

	//draw
	glDrawArrays(type, start, count);

	//clean
	this->Unbind();
}


