#pragma once

#include "ColoredVertex.h"
#include "Shader.h"

#include <vector>
#include <glm/glm.hpp>

class Model
{
public:
	Model(coloredVertex* _shape, int nbOfVrtx, glm::vec3 _color);
	~Model();

	void Bind();
	void Unbind();
	void draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP);
	void draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP, glm::vec3 colorOverride);
private:
	//std::vector<coloredVertex> shape;
	//glm::vec3 position;
	unsigned int VAO;
	unsigned int VBO;
	coloredVertex* shape;
	void initVAO();
	int size;
	glm::vec3 color;

};

