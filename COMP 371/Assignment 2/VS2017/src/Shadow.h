#pragma once
#include "Shader.h"
#include "objModel.h"

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

class Shadow
{
private:
	//Shader* sh;
	unsigned int depthMapFBO;
	unsigned int depthMap;
	const unsigned int SHADOW_WIDTH = 1024, SHADOW_HEIGHT = 1024;

	void init();
public:
	Shadow();
	~Shadow();

	void Bind();
	void Unbind();
	void draw(Shader *sh, objModel* model);

};

