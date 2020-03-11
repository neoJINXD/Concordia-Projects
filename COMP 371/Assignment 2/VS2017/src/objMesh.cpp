#include "objMesh.h"

objMesh::objMesh(string path, glm::vec3 _color, glm::vec3 _position, glm::vec3 _scale, glm::vec3 _rotation)
{
	color = _color;
	position = _position;
	rotation = _rotation;
	scale = _scale;
	part = glm::mat4(1.f);
	worldMatrix = glm::mat4(1.f);
	loadObj(path.c_str());
	init();
}

objMesh::~objMesh(){}

void objMesh::draw(Shader* sh, unsigned int type, glm::mat4 groupMatrix)
{
	glUseProgram(sh->shaderProgram);

	int colorInfo = glGetUniformLocation(sh->shaderProgram, "uColor");
	glUniform4f(colorInfo, color.x, color.y, color.z, 1.0f);

	this->Bind();

	unsigned int worldMatrixLocation = glGetUniformLocation(sh->shaderProgram, "worldMatrix");

	updatePartMatrix();
	applyGroup(groupMatrix);

	//draw
	glUniformMatrix4fv(worldMatrixLocation, 1, GL_FALSE, &worldMatrix[0][0]);
	glDrawArrays(type, 0, vertexCount);

	//clean
	this->Unbind();
}

void objMesh::loadObj(const char* path)
{
	std::vector<int> vertexIndices, uvIndices, normalIndices;
	std::vector<glm::vec3> temp_vertices;
	std::vector<glm::vec2> temp_uvs;
	std::vector<glm::vec3> temp_normals;

	FILE* file;
	file = fopen(path, "r");
	if (!file) {
		printf("Impossible to open the file ! Are you in the right path ?\n");
		printf(path);
		exit(-1);
	}

	while (1) {

		char lineHeader[128];
		// read the first word of the line
		int res = fscanf(file, "%s", lineHeader);
		if (res == EOF)
			break; // EOF = End Of File. Quit the loop.

		// else : parse lineHeader

		if (strcmp(lineHeader, "v") == 0) {
			glm::vec3 vertex;
			fscanf(file, "%f %f %f\n", &vertex.x, &vertex.y, &vertex.z);
			temp_vertices.push_back(vertex);
		}
		else if (strcmp(lineHeader, "vt") == 0) {
			glm::vec2 uv;
			fscanf(file, "%f %f\n", &uv.x, &uv.y);
			uv.y = -uv.y; // Invert V coordinate since we will only use DDS texture, which are inverted. Remove if you want to use TGA or BMP loaders.
			temp_uvs.push_back(uv);
		}
		else if (strcmp(lineHeader, "vn") == 0) {
			glm::vec3 normal;
			fscanf(file, "%f %f %f\n", &normal.x, &normal.y, &normal.z);
			temp_normals.push_back(normal);
		}
		else if (strcmp(lineHeader, "f") == 0) {
			int vertexIndex[3], uvIndex[3], normalIndex[3];
			bool uv = true;
			bool norm = true;
			char line[128];
			fgets(line, 128, file);
			//vertex, uv, norm
			int matches = sscanf(line, "%d/%d/%d %d/%d/%d %d/%d/%d\n", &vertexIndex[0], &uvIndex[0], &normalIndex[0], &vertexIndex[1], &uvIndex[1], &normalIndex[1], &vertexIndex[2], &uvIndex[2], &normalIndex[2]);
			if (matches != 9) {
				//vertex, norm
				matches = sscanf(line, "%d//%d %d//%d %d//%d\n", &vertexIndex[0], &normalIndex[0], &vertexIndex[1], &normalIndex[1], &vertexIndex[2], &normalIndex[2]);
				if (matches != 6) {
					//vertex, uv
					matches = sscanf(line, "%d/%d %d/%d %d/%d\n", &vertexIndex[0], &uvIndex[0], &vertexIndex[1], &uvIndex[1], &vertexIndex[2], &uvIndex[2]);
					if (matches != 6) {
						//vertex
						matches = sscanf(line, "%d %d %d\n", &vertexIndex[0], &vertexIndex[1], &vertexIndex[2]);
						if (matches != 6) {
							printf("File can't be read by our simple parser. 'f' format expected: d/d/d d/d/d d/d/d || d/d d/d d/d || d//d d//d d//d\n");
							printf("Character at %ld", ftell(file));
							exit(-1);
						}
						uv, norm = false;
					}
					else {
						norm = false;
					}
				}
				else {
					uv = false;
				}
			}
			vertexIndices.push_back(vertexIndex[0]);
			vertexIndices.push_back(vertexIndex[1]);
			vertexIndices.push_back(vertexIndex[2]);
			if (norm) {
				normalIndices.push_back(normalIndex[0]);
				normalIndices.push_back(normalIndex[1]);
				normalIndices.push_back(normalIndex[2]);
			}
			if (uv) {
				uvIndices.push_back(uvIndex[0]);
				uvIndices.push_back(uvIndex[1]);
				uvIndices.push_back(uvIndex[2]);
			}
		}
		else {
			char clear[1000];
			fgets(clear, 1000, file);
		}

	}
	//std::cout << "Vertex indices: " << vertexIndices.size() << std::endl;
	//std::cout << "UV indices: " << uvIndices.size() << std::endl;
	//std::cout << "Normal indices: " << normalIndices.size() << std::endl;
	// For each vertex of each triangle
	for (unsigned int i = 0; i < vertexIndices.size(); i++) {
		if (uvIndices.size() != 0) {
			if (i < uvIndices.size()) {
				unsigned int uvIndex = abs(uvIndices[i]);
				glm::vec2 uv = temp_uvs[uvIndex - 1];
				out_uvs.push_back(uv);
			}
		}
		if (normalIndices.size() != 0) {
			if (i < normalIndices.size()) {
				unsigned int normalIndex = abs(normalIndices[i]);
				glm::vec3 normal = temp_normals[normalIndex - 1];
				out_normals.push_back(normal);
			}
		}

		unsigned int vertexIndex = abs(vertexIndices[i]);
		glm::vec3 vertex = temp_vertices[vertexIndex - 1];
		out_vertices.push_back(vertex);
	}
}

