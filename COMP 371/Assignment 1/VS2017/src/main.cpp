#include <iostream>
#include <cmath>

#include "Shader.h"
#include "Camera.h"
#include "ColoredVertex.h"
#include "Model.h"

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif

//TODO replace all GLuint to unsigned int
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>


// fragshader backup
//vec4(vertexColor.r, vertexColor.g, vertexColor.b, 1.0f)


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


int createLine() { // making the line
	coloredVertex line[] = {
		coloredVertex(glm::vec3(-0.5f, 0.0f, 0.0f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, 0.0f, 0.0f), glm::vec3(1.0f, 1.0f, 1.0f)),
	};

	unsigned int vaoLine;
	glGenVertexArrays(1, &vaoLine);
	glBindVertexArray(vaoLine);

	unsigned int vboLine;
	glGenBuffers(1, &vboLine);
	glBindBuffer(GL_ARRAY_BUFFER, vboLine);
	glBufferData(GL_ARRAY_BUFFER, sizeof(line), line, GL_STATIC_DRAW);

	glVertexAttribPointer(0,
		3,
		GL_FLOAT,
		GL_FALSE,
		sizeof(coloredVertex),
		(void*)0
	);
	glEnableVertexAttribArray(0);

	glVertexAttribPointer(1,
		3,
		GL_FLOAT,
		GL_FALSE,
		sizeof(coloredVertex),
		(void*)sizeof(glm::vec3)
	);
	glEnableVertexAttribArray(1);


	return vboLine;
}

