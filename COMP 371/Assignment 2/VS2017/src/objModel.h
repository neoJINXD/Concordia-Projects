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
	void loadObj();


	void draw(Shader sh, unsigned int type);
public:
	objModel();
	~objModel();


	unsigned int VAO;
	unsigned int VBO;

	void Bind();
	void Unbind();

};

