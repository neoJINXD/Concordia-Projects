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
};