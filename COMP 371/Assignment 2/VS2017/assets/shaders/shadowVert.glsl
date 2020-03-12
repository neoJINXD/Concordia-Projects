#version 330 core
layout (location = 0) in vec3 aPos;

uniform mat4 lightSpaceMatrix = mat4(1.0f);
uniform mat4 worldMatrix;

void main()
{
    gl_Position = lightSpaceMatrix * worldMatrix * vec4(aPos, 1.0);
}  