#include <iostream>

#include "Shader.h"
#include "Camera.h"

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif

//TODO replace all GLuint to unsigned int
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>

struct coloredVertex {
	coloredVertex(glm::vec3 _position, glm::vec3 _color)
		: position(_position), color(_color) {}

	glm::vec3 position;
	glm::vec3 color;
};

int createVBOVertex() { // making the squares
	coloredVertex vertexArray[] = {
		coloredVertex(glm::vec3(), glm::vec3(0.0f, 0.0f, 0.0f)),
		coloredVertex(glm::vec3(), glm::vec3(0.0f, 0.0f, 0.0f)),
		coloredVertex(glm::vec3(), glm::vec3(0.0f, 0.0f, 0.0f)),

		coloredVertex(glm::vec3(), glm::vec3(0.0f, 0.0f, 0.0f)),
		coloredVertex(glm::vec3(), glm::vec3(0.0f, 0.0f, 0.0f)),
		coloredVertex(glm::vec3(), glm::vec3(0.0f, 0.0f, 0.0f))
	};

	unsigned int vbo;

	return vbo;
}

// TODO Split to other file, setup as vertex structs
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
	glBufferData(GL_ARRAY_BUFFER, 6 * sizeof(glm::vec3), vertexArray, GL_STATIC_DRAW);

	glVertexAttribPointer(0,                    // attribute 0 matches aPos in Vertex Shader
		3,										// size, dimmenssion of vector
		GL_FLOAT,								// type
		GL_FALSE,								// normalized? - floats are already normalized - eg colour going from 1-255 to 0-1
		2 * sizeof(glm::vec3),					// stride - each vertex contain 2 vec3 (position, color) - amount of bytes between each vertex
		(void*)0								// array buffer offset - offset for starting point
	);
	glEnableVertexAttribArray(0);


	glVertexAttribPointer(1,                    // attribute 1 matches aColor in Vertex Shader
		3,
		GL_FLOAT,
		GL_FALSE,
		2 * sizeof(glm::vec3),
		(void*)sizeof(glm::vec3)				// color is offseted a vec3 (comes after position)
	);
	glEnableVertexAttribArray(1);


	return vertexBufferObject;
}

int main() {
	std::cout << "yes" << std::endl;

	glfwInit();
	/*glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);*/

	// Creating GLFW window
	GLFWwindow* win = glfwCreateWindow(1024, 768, "COMP371 - Assignment 1", NULL, NULL);
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

	//std::cout << glGetString(GL_VERSION) << std::endl;

	Shader sh("assets/shaders/vertexShader.glsl", "assets/shaders/fragShader.glsl");

	int vbo = createVertexBufferObject();

	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	
	// set up Camera class with starting point
	
	float spd = 1.0f;
	
	glm::vec3 Eye = glm::vec3(0.0f, 0.0f, 10.0f);
	glm::vec3 Center = glm::vec3(0.0f, 0.0f, -1.0f);
	glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);

	Camera cam(&Eye, &Center, &Up, &spd, PERSPECTIVE, win);

	
	float lastFrameTime = glfwGetTime();
	//double oldMousePosX, oldMousePosY;
	//glfwGetCursorPos(win, &oldMousePosX, &oldMousePosY);
	glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	
	while (!glfwWindowShouldClose(win))
	{
		float dt = glfwGetTime() - lastFrameTime;
		lastFrameTime += dt;

		glClear(GL_COLOR_BUFFER_BIT);

		// input processing
		cam.processMovement(win, dt);

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

		// performing view and projection transformations
		cam.updateView(sh, win, dt);

		// swap buffers
		glfwSwapBuffers(win);
		// check/call events
		glfwPollEvents();

		// escape to close window
		if (glfwGetKey(win, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
			glfwSetWindowShouldClose(win, true);
		}
	
	}

	glDeleteProgram(sh.shaderProgram);

	glfwTerminate();

	//Shader* sh = new Shader();
	//sh->printV();
	//delete sh;
	//sh = nullptr;

	return 0;
}