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
	/*MeshEBO footLeft(eboCube, eboCubeIndices, glm::vec3(.5f, 0.25f, 0.f), glm::vec3(0.f), glm::vec3(.2f, .5f, .2f), glm::vec3(0.f), glm::vec3(0.f));
	MeshEBO footRight(eboCube, eboCubeIndices, glm::vec3(-.5f, 0.25f, 0.f), glm::vec3(0.f), glm::vec3(.2f, .5f, .2f), glm::vec3(0.f), glm::vec3(0.f));
	
	MeshEBO legLeft(eboCube, eboCubeIndices, glm::vec3(.5f, 0.75f, 0.f), glm::vec3(0.f), glm::vec3(.4f, 1.f, .4f), glm::vec3(0.f), glm::vec3(0.5f));
	MeshEBO legRight(eboCube, eboCubeIndices, glm::vec3(-.5f, 0.75f, 0.f), glm::vec3(0.f), glm::vec3(.4f, 1.f, .4f), glm::vec3(0.f), glm::vec3(0.5f));

	MeshEBO torso(eboCube, eboCubeIndices, glm::vec3(0.f, 2.f, 0.f), glm::vec3(0.f), glm::vec3(2.f, 2.f, 1.f));
	MeshEBO button1(eboCube, eboCubeIndices, glm::vec3(0.f, 2.5f, .2f), glm::vec3(0.f), glm::vec3(.2f, .2f, .8f), glm::vec3(0.f), glm::vec3(0.f));
	MeshEBO button2(eboCube, eboCubeIndices, glm::vec3(0.f, 1.5f, .2f), glm::vec3(0.f), glm::vec3(.2f, .2f, .8f), glm::vec3(0.f), glm::vec3(0.f));

	MeshEBO armLeft(eboCube, eboCubeIndices, glm::vec3(1.5f, 2.5f, 0.f), glm::vec3(0.f), glm::vec3(3.f, .2f, .2f), glm::vec3(0.f), glm::vec3(.5f, .37f, .2f));
	MeshEBO armRight(eboCube, eboCubeIndices, glm::vec3(-1.5f, 2.5f, 0.f), glm::vec3(0.f), glm::vec3(3.f, .2f, .2f), glm::vec3(0.f), glm::vec3(.5f, .37f, .2f));

	MeshEBO handLeft(eboCube, eboCubeIndices, glm::vec3(3.f, 2.5f, 0.f), glm::vec3(0.f), glm::vec3(.5f), glm::vec3(0.f));
	MeshEBO handRight(eboCube, eboCubeIndices, glm::vec3(-3.f, 2.5f, 0.f), glm::vec3(0.f), glm::vec3(.5f), glm::vec3(0.f));

	MeshEBO head(eboCube, eboCubeIndices, glm::vec3(0.f, 3.5f, 0.f), glm::vec3(0.f), glm::vec3(1.f, 2.f, 1.f));
	MeshEBO eye(eboCube, eboCubeIndices, glm::vec3(0.f, 4.f, 0.2f), glm::vec3(0.f), glm::vec3(.6f, 0.2f, .8f), glm::vec3(0.f), glm::vec3(0.f));
	MeshEBO hatBottom(eboCube, eboCubeIndices, glm::vec3(0.f, 4.5f, 0.f), glm::vec3(0.f), glm::vec3(1.5f, 0.2f, 1.1f), glm::vec3(0.f), glm::vec3(.91f, .55f, .12f));
	MeshEBO hatTop(eboCube, eboCubeIndices, glm::vec3(0.f, 4.6f, 0.f), glm::vec3(0.f), glm::vec3(.8f, 1.f, .8f), glm::vec3(0.f), glm::vec3(1.f, .88f, .42f));
	MeshEBO topper(eboCube, eboCubeIndices, glm::vec3(0.f, 5.f, 0.f), glm::vec3(0.f), glm::vec3(.3f, 2.f, .3f), glm::vec3(0.f), glm::vec3(0.f));*/

	// creates the hierarchy
	/*torso.addChild(&footLeft);
	torso.addChild(&footRight);
	torso.addChild(&legLeft);
	torso.addChild(&legRight);
	torso.addChild(&button1);
	torso.addChild(&button2);
	torso.addChild(&armLeft);
	torso.addChild(&armRight);
	torso.addChild(&handLeft);
	torso.addChild(&handRight);
	torso.addChild(&head);
	torso.addChild(&eye);
	torso.addChild(&hatBottom);
	torso.addChild(&hatTop);
	torso.addChild(&topper);*/

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


	// Background Color
	glClearColor(0.11f, 0.44f, 0.68f, 1.0f);
	
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
		sh.use();

		// calculating deltatime
		float dt = glfwGetTime() - lastFrameTime;
		lastFrameTime += dt;

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// camera's input processing
		cam.processMovement(win, dt);

		// Performing view and projection transformations for camera
		cam.updateView(sh, win, dt);

		//Light
		sh.setVec3("light.position", 0.f, 30.f, 0.f);
		sh.setVec3("light.intensities", 1.f, 1.f, 1.f);

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
		

		//Drawing snowman at origin
		glLineWidth(1);
		glPointSize(10);

		//torso.Draw(&sh);

		plane.draw(&sh, GL_TRIANGLES);
		//sphere.draw(&sh, GL_TRIANGLES);
		olaf.draw(&sh, GL_TRIANGLES);
		//olaf.scaleUpDown(n);


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
				//torso.moveBy(.1f, .0f, .0f);
				olaf.moveBy(-.1f, .0f, .0f);
			}
			else
			{
				//torso.rotate(0.f, -1.f, 0.f);
				olaf.rotateBy(0.f, -1.f, 0.f);
			}
		}
		if (glfwGetKey(win, GLFW_KEY_A) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
			{
				//torso.moveBy(-.1f, .0f, .0f);
				olaf.moveBy(.1f, .0f, .0f);
			}
			else
			{
				//torso.rotate(0.f, 1.f, 0.f);
				olaf.rotateBy(0.f, 1.f, 0.f);
			}
		}
		if (glfwGetKey(win, GLFW_KEY_W) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
			{
				//torso.moveBy(.0f, .0f, -.1f);
				olaf.moveBy(.0f, .0f, .1f);
			}
		}
		if (glfwGetKey(win, GLFW_KEY_S) == GLFW_PRESS) {
			if (glfwGetKey(win, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS)
			{
				//torso.moveBy(.0f, .0f, .1f);
				olaf.moveBy(.0f, .0f, -.1f);
			}
		}
		if (glfwGetKey(win, GLFW_KEY_U) == GLFW_PRESS){
			//torso.scaleUpDown(.1f);
			olaf.scaleUpDown(1.1f);
		}
		if (glfwGetKey(win, GLFW_KEY_J) == GLFW_PRESS){
			//torso.scaleUpDown(-.1f);
			olaf.scaleUpDown(.9f);
		}
		if (glfwGetKey(win, GLFW_KEY_HOME) == GLFW_PRESS) {
			//glm::vec3 currentPosition = torso.getPosition();
			//glm::vec3 currentRotation = torso.getRotation();
			//torso.moveBy(-currentPosition.x, -currentPosition.y, -currentPosition.z);
			//torso.rotate(-currentRotation.x, -currentRotation.y, -currentRotation.z);
			cam.reset();
			olaf.reset();
		}
		if (glfwGetKey(win, GLFW_KEY_SPACE) == GLFW_PRESS){
			if (!hasRandomized) {
				hasRandomized = true;
				//torso.randomizePos();
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