//
// COMP 371 Labs Framework
//
// Created by Nicolas Bergeron on 20/06/2019.
//
// Inspired by the following tutorials:
// - https://learnopengl.com/Getting-started/Hello-Window
// - https://learnopengl.com/Getting-started/Hello-Triangle

#include "lab02.h"
#include <iostream>

#define GLEW_STATIC 1   // This allows linking with Static Library on Windows, without DLL
#include <GL/glew.h>    // Include GLEW - OpenGL Extension Wrangler

#include <GLFW/glfw3.h> // GLFW provides a cross-platform interface for creating a graphical context,
                        // initializing OpenGL and binding inputs

#include <glm/glm.hpp>  // GLM is an optimized math library with syntax to similar to OpenGL Shading Language
#include <glm/gtc/matrix_transform.hpp> // include this to create transformation matrices

void wasdMovement(GLFWwindow* win, float* speed, float deltaTime, glm::vec3* camEye, glm::vec3* camCenter, glm::vec3* camUp);

const char* getVertexShaderSource()
{
    // For now, you use a string for your shader code, in the assignment, shaders will be stored in .glsl files
    // World is for object placement?
    // View is for where camera would be
    // Projection is for perspective vs orthographic
    return
                "#version 330 core\n"
                "layout (location = 0) in vec3 aPos;"
                "layout (location = 1) in vec3 aColor;"
                "uniform mat4 worldMatrix = mat4(1.0);"
                "uniform mat4 viewMatrix = mat4(1.0);"
                "uniform mat4 projectionMatrix = mat4(1.0);"
                "out vec3 vertexColor;"
                "void main()"
                "{"
                "   vertexColor = aColor;"
                "   gl_Position = projectionMatrix * viewMatrix * worldMatrix * vec4(aPos.x, aPos.y, aPos.z, 1.0);"
                "}";
}


const char* getFragmentShaderSource()
{
    return
                "#version 330 core\n"
                "in vec3 vertexColor;"
                "out vec4 FragColor;"
                "void main()"
                "{"
                "   FragColor = vec4(vertexColor.r, vertexColor.g, vertexColor.b, 1.0f);"
                "}";
}


int compileAndLinkShaders()
{
    // compile and link shader program
    // return shader program id
    // ------------------------------------

    // vertex shader
    int vertexShader = glCreateShader(GL_VERTEX_SHADER);
    const char* vertexShaderSource = getVertexShaderSource();
    glShaderSource(vertexShader, 1, &vertexShaderSource, NULL);
    glCompileShader(vertexShader);
    
    // check for shader compile errors
    int success;
    char infoLog[512];
    glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(vertexShader, 512, NULL, infoLog);
        std::cerr << "ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" << infoLog << std::endl;
    }
    
    // fragment shader
    int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
    const char* fragmentShaderSource = getFragmentShaderSource();
    glShaderSource(fragmentShader, 1, &fragmentShaderSource, NULL);
    glCompileShader(fragmentShader);
    
    // check for shader compile errors
    glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(fragmentShader, 512, NULL, infoLog);
        std::cerr << "ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" << infoLog << std::endl;
    }
    
    // link shaders
    int shaderProgram = glCreateProgram();
    glAttachShader(shaderProgram, vertexShader);
    glAttachShader(shaderProgram, fragmentShader);
    glLinkProgram(shaderProgram);
    
    // check for linking errors
    glGetProgramiv(shaderProgram, GL_LINK_STATUS, &success);
    if (!success) {
        glGetProgramInfoLog(shaderProgram, 512, NULL, infoLog);
        std::cerr << "ERROR::SHADER::PROGRAM::LINKING_FAILED\n" << infoLog << std::endl;
    }
    
    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);
    
    return shaderProgram;
}

int createVertexBufferObject()
{
    // A vertex is a point on a polygon, it contains positions and other data (eg: colors)
    glm::vec3 vertexArray[] = {
        glm::vec3( 0.0f,  0.5f, 0.03f),  // top center position
        glm::vec3( 1.0f,  0.0f, 0.0f),  // top center color (red)
        glm::vec3( 0.5f, -0.5f, 0.03f),  // bottom right
        glm::vec3( 0.0f,  1.0f, 0.0f),  // bottom right color (green)
        glm::vec3(-0.5f, -0.5f, 0.03f),  // bottom left
        glm::vec3( 0.0f,  0.0f, 1.0f),  // bottom left color (blue)
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
                          2*sizeof(glm::vec3), // stride - each vertex contain 2 vec3 (position, color)
                          (void*)0             // array buffer offset
                          );
    glEnableVertexAttribArray(0);


    glVertexAttribPointer(1,                            // attribute 1 matches aColor in Vertex Shader
                          3,
                          GL_FLOAT,
                          GL_FALSE,
                          2*sizeof(glm::vec3),
                          (void*)sizeof(glm::vec3)      // color is offseted a vec3 (comes after position)
                          );
    glEnableVertexAttribArray(1);

    
    return vertexBufferObject;
}


