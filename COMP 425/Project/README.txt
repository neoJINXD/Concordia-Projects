COMP 425 Winter 2022
- Project by Anik Patel 40091908

How to run:
- Install all the dependencies that are used in the first cell of imports
- Run each cell in order

Part 0:
Run each of the cells to allow for the program to download the KMNIST dataset
and run training and testing on it using a feed forward neural network in the next Part.

Part 1:
Defines the 3 Activation Functions that can be used in the feed forward neural network:
Sigmoid, ReLU, and Identity. We also implement Cross Entropy as the loss function.

WARNING: I chould not get the Cross Entropy to work properly with the networks so for now
I am using the built-in one provided by PyTorch.

The `my_nn` class implements a feed forward neural network from scratch and initializes
the weights and biases of the network with the given requirements. We run the training over
10 epochs and with a learning rate of 0.001.

Part 2:
Runs a Convolutional Neural Network over the Stanford Dogs Dataset. Since I am an Undergrad,
I am using nn.Module as a base for my neural network. The weights are initialized using
Xavier initialization. Information about how to do this was obtained from the official PyTorch
documentation. We run the training over 20 epochs and with a learning rate of 0.001.

The 2nd half of this part uses an imported pretrained model from the torchvision library.
We select 20 random filters from the pretrained model and visualize and apply them to a 
sample image taken from the dogs dataset.