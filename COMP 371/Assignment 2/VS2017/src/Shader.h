#pragma once
#include <string>

//#include <GL/glew.h>

using std::string;



class Shader
{
public:
	Shader(string vertFilePath, string fragFilePath);
	~Shader();
	int shaderProgram;
	
private:
	string vertShader;
	string fragShader;
	int vertShaderID;
	int fragShaderID;
	string readShaderFile(const char* filePath);
	void compileShader();
	void linkShader();
};