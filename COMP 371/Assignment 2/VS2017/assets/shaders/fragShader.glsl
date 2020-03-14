#version 330 core


in vec3 vertexPos;
in vec2 vertexUV;
in vec3 vertexNormal;
//in vec4 FragPosLightSpace;

out vec4 FragColor;

uniform vec4 uColor;
uniform sampler2D diffuseTexture;
uniform vec3 viewPos;
uniform sampler2D shadowMap;
uniform bool shadows;
uniform float far_plane;

uniform struct Material{
    sampler2D diffuse;
    float specular;
    float shininess;
} material;

uniform struct Light {
    vec3 position;
    vec3 intensities;
} light;


vec3 gridSamplingDisk[20] = vec3[]
(
   vec3(1, 1,  1), vec3( 1, -1,  1), vec3(-1, -1,  1), vec3(-1, 1,  1), 
   vec3(1, 1, -1), vec3( 1, -1, -1), vec3(-1, -1, -1), vec3(-1, 1, -1),
   vec3(1, 1,  0), vec3( 1, -1,  0), vec3(-1, -1,  0), vec3(-1, 1,  0),
   vec3(1, 0,  1), vec3(-1,  0,  1), vec3( 1,  0, -1), vec3(-1, 0, -1),
   vec3(0, 1,  1), vec3( 0, -1,  1), vec3( 0, -1, -1), vec3( 0, 1, -1)
);

float shadowCalc(vec3 fragPos)
{
    vec3 fragToLight = fragPos - light.position;

    float currentDepth = length(fragToLight);

    float shadow = 0.0;
    float bias = 0.15;
    int samples = 20;
    float viewDistance = length(viewPos - fragPos);
    float diskRadius = (1.0 + (viewDistance / far_plane)) / 25.0;
    for(int i = 0; i < samples; ++i)
    {
        float closestDepth = texture(shadowMap, fragToLight + gridSamplingDisk[i] * diskRadius).r;
        closestDepth *= far_plane;   // undo mapping [0;1]
        if(currentDepth - bias > closestDepth)
            shadow += 1.0;
    }
    shadow /= float(samples);
        
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
    vec3 diffuse = diff * lightColor * color;

    // specular
    vec3 viewDir = normalize(viewPos - vertexPos);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = 0.0;
    vec3 halfwayDir = normalize(lightDir + viewDir);  
    spec = pow(max(dot(normal, halfwayDir), 0.0), 64.0);
    vec3 specular = spec * lightColor;    

    //attenuation
    float distToLight = length(light.position - vertexPos);
    float attenuation = 1.0 / (1.0 + 0.0002 * pow(distToLight, 2));

    // calculate shadow
    //float shadow = shadows ? shadowCalc(vertexPos) : 0.0;                      
    float shadow = shadowCalc(vertexPos);                      
    vec3 lighting = (ambient + attenuation * (1.0 - shadow) * (diffuse + specular)) * color;    
    
    FragColor = vec4(lighting, 1.0);

}
//Much help from https://www.tomdalling.com/blog/modern-opengl/07-more-lighting-ambient-specular-attenuation-gamma/