#include "Camera.h"


//#include <GL/glew.h>
#include <glm/gtc/matrix_transform.hpp>




Camera::Camera(glm::vec3* Eye, glm::vec3* Center, glm::vec3* Up, float* _speed, ProjectionType Type) {
	/*glm::vec3 Eye(0.0f, 0.0f, 10.0f);
	glm::vec3 Center(0.0f, 0.0f, -1.0f);
	glm::vec3 Up(0.0f, 1.0f, 0.0f);*/
	camEye = Eye;
	camCenter = Center;
	camUp = Up;
	speed = _speed;

	if (Type == PERSPECTIVE) {
		// projection matrix to have camera in perspective
		projectionMatrix = glm::perspective(glm::radians(45.0f),  // field of view in degrees
			1280.0f / 720.0f,      // aspect ratio
			0.01f, 100.0f);       // near and far (near > 0)
	}
	else {
		// projection matrix to have camera in orthograpic TODO

	}

}

Camera::~Camera() {
	camEye = nullptr;
	camCenter = nullptr;
	camUp = nullptr;

}

void Camera::processMovement(GLFWwindow* win, float deltaTime) {
	// escape to close window
	if (glfwGetKey(win, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
		glfwSetWindowShouldClose(win, true);
	}

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
		//std::cout << "Resetting position" << std::endl;

		*camEye = glm::vec3(0.0f, 0.0f, 10.0f);
		*camCenter = glm::vec3(0.0f, 0.0f, -1.0f);
		*camUp = glm::vec3(0.0f, 1.0f, 0.0f);
	}
}

void Camera::updateView(Shader sh) {
	// View Transform - from camera movement
	GLuint viewMatrixLocation = glGetUniformLocation(sh.shaderProgram, "viewMatrix");
	viewMatrix = glm::lookAt(*camEye, *camEye + *camCenter, *camUp);
	glUniformMatrix4fv(viewMatrixLocation, 1, GL_FALSE, &viewMatrix[0][0]);

	// Projection Transform
	GLuint projectionMatrixLocation = glGetUniformLocation(sh.shaderProgram, "projectionMatrix");
	glUniformMatrix4fv(projectionMatrixLocation, 1, GL_FALSE, &projectionMatrix[0][0]);
}