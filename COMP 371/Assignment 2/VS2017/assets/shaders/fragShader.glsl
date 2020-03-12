#version 330 core


in vec3 vertexPos;
in vec2 vertexUV;
in vec3 vertexNormal;

out vec4 FragColor;

uniform vec4 uColor;
uniform sampler2D textureSampler;
uniform vec3 viewPos;

uniform struct Material{
    sampler2D diffuse;
    float specular;
    float shininess;
} material;

uniform struct Light {
    vec3 position;
    vec3 intensities;
} light;

void main()
{
   vec3 normal = normalize(vertexNormal);
   vec3 fragPosition = vertexPos;
   vec4 surfaceColor = texture(textureSampler, vertexUV);
   vec3 surfaceToLight = normalize(light.position - fragPosition);
   vec3 surfaceToCam = normalize(viewPos - fragPosition);

   // ambient
   float ambientStrength = 0.2;
   vec3 ambient = ambientStrength * surfaceColor.rgb * light.intensities;

   //diffuse
   //float brightness = dot(normal, surfaceToLight) / (length(surfaceToLight) * length(normal));
   //brightness = clamp(brightness, 0, 1);
   float diffuseCoef = max(0.0, dot(normal, surfaceToLight));
   vec3 diffuse = diffuseCoef * surfaceColor.rgb * light.intensities;

   //specular
   //float specularStrength = 0.5;
   float specularCoef = 0.0;
   vec3 reflected = normalize(reflect(surfaceToLight, normal));
   if (diffuseCoef > 0.0)
       specularCoef = pow(max(0.0, dot(surfaceToCam, reflected)), 256);
   vec3 specular = specularCoef * light.intensities;


   //attenuation
   float distToLight = length(light.position - fragPosition);
   float attenuation = 1.0 / (1.0 + 0.0002 * pow(distToLight, 2));

   //linear color
   vec3 linearColor = attenuation*(ambient + diffuse + specular);

   vec3 gamma = vec3(1.0/2.2);
   
   FragColor = vec4(ambient + diffuse + specular, surfaceColor.a) * uColor;
   //FragColor = vec4(pow(linearColor, gamma), surfaceColor.a) * uColor;
   //FragColor = textureColor * uColor;

}
//Much help from https://www.tomdalling.com/blog/modern-opengl/07-more-lighting-ambient-specular-attenuation-gamma/