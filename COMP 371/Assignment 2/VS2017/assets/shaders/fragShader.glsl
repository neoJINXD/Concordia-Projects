#version 330 core


in vec3 vertexPos;
in vec2 vertexUV;
in vec3 vertexNormal;
in vec4 FragPosLightSpace;

out vec4 FragColor;

uniform vec4 uColor;
uniform sampler2D diffuseTexture;
uniform vec3 viewPos;
uniform sampler2D shadowMap;

uniform struct Material{
    sampler2D diffuse;
    float specular;
    float shininess;
} material;

uniform struct Light {
    vec3 position;
    vec3 intensities;
} light;

float shadowCalc(vec4 LSFragPos)
{
    vec3 projCoords = LSFragPos.xyz / LSFragPos.w;
    projCoords = projCoords * 0.5 + 0.5;
    float closestDepth = texture(shadowMap, projCoords.xy).r;
    float currentDepth = projCoords.z;

    vec3 normal = normalize(vertexNormal);
    vec3 lightDir = normalize(light.position - vertexPos);
    float bias = max(0.05 * (1.0 - dot(normal, lightDir)), 0.005);

    //PCF
    float shadow = 0.0;
    vec2 texelSize = 1.0 / textureSize(shadowMap, 0);
    for (int x = -1; x <= 1; ++x)
    {
        for(int y = -1; y <= 1; ++y)
        {
            float pcfDepth = texture(shadowMap, projCoords.xy + vec2(x, y) * texelSize).r;
            shadow += currentDepth - bias > pcfDepth ? 1.0 : 0.0;
        }
    }
    shadow /= 9.0;

    if (projCoords.z > 1.0)
        shadow = 0.0;

    return shadow;
}

void main()
{
    vec3 color = texture(diffuseTexture, vertexUV).rgb * uColor.rgb;
    vec3 normal = normalize(vertexNormal);
    vec3 lightColor = vec3(0.3);

    // ambient
    vec3 ambient = 0.3 * color;

    // diffuse
    vec3 lightDir = normalize(light.position - vertexPos);
    float diff = max(dot(lightDir, normal), 0.0);
    vec3 diffuse = diff * lightColor;

    // specular
    vec3 viewDir = normalize(viewPos - vertexPos);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = 0.0;
    vec3 halfwayDir = normalize(lightDir + viewDir);  
    spec = pow(max(dot(normal, halfwayDir), 0.0), 64.0);
    vec3 specular = spec * lightColor;    
    // calculate shadow
    float shadow = shadowCalc(FragPosLightSpace);                      
    vec3 lighting = (ambient + (1.0 - shadow) * (diffuse + specular)) * color;    
    
    FragColor = vec4(lighting, 1.0);

}
//Much help from https://www.tomdalling.com/blog/modern-opengl/07-more-lighting-ambient-specular-attenuation-gamma/