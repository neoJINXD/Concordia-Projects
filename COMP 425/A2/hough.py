'''
Computer Vision Winter 2022
Assignment 2

Anik Patel - 40091908
'''

import cv2
from matplotlib import image
import numpy as np
import time


# Part 1
def HoughTransform(img):
    edges = cv2.Canny(img, 100, 200, 3)

    width, height = img.shape[:2]

    diag = int(np.sqrt(width**2 + height**2))

    thetas = np.arange(0, 180)
    cosTheta = np.cos(np.deg2rad(thetas))
    sinTheta = np.sin(np.deg2rad(thetas))

    houghSpace = np.zeros((diag*2, len(thetas)), dtype=np.int8)

    for y in range(height):
        for x in range(width):
            if (edges[y,x] > 0):
                for theta in thetas:
                    rho = round(x*cosTheta[theta] + y*sinTheta[theta])
                    houghSpace[rho, theta] += 1

    return houghSpace

def DrawHoughLines(img, houghSpace, threshold=40):
    ''' 
    Reverse engineering the lines from the hough space
    From OpenCV docs: https://opencv24-python-tutorials.readthedocs.io/en/latest/py_tutorials/py_imgproc/py_houghlines/py_houghlines.html
    '''
    lines = []
    
    for rho in range(houghSpace.shape[0]):
        for theta in range(houghSpace.shape[1]):
            if houghSpace[rho, theta] > threshold:
                lines.append((rho, theta))

    for rho, theta in lines:
        a = np.cos(np.deg2rad(theta))
        b = np.sin(np.deg2rad(theta))

        xp = a*rho
        yp = b*rho

        x1 = int(xp + 1000*(-b))
        y1 = int(yp + 1000*(a))
        x2 = int(xp - 1000*(-b))
        y2 = int(yp - 1000*(a))

        img = cv2.line(img, (x1, y1), (x2, y2), (0, 0, 255), 1)

    return img

def p1():
    print(f'Running Part 1')

    # mImage = cv2.imread('test.png')
    mImage = cv2.imread('hough/hough1.png')
    mImage2 = cv2.imread('hough/hough2.png')

    print(f'Running Hough Transform')
    start = time.time()
    hough = HoughTransform(mImage)
    print(f'Time taken: {time.time() - start:.2f}s')
    print(f'Running Hough Transform on 2nd Image')
    start = time.time()
    hough2 = HoughTransform(mImage2)
    print(f'Time taken: {time.time() - start:.2f}s')

    cv2.imshow('Hough Space', hough)
    cv2.imshow('Hough Lines', DrawHoughLines(mImage, hough, 25))
    cv2.imshow('Hough Space 2', hough2)
    cv2.imshow('Hough Lines 2', DrawHoughLines(mImage2, hough2, 75))
    cv2.waitKey(0)
    cv2.destroyAllWindows()



if __name__ == "__main__":
    p1()