void objMesh::Bind()
{
	glBindVertexArray(VAO);
	glBindBuffer(GL_ARRAY_BUFFER, vertices_VBO);
	glBindBuffer(GL_ARRAY_BUFFER, normals_VBO);
	glBindBuffer(GL_ARRAY_BUFFER, uvs_VBO);
}

void objMesh::Unbind()
{
	glBindVertexArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glUseProgram(0);
}

void objMesh::init()
{
	//GLuint VAO;
	glGenVertexArrays(1, &VAO);
	glBindVertexArray(VAO); //Becomes active VAO
	// Bind the Vertex Array Object first, then bind and set vertex buffer(s) and attribute pointer(s).

	//Vertex VBO setup
	//GLuint vertices_VBO;
	glGenBuffers(1, &vertices_VBO);
	glBindBuffer(GL_ARRAY_BUFFER, vertices_VBO);
	glBufferData(GL_ARRAY_BUFFER, out_vertices.size() * sizeof(glm::vec3), &out_vertices.front(), GL_STATIC_DRAW);
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid*)0);
	glEnableVertexAttribArray(0);

	//Normals VBO setup
	//GLuint normals_VBO;
	glGenBuffers(1, &normals_VBO);
	glBindBuffer(GL_ARRAY_BUFFER, normals_VBO);
	glBufferData(GL_ARRAY_BUFFER, out_normals.size() * sizeof(glm::vec3), &out_normals.front(), GL_STATIC_DRAW);
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid*)0);
	glEnableVertexAttribArray(1);

	//UVs VBO setup
	//GLuint uvs_VBO;
	glGenBuffers(1, &uvs_VBO);
	glBindBuffer(GL_ARRAY_BUFFER, uvs_VBO);
	glBufferData(GL_ARRAY_BUFFER, out_uvs.size() * sizeof(glm::vec2), &out_uvs.front(), GL_STATIC_DRAW);
	glVertexAttribPointer(2, 2, GL_FLOAT, GL_FALSE, 2 * sizeof(GLfloat), (GLvoid*)0);
	glEnableVertexAttribArray(2);

	glBindVertexArray(0); // Unbind VAO (it's always a good thing to unbind any buffer/array to prevent strange bugs, as we are using multiple VAOs)
	vertexCount = out_vertices.size();
}

void objMesh::applyGroup(glm::mat4 groupMatrix)
{
	worldMatrix = groupMatrix * part;
}

void objMesh::updatePartMatrix()
{
	part = glm::mat4(1.f);

	part = glm::translate(part, position);

	part = glm::rotate(part, glm::radians(rotation.x), glm::vec3(1.f, 0.f, 0.f));
	part = glm::rotate(part, glm::radians(rotation.y), glm::vec3(0.f, 1.f, 0.f));
	part = glm::rotate(part, glm::radians(rotation.z), glm::vec3(0.f, 0.f, 1.f));

	part = glm::scale(part, scale);
}