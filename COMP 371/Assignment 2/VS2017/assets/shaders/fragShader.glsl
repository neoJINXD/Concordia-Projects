#version 330 core
in vec3 vertexColor;
in vec2 vertexUV;

out vec4 FragColor;

uniform vec4 uColor;
uniform sampler2D textureSampler;

// vec4(vertexColor.r, vertexColor.g, vertexColor.b, 1.0f)
void main()
{
   vec4 textureColor = texture( textureSampler, vertexUV );
   FragColor = textureColor * vec4(uColor.r, uColor.g, uColor.b, uColor.a);
}