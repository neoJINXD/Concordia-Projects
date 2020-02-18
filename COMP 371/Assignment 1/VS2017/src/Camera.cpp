#include "Camera.h"

#include <iostream>
#include <algorithm>

#include <glm/gtc/matrix_transform.hpp>

Camera::Camera(glm::vec3* Eye, glm::vec3* Center, glm::vec3* Up, float _pitch, float* _speed, ProjectionType Type, GLFWwindow* win) {
	camEye = Eye;			// Position
	camCenter = Center;		// Looking At Point
	camUp = Up;				// Up
	speed = _speed;

	if (Type == PERSPECTIVE) {
		// projection matrix to have camera in perspective
		projectionMatrix = glm::perspective(glm::radians(45.0f),  // field of view in degrees
			1280.0f / 720.0f,      // aspect ratio
			0.01f, 200.0f);       // near and far (near > 0)
	}
	else {
		// projection matrix to have camera in orthograpic TODO

	}


	glfwGetCursorPos(win, &oldMousePosX, &oldMousePosY);

	yaw = -90.0f;
	//pitch = 0.0f;
	pitch = _pitch;
}

Camera::~Camera() {
	camEye = nullptr;
	camCenter = nullptr;
	camUp = nullptr;
	speed = nullptr;
}

void Camera::processMovement(GLFWwindow* win, float deltaTime) {

	// Calcualting a speed normalized based on how much time has passed,
	// speed is no longer affected by fps
	float normalizedSpeed = *speed * deltaTime;
	if (glfwGetKey(win, GLFW_KEY_W) == GLFW_PRESS) {
		*camEye += *camCenter * normalizedSpeed;
	}
	if (glfwGetKey(win, GLFW_KEY_S) == GLFW_PRESS) {
		*camEye -= *camCenter * normalizedSpeed;
	}
	if (glfwGetKey(win, GLFW_KEY_A) == GLFW_PRESS) {
		glm::vec3 movement = glm::normalize(glm::cross(*camCenter, *camUp)) * normalizedSpeed;
		*camEye -= movement;
	}
	if (glfwGetKey(win, GLFW_KEY_D) == GLFW_PRESS) {
		glm::vec3 movement = glm::normalize(glm::cross(*camCenter, *camUp)) * normalizedSpeed;
		*camEye += movement;
	}
	if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
		// Increasing speed
		*speed = 10.0f;
	}
	if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_RELEASE) {
		// Returning to normal speed
		*speed = 1.0f;
	}

	// by default, camera is centered at the origin and look towards negative z-axis
	if (glfwGetKey(win, GLFW_KEY_1) == GLFW_PRESS)
	{
		std::cout << "Resetting position" << std::endl;

		*camEye = glm::vec3(0.0f, 0.0f, 10.0f);
		*camCenter = glm::vec3(0.0f, 0.0f, 0.0f);
		*camUp = glm::vec3(0.0f, 1.0f, 0.0f);
	}

	// mouse movement handling
	//float dx = mousePosX - oldMousePosX;
	//float dy = mousePosY - oldMousePosY;
	//if (dx != 0 || dy != 0) {

	//	if (dx < 0) {
	//		//std::cout << "Left" << std::endl;
	//	}
	//	else {
	//		//std::cout << "Right" << std::endl;
	//	}
	//	if (dy < 0) {
	//		//std::cout << "Up" << std::endl;
	//	}
	//	else {
	//		//std::cout << "Down" << std::endl;
	//	}
	//}

}

void Camera::updateView(Shader sh, GLFWwindow* win, float deltaTime) {
	
	double mousePosX, mousePosY;
	glfwGetCursorPos(win, &mousePosX, &mousePosY);

	double dx = mousePosX - oldMousePosX;
	double dy = mousePosY - oldMousePosY;

	oldMousePosX = mousePosX;
	oldMousePosY = mousePosY;

	float sensitivity = 0.1f;
	dx *= sensitivity;
	dy *= -sensitivity;

	yaw += dx;
	pitch += dy;

	pitch = std::max(-89.0f, std::min(89.0f, pitch));

	*camCenter = glm::normalize(glm::vec3(
		cosf(glm::radians(yaw)) * cosf(glm::radians(pitch)), 
		sinf(glm::radians(pitch)), 
		sin(glm::radians(yaw)) * cos(glm::radians(pitch))
	));

	// Projection Transform
	unsigned int projectionMatrixLocation = glGetUniformLocation(sh.shaderProgram, "projectionMatrix");
	glUniformMatrix4fv(projectionMatrixLocation, 1, GL_FALSE, &projectionMatrix[0][0]);
	// View Transform - from camera movement
	// TODO fix cam to look at 0,0,0 and rotate world
	unsigned int viewMatrixLocation = glGetUniformLocation(sh.shaderProgram, "viewMatrix");
	/*viewMatrix = glm::lookAt(
		*camEye,
		glm::vec3(0.0f, 0.0f, 0.0f),
		*camUp
	);*/
	viewMatrix = glm::lookAt(
		*camEye,
		*camEye + *camCenter,
		*camUp
	);
	glUniformMatrix4fv(viewMatrixLocation, 1, GL_FALSE, &viewMatrix[0][0]);

}