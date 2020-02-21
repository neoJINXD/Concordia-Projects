#include <iostream>
#include <cmath>

#include "Shader.h"
#include "Camera.h"
#include "ColoredVertex.h"
#include "Mesh.h"

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif

#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>


//For error checking for error checking
#define ASSERT(x) if (!(x)) __debugbreak();
#define GLCall(x) GlClearError();\
	x;\
	ASSERT(glError(#x, __FILE__, __LINE__))

static void GlClearError() {
	while (glGetError() != 0);
}

static bool glError(const char* funct, const char* file, int line) {
	while (GLenum err = glGetError()) {
		std::cout << "[OpenGL Error]: " << err << funct << " " << file << " : " << line << ";" << std::endl;
		return false;
	}
	return true;
}


int main() {
	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

	// Creating GLFW window
	GLFWwindow* win = glfwCreateWindow(1024, 768, "COMP371 - Assignment 1", NULL, NULL);
	if (win == NULL)
	{
		std::cout << "Failed to create GLFW window" << std::endl;
		glfwTerminate();
		return -1;
	}
	glfwMakeContextCurrent(win);
	
	// VSYNC - Sync framerate with screen refresh rate - 60Hz
	glfwSwapInterval(1);

	// Initialize GLEW
	if (glewInit() != GLEW_OK) {
		std::cerr << "Failed to create GLEW" << std::endl;
		glfwTerminate();
		return -1;
	}

	// OpenGL Version Check
	//std::cout << glGetString(GL_VERSION) << std::endl;

	// Shader Creation
	Shader sh("assets/shaders/vertexShader.glsl", "assets/shaders/fragShader.glsl");



	// Arrays for the shapes used in rendering
	coloredVertex line[] = {
		coloredVertex(glm::vec3(-0.5f, 0.0f, 0.0f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, 0.0f, 0.0f), glm::vec3(1.0f, 1.0f, 1.0f)),
	};

	std::vector<coloredVertex> eboCube{
		coloredVertex(glm::vec3(-0.5f, -0.5f, 0.5f), glm::vec3(1.f)),
		coloredVertex(glm::vec3( 0.5f, -0.5f, 0.5f), glm::vec3(1.f)),
		coloredVertex(glm::vec3( 0.5f,  0.5f, 0.5f), glm::vec3(1.f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f, 0.5f), glm::vec3(1.f)),

		coloredVertex(glm::vec3(-0.5f, -0.5f,-0.5f), glm::vec3(1.f)),
		coloredVertex(glm::vec3( 0.5f, -0.5f,-0.5f), glm::vec3(1.f)),
		coloredVertex(glm::vec3( 0.5f,  0.5f,-0.5f), glm::vec3(1.f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f,-0.5f), glm::vec3(1.f)),
	};

	std::vector<unsigned int> eboCubeIndices{
		0, 1, 2,
		0, 2, 3,

		1, 5, 6,
		1, 6, 2,

		5, 4, 7,
		5, 7, 6,

		4, 0, 3,
		4, 3, 7,

		3, 2, 6,
		3, 6, 7,

		0, 5, 1,
		0, 4, 5, 
	};



	// Creating meshes
	// creating line
	Mesh _line(line, sizeof(line), glm::vec3(1.0f, 1.0f, 0.0f));

	// creating the snowman
	MeshEBO torso(eboCube, eboCubeIndices, glm::vec3(0.f, 0.f, 0.f), glm::vec3(0.f), glm::vec3(1.f));
	MeshEBO right(eboCube, eboCubeIndices, glm::vec3(1.f, 0.f, 0.f), glm::vec3(0.f), glm::vec3(1.f), glm::vec3(0.f), glm::vec3(1.f, 0.f, 0.f));
	MeshEBO rightright(eboCube, eboCubeIndices, glm::vec3(2.f, 0.f, 0.f), glm::vec3(0.f), glm::vec3(1.f), glm::vec3(0.f), glm::vec3(1.f, 0.f, 1.f));
	MeshEBO left(eboCube, eboCubeIndices, glm::vec3(-1.f, 0.f, 0.f), glm::vec3(0.f), glm::vec3(1.f), glm::vec3(0.f), glm::vec3(0.f, 1.f, 0.f));
	// creates the hierarchy
	torso.addChild(&right);
	torso.addChild(&left);
	right.addChild(&rightright);

	MeshEBO edge(eboCube, eboCubeIndices, glm::vec3(50.f, 0.f, 50.f), glm::vec3(0.f), glm::vec3(1.f));

	// Background Color
	glClearColor(0.11f, 0.44f, 0.68f, 1.0f);

	
	// Setting up Camera with starting point
	float spd = 1.0f;
	
	glm::vec3 Eye = glm::vec3(0.0f, 10.0f, 10.0f);
	glm::vec3 Center = glm::vec3(0.0f, 0.0f, 0.0f);
	glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);

	//Eventually removed, since no mouse movement for camera
	float pitch = acos(glm::dot(Center - Eye, glm::vec3(0.0f, 1.0f, 0.0f)) / (glm::length(Center - Eye) * glm::length(glm::vec3(0.0f, 1.0f, 0.0f))));

	Camera cam(&Eye, &Center, &Up, -pitch, &spd, PERSPECTIVE, win);

	
	float lastFrameTime = glfwGetTime();

	// Disabling mouse cursor
	glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	glEnable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);

	bool hasRandomized = false;

	while (!glfwWindowShouldClose(win))
	{
		glUseProgram(sh.shaderProgram);

		// calculating deltatime
		float dt = glfwGetTime() - lastFrameTime;
		lastFrameTime += dt;

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// camera's input processing
		cam.processMovement(win, dt);

		// Performing view and projection transformations for camera
		cam.updateView(sh, win, dt);

		// Rendering
		glm::mat4 scalingMatrix;
		glm::mat4 translationMatrix;
		glm::mat4 worldMatrix;
		unsigned int worldMatrixLocation = glGetUniformLocation(sh.shaderProgram, "worldMatrix");

		// Grid Lines
		scalingMatrix = glm::scale(glm::mat4(1.0f), glm::vec3(100.0f, 1.0f, 1.0f));

		glLineWidth(1);

		// Z-axis Lines
		for (int i = -50; i <= 50; i++) {
			translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, 0.0f, i * 1.0f));
			worldMatrix = translationMatrix * scalingMatrix;
			_line.draw(sh, GL_LINES, 0, 3, worldMatrix);
		}

		// X-axis Lines
		glm::mat4 rotation = glm::rotate(glm::mat4(1.0f), glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		for (int i = -50; i <= 50; i++) {
			translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(i * 1.0f, 0.0f, 0.0f));
			worldMatrix = translationMatrix * rotation * scalingMatrix;
			_line.draw(sh, GL_LINES, 0, 3, worldMatrix);
		}
		
		// Coordinate Axis Lines
		int scale = 5; // 5 Unit length
		glLineWidth(15);

		//X
		scalingMatrix = glm::scale(glm::mat4(1.0f), glm::vec3(scale * 1.0f, 1.1f, 1.1f));
		translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(scale * 0.5f, 0.0f, 0.0f));
		worldMatrix = translationMatrix * scalingMatrix;
		_line.draw(sh, GL_LINES, 0, 3, worldMatrix, glm::vec3(1.0f, 0.0f, 0.0f));

		//Y
		rotation = glm::rotate(glm::mat4(1.0f), glm::radians(90.0f), glm::vec3(0.0f, 0.0f, 1.0f));
		translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, scale * 0.5f, 0.0f));
		worldMatrix = translationMatrix * rotation * scalingMatrix;
		_line.draw(sh, GL_LINES, 0, 3, worldMatrix, glm::vec3(0.0f, 1.0f, 0.0f));

		//Z
		rotation = glm::rotate(glm::mat4(1.0f), glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, 0.0f, scale * 0.5f));
		worldMatrix = translationMatrix * rotation * scalingMatrix;
		_line.draw(sh, GL_LINES, 0, 3, worldMatrix, glm::vec3(0.0f, 0.0f, 1.0f));
		

		//Drawing snowman at origin
		glLineWidth(1);
		glPointSize(10);

		torso.Draw(&sh);
		edge.Draw(&sh);
		
		
		// Swap buffers
		glfwSwapBuffers(win);
		// Check/call events
		glfwPollEvents();

		//TODO setup as callback
		if (glfwGetKey(win, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
			// Escape to close window
			glfwSetWindowShouldClose(win, true);
		}
		if (glfwGetKey(win, GLFW_KEY_L) == GLFW_PRESS) {
			// Wireframe with GL_LINE_LOOP
			torso.changeType(GL_LINE_LOOP);
		}
		if (glfwGetKey(win, GLFW_KEY_T) == GLFW_PRESS) {
			// Shape with GL_TRIANGLES
			torso.changeType(GL_TRIANGLES);
		}
		if (glfwGetKey(win, GLFW_KEY_P) == GLFW_PRESS) {
			// Points with GL_POINTS
			torso.changeType(GL_POINTS);
		}
		if (glfwGetKey(win, GLFW_KEY_D) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
				torso.moveBy(.1f, .0f, .0f);
			else
				torso.rotate(0.f, -1.f, 0.f);
		}
		if (glfwGetKey(win, GLFW_KEY_A) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
				torso.moveBy(-.1f, .0f, .0f);
			else 
				torso.rotate(0.f, 1.f, 0.f);
		}
		if (glfwGetKey(win, GLFW_KEY_W) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
				torso.moveBy(.0f, .0f, -.1f);
		}
		if (glfwGetKey(win, GLFW_KEY_S) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
				torso.moveBy(.0f, .0f, .1f);
		}
		if (glfwGetKey(win, GLFW_KEY_U) == GLFW_PRESS){
			torso.scaleUpDown(.1f);
		}
		if (glfwGetKey(win, GLFW_KEY_J) == GLFW_PRESS){
			torso.scaleUpDown(-.1f);
		}
		if (glfwGetKey(win, GLFW_KEY_HOME) == GLFW_PRESS) {
			glm::vec3 currentPosition = torso.getPosition();
			glm::vec3 currentRotation = torso.getRotation();
			torso.moveBy(-currentPosition.x, -currentPosition.y, -currentPosition.z);
			torso.rotate(-currentRotation.x, -currentRotation.y, -currentRotation.z);
		}
		if (glfwGetKey(win, GLFW_KEY_SPACE) == GLFW_PRESS){
			if (!hasRandomized) {
				hasRandomized = true;
				torso.randomizePos();
			}
		}
		if (glfwGetKey(win, GLFW_KEY_SPACE) == GLFW_RELEASE) {
			hasRandomized = false;
		}

		glUseProgram(0);
	}

	// Cleanup
	glDeleteProgram(sh.shaderProgram);
	glfwTerminate();

	return 0;
}