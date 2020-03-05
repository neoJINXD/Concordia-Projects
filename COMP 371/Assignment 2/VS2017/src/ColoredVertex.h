#pragma once

#include <glm/glm.hpp>

struct coloredVertex {
	coloredVertex(glm::vec3 _position, glm::vec3 _normal)
		: position(_position), normal(_normal) {}

	glm::vec3 position;
	glm::vec3 normal;
};