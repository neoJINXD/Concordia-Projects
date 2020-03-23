#pragma once

#include "objMesh.h"
#include "Shader.h"
#include <vector>

class objModel
{
public:
	objModel();
	~objModel();

	void draw(Shader* sh);
	void rotateBy(float x, float y, float z);
	void randomizePos();
	void moveBy(float x, float y, float z);
	void scaleUpDown(float n);
	void reset();

	void addMesh(objMesh* _mesh);

	void changeType(unsigned int _type);


private:
	std::vector<objMesh*> meshes;
	
	glm::mat4 groupMatrix;
};

