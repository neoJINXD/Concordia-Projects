#pragma once

#include <glm/glm.hpp>

struct coloredVertex {
	coloredVertex(glm::vec3 _position, glm::vec3 _color)
		: position(_position), color(_color) {}

	glm::vec3 position;
	glm::vec3 color;
};