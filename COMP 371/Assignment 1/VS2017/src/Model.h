#pragma once

#include "ColoredVertex.h"
#include "Shader.h"

#include <vector>
#include <glm/glm.hpp>

class Model
{
public:
	Model(coloredVertex* shape, glm::vec3 _position);
	~Model();

	void Bind();
	void Unbind();
	void draw(Shader sh, unsigned int type, int start, int count);
private:
	//std::vector<coloredVertex> shape;
	glm::vec3 position;
	unsigned int VAO;
	unsigned int VBO;
	coloredVertex* shape;
	void initVAO();

};

