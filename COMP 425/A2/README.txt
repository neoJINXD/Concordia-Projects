COMP 425 Winter 2022
- Assignment 2 by Anik Patel 40091908

How to run:
- Install all the pip requirement using the requirements.txt file
- Run `hough.py` to run part 1 or `detectors.py` to run part 2 and 3

The zip contains the folders with the images given for the assignment
since the link from the pdf of the assignment are broken.

Part1:
By default, the program will run the hough transform on the images
found in the hough folder. The parameters are set to give an adequate 
result from the given images.

Part2:
By default, the program will run the harris corner detection on an image
found in image_sets/yosemite folder.

Part3:
Runs consecutively after Part 2. 
WARNING, with the currently set parameters it will take around 4-5 mins to run.
Runs on both images found in image_sets/yosemite folder.
Could not get the drawMatches function to work, so I am manually drawing lines 
between matched keypoints with cv2.lines.

Part4:
Prerunned jupyter cells are saved so that we can see the results at a glance.
There were a few changes in the first cell, a missing import was added for
matplotlib.pyplot and a change was made to the plotly renderer to make it
work with VSCode. This was tested and still works if running with `jupyter notebook`.