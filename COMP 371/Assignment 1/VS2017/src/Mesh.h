#pragma once

#include "ColoredVertex.h"
#include "Shader.h"

#include <vector>
#include <glm/glm.hpp>

class Mesh
{
public:
	Mesh(coloredVertex* _shape, int nbOfVrtx, glm::vec3 _color);
	~Mesh();

	void draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP);
	void draw(Shader sh, unsigned int type, int start, int count, glm::mat4 MVP, glm::vec3 colorOverride);

private:
	coloredVertex* shape;
	int size;
	glm::vec3 color;
	unsigned int VAO;
	unsigned int VBO;

	void initVAO();
	void Bind();
	void Unbind();
};

class MeshEBO 
{
public:
	MeshEBO(std::vector<coloredVertex> _vertices, std::vector<unsigned int> _indices);
	~MeshEBO();

	std::vector<coloredVertex> vertices;
	std::vector<unsigned int> indices;

	void Draw(Shader* sh, glm::mat4 Model, glm::vec3 color = glm::vec3(1.f, 1.f, 1.f));
	void changeType(unsigned int newType);
private:
	unsigned int VBO;
	unsigned int VAO;
	unsigned int EBO;

	unsigned int type;

	glm::vec3 color;

	void initVAO();
	void Bind();
	void Unbind();
};

