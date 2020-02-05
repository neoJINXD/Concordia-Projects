#pragma once
#include <string>

#include <GL/glew.h>

using std::string;



class Shader
{
public:
	Shader();
	~Shader();
	void printV();
	void printF();
private:
	string vertShader;
	string fragShader;
	static string readShaderFile(const char* filePath);
	GLuint compileShader();
	GLuint buildShader();


};

