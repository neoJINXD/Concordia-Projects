#pragma once

#include "ColoredVertex.h"
#include "Shader.h"

#include <vector>
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
			MeshEBO* _parent = nullptr,
			glm::vec3 _position = glm::vec3(0.f), 
			glm::vec3 _rotation = glm::vec3(0.f), 
			glm::vec3 _scale = glm::vec3(1.f),
			glm::vec3 _conection = glm::vec3(0.f)
			);
	~MeshEBO();


	void Draw(Shader* sh, glm::vec3 color = glm::vec3(1.f, 1.f, 1.f)); // TODO set up color 
	void changeType(unsigned int newType);
	void rotate(float x, float y, float z);
	void randomizePos();
	void moveBy(float x, float y, float z);
	void scaleUp(float x, float y, float z);
	void scaleDown();

private:
	MeshEBO* parent;//TODO setup parent

	std::vector<coloredVertex> vertices;
	std::vector<unsigned int> indices;
	//unsigned int nbVertices;//? dont think i need these 2
	//unsigned int nbIndices;//?

	unsigned int VBO;
	unsigned int VAO;
	unsigned int EBO;

	unsigned int type;

	glm::vec3 color;//TODO use this

	glm::vec3 conection;// where the current mesh is connected to parent
	glm::vec3 position; // Translate by how much
	glm::vec3 rotation; // Rotate by how much based on each axis
	glm::vec3 scale;    // Scale by how much per axis

	glm::mat4 ModelMatrix;

	void initVAO();
	void Bind();
	void Unbind();
	void updateModelMatrix(); // TODO
	//void transform(); //Applies model matrix?, or should i just apply it in draw

};