int createCube() {
	coloredVertex cube[] = {
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//left
		coloredVertex(glm::vec3(-0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3( 0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//top
		coloredVertex(glm::vec3( 0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
								 	    	 	 
		coloredVertex(glm::vec3( 0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3( 0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//right
		coloredVertex(glm::vec3( 0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3( 0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3( 0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//bottom
		coloredVertex(glm::vec3(-0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//back
		coloredVertex(glm::vec3(-0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3( 0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		
		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//front
		coloredVertex(glm::vec3(-0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
							
		coloredVertex(glm::vec3( 0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3( 0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
	};

	coloredVertex* ptr = cube;

	//std::cout << sizeof(ptr) << " yes " << sizeof(cube) << std::endl;

	unsigned int vaoCube;
	glGenVertexArrays(1, &vaoCube);
	glBindVertexArray(vaoCube);

	unsigned int vboCube;
	glGenBuffers(1, &vboCube);
	glBindBuffer(GL_ARRAY_BUFFER, vboCube);
	glBufferData(GL_ARRAY_BUFFER, sizeof(cube), ptr, GL_STATIC_DRAW);

	glVertexAttribPointer(0,
		3,
		GL_FLOAT,
		GL_FALSE,
		sizeof(coloredVertex),
		(void*)0
	);
	glEnableVertexAttribArray(0);

	glVertexAttribPointer(1,
		3,
		GL_FLOAT,
		GL_FALSE,
		sizeof(coloredVertex),
		(void*)sizeof(glm::vec3)
	);
	glEnableVertexAttribArray(1);


	return vboCube;

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
	//std::cout <<  << std::endl;

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
	
	//VSYNC
	glfwSwapInterval(1);

	// Initialize GLEW
	//glewExperimental = true; // TODO what this?

	if (glewInit() != GLEW_OK) {
		std::cerr << "Failed to create GLEW" << std::endl;
		glfwTerminate();
		return -1;
	}

	//std::cout << glGetString(GL_VERSION) << std::endl;

	Shader sh("assets/shaders/vertexShader.glsl", "assets/shaders/fragShader.glsl");


	coloredVertex line[] = {
		coloredVertex(glm::vec3(-0.5f, 0.0f, 0.0f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, 0.0f, 0.0f), glm::vec3(1.0f, 1.0f, 1.0f)),
	};

	coloredVertex cube[] = {
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//left
		coloredVertex(glm::vec3(-0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//top
		coloredVertex(glm::vec3(0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//right
		coloredVertex(glm::vec3(0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//bottom
		coloredVertex(glm::vec3(-0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//back
		coloredVertex(glm::vec3(-0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(0.5f,  0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f, -0.5f, -0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),//front
		coloredVertex(glm::vec3(-0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),

		coloredVertex(glm::vec3(0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(-0.5f,  0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
		coloredVertex(glm::vec3(0.5f, -0.5f,  0.5f), glm::vec3(1.0f, 1.0f, 1.0f)),
	};

	//std::cout << *(&cube->position.z + sizeof(coloredVertex)) << std::endl;

	
	Model _line(line, sizeof(line), glm::vec3(1.0f, 1.0f, 0.0f));
	Model _cube(cube, sizeof(cube), glm::vec3(1.0f, 1.0f, 1.0f)); // WARNING SIZE

	int vbo = createLine();
	//int cube = createCube();

	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	
	// set up Camera class with starting point
	
	float spd = 1.0f;
	
	glm::vec3 Eye = glm::vec3(0.0f, 10.0f, 10.0f);
	glm::vec3 Center = glm::vec3(0.0f, 0.0f, 0.0f);
	glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);

	float pitch = acos(glm::dot(Center - Eye, glm::vec3(0.0f, 1.0f, 0.0f)) / (glm::length(Center - Eye) * glm::length(glm::vec3(0.0f, 1.0f, 0.0f))));

	//std::cout << "Pitch: " << pitch << std::endl;

	Camera cam(&Eye, &Center, &Up, -pitch, &spd, PERSPECTIVE, win);

	
	float lastFrameTime = glfwGetTime();
	
	glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

	glEnable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);
	
	while (!glfwWindowShouldClose(win))
	{
		glUseProgram(sh.shaderProgram);

		float dt = glfwGetTime() - lastFrameTime;
		lastFrameTime += dt;

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// input processing
		cam.processMovement(win, dt);

		// performing view and projection transformations
		cam.updateView(sh, win, dt);

		// rendering
		glm::mat4 scalingMatrix;
		glm::mat4 translationMatrix;
		glm::mat4 worldMatrix;
		GLuint worldMatrixLocation = glGetUniformLocation(sh.shaderProgram, "worldMatrix");

		//Grid Lines
		scalingMatrix = glm::scale(glm::mat4(1.0f), glm::vec3(100.0f, 1.0f, 1.0f));

		//Z-axis Lines
		for (int i = -50; i <= 50; i++) {
			translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, 0.0f, i * 1.0f));
			worldMatrix = translationMatrix * scalingMatrix;
			_line.draw(sh, GL_LINES, 0, 3, worldMatrix);

		}

		//X-axis Lines
		glm::mat4 rotation = glm::rotate(glm::mat4(1.0f), glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));

		for (int i = -50; i <= 50; i++) {
			translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(i * 1.0f, 0.0f, 0.0f));
			worldMatrix = translationMatrix * rotation * scalingMatrix;
			_line.draw(sh, GL_LINES, 0, 3, worldMatrix);
		}
		

		//Coordinate Axis Lines
		int scale = 5;
		
		//X
		scalingMatrix = glm::scale(glm::mat4(1.0f), glm::vec3(scale * 1.0f, 1.1f, 1.1f));
		translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(scale * 0.5f, 0.01f, 0.0f));
		worldMatrix = translationMatrix * scalingMatrix;
		_line.draw(sh, GL_LINES, 0, 3, worldMatrix, glm::vec3(1.0f, 0.0f, 0.0f));

		//Y
		rotation = glm::rotate(glm::mat4(1.0f), glm::radians(90.0f), glm::vec3(0.0f, 0.0f, 1.0f));
		translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, scale * 0.5f, 0.0f));
		worldMatrix = translationMatrix * rotation * scalingMatrix;
		_line.draw(sh, GL_LINES, 0, 3, worldMatrix, glm::vec3(0.0f, 1.0f, 0.0f));

		//Z
		rotation = glm::rotate(glm::mat4(1.0f), glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
		translationMatrix = glm::translate(glm::mat4(1.0f), glm::vec3(0.0f, 0.01f, scale * 0.5f));
		worldMatrix = translationMatrix * rotation * scalingMatrix;
		_line.draw(sh, GL_LINES, 0, 3, worldMatrix, glm::vec3(0.0f, 0.0f, 1.0f));
		
		//Drawing cube at origin
		scalingMatrix = glm::scale(glm::mat4(1.0f), glm::vec3(4.0f, 4.0f, 4.0f));
		worldMatrix = scalingMatrix;
		_cube.draw(sh, GL_TRIANGLES, 0, 36, worldMatrix);
		
		// swap buffers
		glfwSwapBuffers(win);
		// check/call events
		glfwPollEvents();

		// escape to close window
		if (glfwGetKey(win, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
			glfwSetWindowShouldClose(win, true);
		}

		glUseProgram(0);
	
	}

	glDeleteProgram(sh.shaderProgram);

	glfwTerminate();


	return 0;
}