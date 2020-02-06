#include <iostream>

#include "Shader.h"

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif


#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>


// TODO Split to other file
void getInputs (GLFWwindow* win, float* speed, float deltaTime, glm::vec3* camEye, glm::vec3* camCenter, glm::vec3* camUp) {
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
		std::cout << "Resetting position" << std::endl;

		*camEye = glm::vec3(0.0f, 0.0f, 0.0f);
		*camCenter = glm::vec3(0.0f, 0.0f, -1.0f);
		*camUp = glm::vec3(0.0f, 1.0f, 0.0f);
	}
}

// TODO Split to other file
int createVertexBufferObject()
{
	// A vertex is a point on a polygon, it contains positions and other data (eg: colors)
	glm::vec3 vertexArray[] = {
		glm::vec3(0.0f,  0.5f, 0.03f),  // top center position
		glm::vec3(1.0f,  0.0f, 0.0f),  // top center color (red)
		glm::vec3(0.5f, -0.5f, 0.03f),  // bottom right
		glm::vec3(0.0f,  1.0f, 0.0f),  // bottom right color (green)
		glm::vec3(-0.5f, -0.5f, 0.03f),  // bottom left
		glm::vec3(0.0f,  0.0f, 1.0f),  // bottom left color (blue)
	};

	// Create a vertex array
	GLuint vertexArrayObject;
	glGenVertexArrays(1, &vertexArrayObject);
	glBindVertexArray(vertexArrayObject);


	// Upload Vertex Buffer to the GPU, keep a reference to it (vertexBufferObject)
	GLuint vertexBufferObject;
	glGenBuffers(1, &vertexBufferObject);
	glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertexArray), vertexArray, GL_STATIC_DRAW);

	glVertexAttribPointer(0,                   // attribute 0 matches aPos in Vertex Shader
		3,                   // size
		GL_FLOAT,            // type
		GL_FALSE,            // normalized?
		2 * sizeof(glm::vec3), // stride - each vertex contain 2 vec3 (position, color)
		(void*)0             // array buffer offset
	);
	glEnableVertexAttribArray(0);


	glVertexAttribPointer(1,                            // attribute 1 matches aColor in Vertex Shader
		3,
		GL_FLOAT,
		GL_FALSE,
		2 * sizeof(glm::vec3),
		(void*)sizeof(glm::vec3)      // color is offseted a vec3 (comes after position)
	);
	glEnableVertexAttribArray(1);


	return vertexBufferObject;
}

int main() {
	std::cout << "yes" << std::endl;

	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

	// Creating GLFW window
	GLFWwindow* win = glfwCreateWindow(1280, 720, "COMP371 - Assignment 1", NULL, NULL);
	if (win == NULL)
	{
		std::cout << "Failed to create GLFW window" << std::endl;
		glfwTerminate();
		return -1;
	}
	glfwMakeContextCurrent(win);

	// Initialize GLEW
	//glewExperimental = true; // TODO what this?

	if (glewInit() != GLEW_OK) {
		std::cerr << "Failed to create GLEW" << std::endl;
		glfwTerminate();
		return -1;
	}

	Shader sh("assets/shaders/vertexShader.glsl", "assets/shaders/fragShader.glsl");

	int vbo = createVertexBufferObject();

	glClearColor(0.2f,0.1f,0.4f,0.5f);

	

	// TODO set up Camera class with this
	
	float spd = 1.0f;
	float* speed = &spd;
	
	glm::vec3 Eye = glm::vec3(0.0f, 0.0f, 0.0f);
	glm::vec3 camDefaultTarget = glm::vec3(0.0f, 0.0f, 0.0f);
	glm::vec3 Center = glm::vec3(0.0f, 0.0f, -1.0f);
	glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);

	glm::vec3* camEye = &Eye;
	glm::vec3* camCenter = &Center;
	glm::vec3* camUp = &Up;

	// projection matrix to keep camera in perspective
	glm::mat4 projectionMatrix = glm::perspective(glm::radians(45.0f),  // field of view in degrees
		800.0f / 600.0f,      // aspect ratio
		0.01f, 100.0f);       // near and far (near > 0)
	
	float lastFrameTime = glfwGetTime();
	
	while (!glfwWindowShouldClose(win))
	{
		float dt = glfwGetTime() - lastFrameTime;
		lastFrameTime += dt;

		

		glClear(GL_COLOR_BUFFER_BIT);
		// input processing
		getInputs(win, speed, dt, camEye, camCenter, camUp);

		// rendering
		glUseProgram(sh.shaderProgram);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		GLuint worldMatrixLocation = glGetUniformLocation(sh.shaderProgram, "worldMatrix");
		// add 2 triangles
		// Top right triangle - translate by (0.5, 0.5, 0.5)
		// Scaling model by 0.25, notice negative value to flip Y axis
		glm::mat4 scalingMatrix = glm::scale(glm::mat4(1.0f), glm::vec3(0.25f, -0.250f, 0.25f));
		glm::mat4 translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(0.5f, 0.5f, 0.0f));

		glm::mat4 worldMatrix = translationMatrix * scalingMatrix;

		glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &worldMatrix[0][0]);
		glDrawArrays(GL_TRIANGLES, 0, 3);

		// Top left triangle - translate by (-0.5, 0.5, 0.5)
		translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(-0.5f, 0.5f, 0.0f));
		worldMatrix = translationMatrix * scalingMatrix;

		glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &worldMatrix[0][0]);
		glDrawArrays(GL_TRIANGLES, 0, 3);

		// View Transform - from camera movement
		GLuint viewMatrixLocation = glGetUniformLocation(sh.shaderProgram, "viewMatrix");
		glm::mat4 viewMatrix = glm::lookAt(*camEye, *camEye + *camCenter, *camUp);
		glUniformMatrix4fv(viewMatrixLocation, 1, GL_FALSE, &viewMatrix[0][0]);

		// Projection Transform
		GLuint projectionMatrixLocation = glGetUniformLocation(sh.shaderProgram, "projectionMatrix");
		glUniformMatrix4fv(projectionMatrixLocation, 1, GL_FALSE, &projectionMatrix[0][0]);

		// swap buffers
		glfwSwapBuffers(win);
		// check/call events
		glfwPollEvents();

		
		
	}

	glfwTerminate();

	//Shader* sh = new Shader();
	//sh->printV();
	//delete sh;
	//sh = nullptr;

	return 0;
}