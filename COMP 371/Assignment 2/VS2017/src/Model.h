#pragma once

#include "Mesh.h"
#include "Shader.h"
#include <vector>

class Model
{
public:
	Model(std::vector<Mesh> _meshes);
	~Model();

	void draw(Shader sh);

private:
	std::vector<Mesh> meshes;

};

