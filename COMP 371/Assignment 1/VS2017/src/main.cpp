#include <iostream>

#include "lab02.h"
#include "Shader.h"


int main() {
	std::cout << "yes" << std::endl;
	//return run();

	Shader* sh = new Shader();
	sh->printV();

	delete sh;
	sh = nullptr;

	return 0;
}