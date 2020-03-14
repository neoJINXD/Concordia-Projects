#include <iostream>
#include <cmath>

#include "Shader.h"
#include "Camera.h"
#include "ColoredVertex.h"
#include "Mesh.h"
#include "objMesh.h"
#include "objModel.h"
#include "Texture.h"

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

	unsigned int WIDTH = 1024, HEIGHT = 768;

	glfwInit();
	glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

	// Creating GLFW window
	GLFWwindow* win = glfwCreateWindow(WIDTH, HEIGHT, "COMP371 - Assignment 1", NULL, NULL);
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
	Shader depthShader("assets/shaders/shadowVert.glsl", "assets/shaders/shadowFrag.glsl");


	// Arrays for the shapes used in rendering
	coloredVertex line[] = {
		coloredVertex(glm::vec3(-0.5f, 0.0f, 0.0f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, 0.0f, 0.0f), glm::vec3(1.0f, 1.0f, 1.0f)),
	};


	// Creating meshes
	// creating line
	Mesh _line(line, sizeof(line), glm::vec3(1.0f, 1.0f, 0.0f));

	objMesh plane("assets/models/plane.obj", glm::vec3(.8f), glm::vec3(0.f), glm::vec3(50.f, 1.f, 50.f));

	objMesh torso("assets/models/sphere.obj", glm::vec3(.99f), glm::vec3(0.f, 2.1f, 0.f), glm::vec3(1.5f, 1.f, 1.f));
	objMesh button1("assets/models/sphere.obj", glm::vec3(0.f), glm::vec3(0.f, 2.6f, .9f), glm::vec3(.2f, .2f, .2f));
	objMesh button2("assets/models/sphere.obj", glm::vec3(0.f), glm::vec3(0.f, 2.1f, .9f), glm::vec3(.2f, .2f, .2f));
	objMesh button3("assets/models/sphere.obj", glm::vec3(0.f), glm::vec3(0.f, 1.6f, .9f), glm::vec3(.2f, .2f, .2f));

	objMesh head("assets/models/sphere.obj", glm::vec3(1.f), glm::vec3(0.f, 3.6f, 0.f), glm::vec3(1.2f));
	objMesh hat1("assets/models/cube.obj", glm::vec3(1.f), glm::vec3(0.f, 4.8f, 0.f), glm::vec3(1.f, .3f, 1.f));
	objMesh hat2("assets/models/cube.obj", glm::vec3(1.f, .88f, .42f), glm::vec3(0.f, 5.8f, 0.f), glm::vec3(.7f, .5f, .7f));
	objMesh hat3("assets/models/cube.obj", glm::vec3(1.f), glm::vec3(0.f, 6.8f, 0.f), glm::vec3(.25f, .7f, .25f));
	objMesh eye("assets/models/cube.obj", glm::vec3(1.f), glm::vec3(0.f, 4.1f, 1.f), glm::vec3(.8f, .2f, .2f));

	objMesh leftArm("assets/models/cube.obj", glm::vec3(.5f, .37f, .2f), glm::vec3(2.8f, 2.1f, 0.f), glm::vec3(1.5f, .1f, .1f));
	objMesh rightArm("assets/models/cube.obj", glm::vec3(.5f, .37f, .2f), glm::vec3(-2.8f, 2.1f, 0.f), glm::vec3(1.5f, .1f, .1f));

	objMesh leftLeg("assets/models/cube.obj", glm::vec3(.5f, .37f, .2f), glm::vec3(.5f, .3f, 0.f), glm::vec3(.2f, 1.f, .2f));
	objMesh rightLeg("assets/models/cube.obj", glm::vec3(.5f, .37f, .2f), glm::vec3(-.5f, .3f, 0.f), glm::vec3(.2f, 1.f, .2f));
	objMesh leftFoot("assets/models/cube.obj", glm::vec3(0.f), glm::vec3(.5f, .1f, 0.f), glm::vec3(.35f, .1f, .35f));
	objMesh rightFoot("assets/models/cube.obj", glm::vec3(0.f), glm::vec3(-.5f, .1f, 0.f), glm::vec3(.35f, .1f, .35f));

	objMesh scarf("assets/models/cube.obj", glm::vec3(.81f, .55f, .18f), glm::vec3(0.f, 3.f, 0.f), glm::vec3(1.2f, .2f, 1.f), glm::vec3(0.f, 0.f, 10.f));
	objMesh scarfBit("assets/models/cube.obj", glm::vec3(.81f, .55f, .18f), glm::vec3(1.f, 3.f, 0.f), glm::vec3(1.2f, .2f, .2f), glm::vec3(0.f, 0.f, 10.f));

	objModel olaf;
	olaf.addMesh(&torso);
	olaf.addMesh(&button1);
	olaf.addMesh(&button2);
	olaf.addMesh(&button3);
	olaf.addMesh(&head);
	olaf.addMesh(&hat1);
	olaf.addMesh(&hat2);
	olaf.addMesh(&hat3);
	olaf.addMesh(&eye);
	olaf.addMesh(&leftArm);
	olaf.addMesh(&rightArm);
	olaf.addMesh(&leftLeg);
	olaf.addMesh(&leftFoot);
	olaf.addMesh(&rightLeg);
	olaf.addMesh(&rightFoot);
	olaf.addMesh(&scarf);
	olaf.addMesh(&scarfBit);

	//Textures!!1

	Texture snow("assets/textures/snow.jpg", GL_TEXTURE_2D);
	plane.setTexture(&snow);
	Texture carrot("assets/textures/carrot.jpg", GL_TEXTURE_2D);
	eye.setTexture(&carrot);
	Texture metal("assets/textures/metal.jpg", GL_TEXTURE_2D);
	hat1.setTexture(&metal);
	hat2.setTexture(&metal);
	hat3.setTexture(&metal);

	Texture col("assets/textures/color.png", GL_TEXTURE_2D);

	//configuring depth map
	const unsigned int SHADOW_WIDTH = 1024, SHADOW_HEIGHT = 1024;
	unsigned int depthMapFBO;
	glGenFramebuffers(1, &depthMapFBO);

	unsigned int depthMap;
	glGenTextures(1, &depthMap);
	glBindTexture(GL_TEXTURE_2D, depthMap);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL_DEPTH_COMPONENT, GL_FLOAT, NULL);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
	float borderColor[] = { 1.f, 1.f, 1.f, 1.f };
	glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);

	glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);
	glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthMap, 0);
	glDrawBuffer(GL_NONE);
	glReadBuffer(GL_NONE);
	glBindFramebuffer(GL_FRAMEBUFFER, 0);

	//initial shader config
	sh.use();
	sh.setInt("diffuseTexture", 0);
	sh.setInt("shadowMap", 1);


	glm::vec3 lightPos = glm::vec3(0.f, 30.f, 0.f);



	
	// Setting up Camera with starting point
	float spd = 1.0f;
	
	glm::vec3 Eye = glm::vec3(0.0f, 25.0f, 10.0f);
	glm::vec3 Center = glm::vec3(0.0f, 0.0f, 0.0f);
	glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);

	//Eventually removed, since no mouse movement for camera
	float pitch = acos(glm::dot(Center - Eye, glm::vec3(0.0f, 1.0f, 0.0f)) / (glm::length(Center - Eye) * glm::length(glm::vec3(0.0f, 1.0f, 0.0f))));

	Camera cam(&Eye, &Center, &Up, &spd, PERSPECTIVE, win);

	
	float lastFrameTime = glfwGetTime();

	// Disabling mouse cursor
	glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	glEnable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);
	//glEnable(GL_FRAMEBUFFER_SRGB);

	bool hasRandomized = false;
	float n = 1.f;

	while (!glfwWindowShouldClose(win))
	{
		// calculating deltatime
		float dt = glfwGetTime() - lastFrameTime;
		lastFrameTime += dt;

		// Background Color
		glClearColor(0.11f, 0.44f, 0.68f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		

		//////////////////////////////////////////////////////////////////////////////////////////////////
		//first pass, render shadow map

		glm::mat4 lightProjection, lightView;
		glm::mat4 lightSpaceMatrix;
		float near_plane = 1.f, far_plane = 25.f;

		//lightProjection = glm::perspective(glm::radians(45.f), (float)SHADOW_WIDTH / (float)SHADOW_HEIGHT, near_plane, far_plane);
		lightProjection = glm::ortho(-10.f, 10.f, -10.f, 10.f, near_plane, far_plane);
		lightView = glm::lookAt(lightPos, glm::vec3(0.f), glm::vec3(0.f, 1.f, 0.f));
		lightSpaceMatrix = lightProjection * lightView;

		//render from ligth's pov
		depthShader.use();
		depthShader.setMat4("lightSpaceMatrix", lightSpaceMatrix);

		glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
		glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);
		glClear(GL_DEPTH_BUFFER_BIT);
		plane.draw(&depthShader, GL_TRIANGLES);
		olaf.draw(&depthShader, GL_TRIANGLES);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);


		//reset
		glViewport(0, 0, WIDTH, HEIGHT);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


		/////////////////////////////////////////////////////////////////////////////////////////////////
		//second pass, render scene
		glViewport(0, 0, WIDTH, HEIGHT);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		sh.use();

		// camera's input processing
		cam.processMovement(win, dt);

		// Performing view and projection transformations for camera
		cam.updateView(sh, win, dt);

		
		//Light
		sh.setVec3("light.position", lightPos);
		sh.setVec3("light.intensities", 1.f, 1.f, 1.f);
		sh.setMat4("lightSpaceMatrix", lightSpaceMatrix);

		//plane.setTexture(&depthMap);

		//drawing
		//Drawing snowman at origin
		glLineWidth(1);
		glPointSize(10);

		plane.draw(&sh, GL_TRIANGLES);
		olaf.draw(&sh, GL_TRIANGLES);


		// Rendering
		glm::mat4 scalingMatrix;
		glm::mat4 translationMatrix;
		glm::mat4 worldMatrix;
		glm::mat4 rotation;

		// Coordinate Axis Lines
		int scale = 5; // 5 Unit length
		glLineWidth(5);

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
		/////////////////////////////////////////////////////////////////////////////////////////////////

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
			//torso.changeType(GL_LINE_LOOP);
		}
		if (glfwGetKey(win, GLFW_KEY_T) == GLFW_PRESS) {
			// Shape with GL_TRIANGLES
			//torso.changeType(GL_TRIANGLES);
		}
		if (glfwGetKey(win, GLFW_KEY_P) == GLFW_PRESS) {
			// Points with GL_POINTS
			//torso.changeType(GL_POINTS);
		}
		if (glfwGetKey(win, GLFW_KEY_D) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
			{
				olaf.moveBy(-.1f, .0f, .0f);
			}
			else
			{
				olaf.rotateBy(0.f, -1.f, 0.f);
			}
		}
		if (glfwGetKey(win, GLFW_KEY_A) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
			{
				olaf.moveBy(.1f, .0f, .0f);
			}
			else
			{
				olaf.rotateBy(0.f, 1.f, 0.f);
			}
		}
		if (glfwGetKey(win, GLFW_KEY_W) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
			{
				olaf.moveBy(.0f, .0f, .1f);
			}
		}
		if (glfwGetKey(win, GLFW_KEY_S) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
			{
				olaf.moveBy(.0f, .0f, -.1f);
			}
		}
		if (glfwGetKey(win, GLFW_KEY_U) == GLFW_PRESS){
			olaf.scaleUpDown(1.1f);
		}
		if (glfwGetKey(win, GLFW_KEY_J) == GLFW_PRESS){
			olaf.scaleUpDown(.9f);
		}
		if (glfwGetKey(win, GLFW_KEY_HOME) == GLFW_PRESS) {
			cam.reset();
			olaf.reset();
		}
		if (glfwGetKey(win, GLFW_KEY_SPACE) == GLFW_PRESS){
			if (!hasRandomized) {
				hasRandomized = true;
				olaf.randomizePos();
			}
		}
		if (glfwGetKey(win, GLFW_KEY_SPACE) == GLFW_RELEASE) {
			hasRandomized = false;
		}
		if (glfwGetKey(win, GLFW_KEY_UP) == GLFW_PRESS)
		{
			plane.setTexture(&snow);
			eye.setTexture(&carrot);
			hat1.setTexture(&metal);
			hat2.setTexture(&metal);
			hat3.setTexture(&metal);

			eye.setColor(glm::vec3(1.f));
			hat1.setColor(glm::vec3(1.f));
			hat3.setColor(glm::vec3(1.f));
		}
		if (glfwGetKey(win, GLFW_KEY_DOWN) == GLFW_PRESS)
		{
			plane.setTexture(&col);
			eye.setTexture(&col);
			hat1.setTexture(&col);
			hat2.setTexture(&col);
			hat3.setTexture(&col);

			eye.setColor(glm::vec3(0.f));
			hat1.setColor(glm::vec3(0.f));
			hat3.setColor(glm::vec3(0.f));
		}

		glUseProgram(0);
	}

	// Cleanup
	glfwTerminate();

	return 0;
}