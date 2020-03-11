#version 330 core

struct Material{
    sampler2D diffuse;
    sampler2D specular;
    float shiny;
};

struct PointLight{
    vec3 pos;

    float constant;
    float linear;
    float quadratic;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

in vec3 vertexNormal;
in vec2 vertexUV;
in vec3 vertexPos;

out vec4 FragColor;

uniform vec4 uColor;
uniform sampler2D textureSampler;
uniform vec3 ambientColor = vec3(1.0f);

uniform vec3 viewPos;
uniform Material mat;
uniform PointLight pointLight;

vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir);


void main()
{
   vec3 norm = normalize(vertexNormal);
   vec3 viewDir = normalize(viewPos - vertexPos);

   // ambient
   float ambientStrength = 0.1;
   vec3 ambient = ambientStrength * ambientColor;
   vec4 ambientVec = vec4(ambient.r, ambient.g, ambient.b, 1.0f);

   vec4 textureColor = texture( textureSampler, vertexUV );

   vec3 result = CalcPointLight(pointLight, norm, vertexPos, viewDir);
   
   FragColor = vec4(result, 1.0f) * uColor;
   //FragColor = ambientVec * textureColor * vec4(uColor.r, uColor.g, uColor.b, uColor.a);
}

//from learnOpenGL
vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir)
{
   vec3 lightDir = normalize(light.pos - fragPos);
   //diffuse
   float diff = max(dot(normal, lightDir), 0.0);
   //specular
   vec3 reflectDir = reflect(-lightDir, normal);
   float spec = pow(max(dot(viewDir, reflectDir), 0.0), mat.shiny);
   //attenuation
   float distance = length(light.pos - fragPos);
   float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));

   //polymerization!
   vec3 ambient = light.ambient * vec3(texture(mat.diffuse, vertexUV));
   vec3 diffuse = light.diffuse * diff * vec3(texture(mat.diffuse, vertexUV));
   vec3 specular = light.specular * spec * vec3(texture(mat.diffuse, vertexUV));
   ambient *= attenuation;
   diffuse *= attenuation;
   specular *= attenuation;

   return (ambient + diffuse + specular);

}

