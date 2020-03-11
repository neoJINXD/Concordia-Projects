#include "Camera.h"

#include <iostream>
#include <algorithm>

#include <glm/gtc/matrix_transform.hpp>

Camera::Camera(glm::vec3* Eye, glm::vec3* Center, glm::vec3* Up, float* _speed, ProjectionType Type, GLFWwindow* win) {
	camEye = Eye;			// Position
	//camCenter = Center;		// Looking At Point
	camCenter = new glm::vec3(0.f);
	camUp = Up;				// Up
	speed = _speed;
	radius = 10.f;
	position = 0.f;
	angle = 70.f;

	if (Type == PERSPECTIVE) {
		// projection matrix to have camera in perspective
		projectionMatrix = glm::perspective(glm::radians(angle),  // field of view in degrees
			1280.0f / 720.0f,      // aspect ratio
			0.01f, 200.0f);       // near and far (near > 0)
	}
	else {
		// projection matrix to have camera in orthograpic TODO

	}


	glfwGetCursorPos(win, &oldMousePosX, &oldMousePosY);

	yaw = -90.0f;
	pitch = -45.0f;
}

Camera::~Camera() {
	camEye = nullptr;
	camCenter = nullptr;
	camUp = nullptr;
	speed = nullptr;
}

void Camera::processMovement(GLFWwindow* win, float deltaTime) {

	double mousePosX, mousePosY;
	glfwGetCursorPos(win, &mousePosX, &mousePosY);

	double dx = mousePosX - oldMousePosX;
	double dy = mousePosY - oldMousePosY;

	oldMousePosX = mousePosX;
	oldMousePosY = mousePosY;

	float sensitivity = 0.1f;
	dx *= sensitivity;
	dy *= -sensitivity;


	// Calcualting a speed normalized based on how much time has passed,
	// speed is no longer affected by fps
	//float normalizedSpeed = *speed * deltaTime;

	//if (glfwGetKey(win, GLFW_KEY_W) == GLFW_PRESS) {
	//	*camEye += *camCenter * normalizedSpeed;
	//}
	//if (glfwGetKey(win, GLFW_KEY_S) == GLFW_PRESS) {
	//	*camEye -= *camCenter * normalizedSpeed;
	//}
	if (glfwGetKey(win, GLFW_KEY_LEFT) == GLFW_PRESS) {
		position -= .1f;
		yaw += 5.73f;

	}
	if (glfwGetKey(win, GLFW_KEY_RIGHT) == GLFW_PRESS) {
		position += .1f;
		yaw -= 5.73f;
	}
	if (glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
		//zoom in and out, up-down
		if (dy < 0) {
			//up
			angle -= 1.f;
		}
		else if (dy > 0) {
			//down
			angle += 1.f;
		}
	}
	if (glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
		//rotate view left-right
		yaw += dx;
	}
	if (glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_MIDDLE) == GLFW_PRESS) {
		//rotate view up-down
		pitch += dy;

		pitch = std::max(-89.0f, std::min(89.0f, pitch));
	}


}

void Camera::updateView(Shader sh, GLFWwindow* win, float deltaTime) {

	// Projection Transform
	unsigned int projectionMatrixLocation = glGetUniformLocation(sh.shaderProgram, "projectionMatrix");
	projectionMatrix = glm::perspective(glm::radians(angle),  // field of view in degrees
		1024.0f / 768.0f,      // aspect ratio
		0.01f, 200.0f);       // near and far (near > 0)
	glUniformMatrix4fv(projectionMatrixLocation, 1, GL_FALSE, &projectionMatrix[0][0]);
	
	
	glUniform3f(glGetUniformLocation(sh.shaderProgram, "viewPos"), camEye->x, camEye->y, camEye->z);

	// View Transform - from camera movement
	unsigned int viewMatrixLocation = glGetUniformLocation(sh.shaderProgram, "viewMatrix");

	float camX = sin(position) * radius;
	float camZ = cos(position) * radius;

	*camCenter = glm::normalize(glm::vec3(
		cosf(glm::radians(yaw)) * cosf(glm::radians(pitch)),
		sinf(glm::radians(pitch)),
		sin(glm::radians(yaw)) * cos(glm::radians(pitch))
	));

	*camEye = glm::vec3(camX, 10.f, camZ);

	viewMatrix = glm::lookAt(
		*camEye,
		*camEye + *camCenter,
		*camUp
	);

	glUniformMatrix4fv(viewMatrixLocation, 1, GL_FALSE, &viewMatrix[0][0]);

}

void Camera::reset() {
	yaw = -90.0f;
	pitch = -45.0f;
	position = 0.f;
}