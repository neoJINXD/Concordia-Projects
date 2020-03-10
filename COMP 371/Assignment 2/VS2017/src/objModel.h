#pragma once

#include "Shader.h"

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>


#include <cstring>
#include <vector>
#include <string>
#include <stdio.h>
#include <stdlib.h>


class objModel
{
private:
	void loadObj(const char* path);

	std::vector<glm::vec3> out_vertices;
	std::vector<glm::vec3> out_normals;
	std::vector<glm::vec2> out_uvs;

	unsigned int VAO;
	unsigned int vertices_VBO;
	unsigned int normals_VBO;
	unsigned int uvs_VBO;

	int vertexCount;

	glm::vec3 color;

	void Bind();
	void Unbind();
	void init();

public:
	objModel(string path, glm::vec3 _color = glm::vec3(1.f));
	~objModel();


	void draw(Shader* sh, unsigned int type);

};

