#include "Shader.h"
#include <iostream>
#include <fstream>
#include <sstream>

#ifndef GLEW_STATIC
#define GLEW_STATIC 1
#endif


#include <GL/glew.h>

Shader::Shader(string vertFilePath, string fragFilePath) {
    vertShader = readShaderFile(vertFilePath.c_str());
    fragShader = readShaderFile(fragFilePath.c_str());
    compileShader();
    linkShader();
};

Shader::~Shader() {};


string Shader::readShaderFile(const char* fileName) {
	string content;
	std::ifstream input(fileName, std::ios::in);

	//Kills program if file stream fails to initiate
	if (!input.good()) {
		std::cerr << "INVALID FILE" << std::endl;
		exit(-1);//NOT GOOD PRACTICE
	}
	//Reads from file
	if (input.is_open()) {
		std::stringstream ss;
		ss << input.rdbuf();
		content = ss.str();
		input.close();
	}
	return content;
};

void Shader::compileShader() {

    // vertex shader
    //int vertID = glCreateShader(GL_VERTEX_SHADER);
    vertShaderID = glCreateShader(GL_VERTEX_SHADER);
    const char* vertSource = vertShader.c_str();
    glShaderSource(vertShaderID, 1, &vertSource, NULL);
    glCompileShader(vertShaderID);

    // check for shader compile errors
    int success;
    char infoLog[512];
    glGetShaderiv(vertShaderID, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(vertShaderID, 512, NULL, infoLog);
        std::cerr << "ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    // fragment shader
    fragShaderID = glCreateShader(GL_FRAGMENT_SHADER);
    const char* fragSource = fragShader.c_str();
    glShaderSource(fragShaderID, 1, &fragSource, NULL);
    glCompileShader(fragShaderID);

    // check for shader compile errors
    glGetShaderiv(fragShaderID, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(fragShaderID, 512, NULL, infoLog);
        std::cerr << "ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

}

void Shader::linkShader() {
    // link shaders
    shaderProgram = glCreateProgram();
    glAttachShader(shaderProgram, vertShaderID);
    glAttachShader(shaderProgram, fragShaderID);
    glLinkProgram(shaderProgram);

    // check for linking errors
    int success;
    char infoLog[512];
    glGetProgramiv(shaderProgram, GL_LINK_STATUS, &success);
    if (!success) {
        glGetProgramInfoLog(shaderProgram, 512, NULL, infoLog);
        std::cerr << "ERROR::SHADER::PROGRAM::LINKING_FAILED\n" << infoLog << std::endl;
    }

    glDetachShader(shaderProgram, vertShaderID);
    glDetachShader(shaderProgram, fragShaderID);


    glDeleteShader(vertShaderID);
    glDeleteShader(fragShaderID);
}

/*
#include "Shader.h"
#include <iostream>
#include <fstream>
#include <sstream>

using std::string;

Shader::Shader() {};

Shader::~Shader() {};

void Shader::printV() {
    this->vertShader = readShaderFile("assets/shaders/vertexShader.glsl");
    std::cout << this->vertShader << std::endl;
};

void Shader::printF() {

};

string Shader::readShaderFile(const char* fileName) {
    string content;
    std::ifstream input(fileName, std::ios::in);

    //Kills program if file stream fails to initiate
    if (!input.good()) {
        std::cerr << "INVALID FILE" << std::endl;
        exit(-1);//NOT GOOD PRACTICE
    }
    //Reads from file
    if (input.is_open()) {
        std::stringstream ss;
        ss << input.rdbuf();
        content = ss.str();
        input.close();
    }
    return content;
};*/