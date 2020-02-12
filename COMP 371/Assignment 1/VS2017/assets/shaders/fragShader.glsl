#version 330 core
in vec3 vertexColor;
out vec4 FragColor;

uniform vec4 uColor;
// vec4(vertexColor.r, vertexColor.g, vertexColor.b, 1.0f)
void main()
{
   FragColor = uColor;
}