int run()
{
    // Initialize GLFW and OpenGL version
    glfwInit();
    
#if defined(PLATFORM_OSX)
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
#else
    // On windows, we set OpenGL version to 2.1, to support more hardware
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
#endif

    // Create Window and rendering context using GLFW, resolution is 800x600
    GLFWwindow* window = glfwCreateWindow(800, 600, "Comp371 - Assignment 1", NULL, NULL);
    if (window == NULL)
    {
        std::cerr << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);
    

    // Initialize GLEW
    glewExperimental = true; // Needed for core profile
    if (glewInit() != GLEW_OK) {
        std::cerr << "Failed to create GLEW" << std::endl;
        glfwTerminate();
        return -1;
    }

    // Black background
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    
    // Compile and link shaders here ...
    int shaderProgram = compileAndLinkShaders();
    
    // Define and upload geometry to the GPU here ...
    int vbo = createVertexBufferObject();
    
    // Variables to be used later in tutorial
    float angle = 0;
    float rotationSpeed = 180.0f;  // 180 degrees per second
    double lastFrameTime = glfwGetTime();

    //glEnable(GL_CULL_FACE);

    float sped = 1.0f;
    float* speed = &sped;
    
    //glm::mat4 defaultStart = glm::mat4(1.0f);


    glm::vec3 Eye = glm::vec3(0.0f, 0.0f, 0.0f);
    glm::vec3 camDefaultTarget = glm::vec3(0.0f, 0.0f, 0.0f);
    glm::vec3 Center = glm::vec3(0.0f, 0.0f, -1.0f);
    //glm::vec3 Center = glm::normalize(Eye - camDefaultTarget);
    glm::vec3 Up = glm::vec3(0.0f, 1.0f, 0.0f);
    //glm::vec3 camRight = glm::normalize(glm::cross(Up, Center));

    /*
            *camEye = glm::vec3(0.0f, 0.0f, 0.0f);
            *camCenter = glm::vec3(0.0f, 0.0f, -1.0f);
            *camUp = glm::vec3(0.0f, 1.0f, 0.0f);
            */

    glm::vec3* camEye = &Eye;
    glm::vec3* camCenter = &Center;
    glm::vec3* camUp = &Up;


    std::cout << "We heading into Perspective" << std::endl;
    glm::mat4 projectionMatrix = glm::perspective(glm::radians(45.0f),  // field of view in degrees
        800.0f / 600.0f,      // aspect ratio
        0.01f, 100.0f);       // near and far (near > 0)


    // Entering Main Loop
    while(!glfwWindowShouldClose(window))
    {
        
        // Each frame, reset color of each pixel to glClearColor
        glClear(GL_COLOR_BUFFER_BIT);
        
        // Draw geometry
        glUseProgram(shaderProgram);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        float dt = glfwGetTime() - lastFrameTime;
        lastFrameTime += dt;

        angle = (angle + rotationSpeed * dt); // angles in degrees, but glm expects radians (conversion below)
        glm::mat4 rotationMatrix = glm::rotate(glm::mat4(1.0f), glm::radians(angle), glm::vec3(0.0f, 1.0f, 0.0f));

        GLuint worldMatrixLocation = glGetUniformLocation(shaderProgram, "worldMatrix");
        glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &rotationMatrix[0][0]);

        glDrawArrays(GL_TRIANGLES, 0, 3); // 3 vertices, starting at index 0

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

        // End Frame
        glfwSwapBuffers(window);
        glfwPollEvents();
        
        // Handle inputs
        wasdMovement(window, speed, dt, camEye, camCenter, camUp);


        // View Transform
        GLuint viewMatrixLocation = glGetUniformLocation(shaderProgram, "viewMatrix");
        glm::mat4 viewMatrix = glm::lookAt(*camEye, *camEye + *camCenter, *camUp);
        glUniformMatrix4fv(viewMatrixLocation, 1, GL_FALSE, &viewMatrix[0][0]);

        // Projection Transform
        GLuint projectionMatrixLocation = glGetUniformLocation(shaderProgram, "projectionMatrix");
        glUniformMatrix4fv(projectionMatrixLocation, 1, GL_FALSE, &projectionMatrix[0][0]);
        
    }
    
    // Shutdown GLFW
    glfwTerminate();
    
	return 0;
}

void wasdMovement(GLFWwindow* win, float* speed, float deltaTime, glm::vec3* camEye, glm::vec3* camCenter, glm::vec3* camUp) { // TODO should probably pass pointers to the cam variables?
    // Calcualting a speed normalized based on how much time has passed,
    // speed is no longer affected by fps
    if (glfwGetKey(win, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
        glfwSetWindowShouldClose(win, true);
    }

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
};