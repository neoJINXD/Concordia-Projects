#pragma once

#include "ColoredVertex.h"
#include "Shader.h"

#include <vector>

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>

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
	MeshEBO(std::vector<coloredVertex> _vertices, 
			std::vector<unsigned int> _indices, 
			glm::vec3 _position = glm::vec3(0.f), 
			glm::vec3 _rotation = glm::vec3(0.f), 
			glm::vec3 _scale = glm::vec3(1.f),
			glm::vec3 _origin = glm::vec3(0.f),
			glm::vec3 _color = glm::vec3(1.f)
			);
	~MeshEBO();


	void Draw(Shader* sh);
	void changeType(unsigned int newType);
	void rotate(float x, float y, float z);
	void randomizePos();
	void moveBy(float x, float y, float z);
	void scaleUpDown(float x);

	void addChild(MeshEBO* child);

private:
	std::vector<MeshEBO*> children;

	std::vector<coloredVertex> vertices;
	std::vector<unsigned int> indices;

	unsigned int VBO;
	unsigned int VAO;
	unsigned int EBO;

	unsigned int type;

	glm::vec3 color;

	glm::vec3 origin;	// where the current mesh is connected to parent
	glm::vec3 position; // Translate by how much
	glm::vec3 rotation; // Rotate by how much based on each axis
	glm::vec3 scale;    // Scale by how much per axis

	glm::mat4 ModelMatrix;

	void initVAO();
	void Bind();
	void Unbind();
	void updateModelMatrix();
};

