#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormal;
layout (location = 2) in vec2 aUv;

uniform mat4 worldMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
uniform mat4 lightSpaceMatrix;

out vec3 vertexPos;
out vec3 vertexNormal;
out vec2 vertexUV;
out vec4 FragPosLightSpace;

void main()
{
   vertexPos = vec3(worldMatrix * vec4(aPos, 1.0));
   vertexNormal = mat3(transpose(inverse(worldMatrix))) * aNormal;
   vertexUV = aUv;
   FragPosLightSpace = lightSpaceMatrix * vec4(vertexPos, 1.0);

   gl_Position = projectionMatrix * viewMatrix * worldMatrix * vec4(aPos.x, aPos.y, aPos.z, 1.0);
}