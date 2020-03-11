#include "objModel.h"

objModel::objModel()
{
	groupMatrix = glm::mat4(1.f); 
	//scale = glm::vec3(1.f);
}

objModel::~objModel(){}

void objModel::draw(Shader* sh, unsigned int type)
{
	for (auto* i : meshes)
	{
		i->updatePartMatrix();
		i->applyGroup(groupMatrix);
		i->draw(sh, type);
	}
}

void objModel::rotateBy(float x, float y, float z)
{
	groupMatrix = glm::rotate(groupMatrix, glm::radians(x), glm::vec3(1.f, 0.f, 0.f));
	groupMatrix = glm::rotate(groupMatrix, glm::radians(y), glm::vec3(0.f, 1.f, 0.f));
	groupMatrix = glm::rotate(groupMatrix, glm::radians(z), glm::vec3(0.f, 0.f, 1.f));
}

void objModel::randomizePos()
{
	float newX = (std::rand() % 100) - 50;
	float newZ = (std::rand() % 100) - 50;

	//std::cout << "new x: " << newX << " new z: " << newZ << std::endl;
	moveBy(newX, 0, newZ);
}

void objModel::moveBy(float x, float y, float z)
{
	groupMatrix = glm::translate(groupMatrix, glm::vec3(x, y, z));
}

void objModel::scaleUpDown(float n)
{
	groupMatrix = glm::scale(groupMatrix, glm::vec3(n));
}

void objModel::addMesh(objMesh* _mesh)
{
	meshes.push_back(_mesh);
}

void objModel::reset()
{
	groupMatrix = glm::mat4(1.f);
}