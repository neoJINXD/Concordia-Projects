#pragma once

#include "Shader.h"

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif


#include <GL/glew.h>

#include <GLFW/glfw3.h>
#include <glm/glm.hpp>

#ifndef _PROJECTION_TYPE
enum ProjectionType {
	PERSPECTIVE,
	ORTHOGRAPHIC
};
#endif

class Camera
{

public:
	Camera(glm::vec3* Eye, glm::vec3* Center, glm::vec3* Up, float* _speed, ProjectionType Type);
	~Camera();
	void processMovement(GLFWwindow* win, float deltaTime);

	glm::mat4 projectionMatrix;
	glm::mat4 viewMatrix;

	void updateView(Shader sh);

private:
	float* speed;
	glm::vec3* camEye; // position of the cam
	glm::vec3* camCenter; // where looking
	glm::vec3* camUp; // where up is


};

