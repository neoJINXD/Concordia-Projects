#pragma once

#include "objMesh.h"
#include "Shader.h"
#include <vector>

class objModel
{
public:
	objModel();
	~objModel();

	void draw(Shader* sh, unsigned int type);
	void rotateBy(float x, float y, float z);
	void randomizePos();
	void moveBy(float x, float y, float z);
	void scaleUpDown(float n);
	void reset();

	void addMesh(objMesh* _mesh);


private:
	std::vector<objMesh*> meshes;
	
	glm::mat4 groupMatrix;
};

