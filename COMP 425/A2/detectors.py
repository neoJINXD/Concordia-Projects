'''
Computer Vision Winter 2022
Assignment 2

Anik Patel - 40091908
'''

import math
import cv2
from matplotlib import image
import numpy as np
import time

# Part 2 & Part 3
def HarrisCornerDetection(img, windowSize=3, k=0.03, threshold=1000):
    
    height, width = img.shape[:2]
    grayscale = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    
    # compute gradients
    gaussian = cv2.GaussianBlur(grayscale, (3, 3), 0)

    gradX = cv2.Sobel(gaussian, cv2.CV_32F, 1, 0, ksize=3)
    gradY = cv2.Sobel(gaussian, cv2.CV_32F, 0, 1, ksize=3)

    gradXX = gradX**2
    gradYY = gradY**2
    gradXY = gradX*gradY


    matrixR = np.zeros((height, width))
    keypoints = []

    # compute harris matrix
    offset = windowSize//2
    for y in range(offset, height-offset):
        for x in range(offset, width-offset):
            sumXX = np.sum(gradXX[y-offset:y+offset+1, x-offset:x+offset+1])
            sumYY = np.sum(gradYY[y-offset:y+offset+1, x-offset:x+offset+1])
            sumXY = np.sum(gradXY[y-offset:y+offset+1, x-offset:x+offset+1])
            
            H = np.array([[sumXX, sumXY],[sumXY, sumYY]])

            # compute corner response function
            det = sumXX*sumYY - sumXY*sumXY
            trace = sumXX + sumYY

            R = det - k*(trace**2)

            if R > threshold:
                keypoints.append(cv2.KeyPoint(x, y, 1))
                matrixR[y-offset, x-offset] = R

    # thresholding the corner response function
    cv2.normalize(matrixR, matrixR, 0, 255, cv2.NORM_MINMAX)
    
    keypointImg = cv2.drawKeypoints(img, keypoints, None)
    
    concatGrads = np.concatenate((gradX, gradXY, gradY), axis=1)

    return keypointImg, keypoints, matrixR, concatGrads

def MatchPoints(img1, img2, windowSize=3, threshold=10**9, distanceThreshold=0.9):
    '''
        Brute force checking each keypoint with each other keypoint from
        running harris corner detection with Lowe's ratio test (checking the 2 best matches).
    '''

    print(f'Rerunning Harris Corner Detection on both images')
    keypointImg1, keypoints1, _, _ = HarrisCornerDetection(img1, windowSize=windowSize, k=0.03, threshold=threshold)
    keypointImg2, keypoints2, _, _ = HarrisCornerDetection(img2, windowSize=windowSize, k=0.03, threshold=threshold)

    offset = windowSize//2

    matchingPoints = []
    matches1to2 = []
    goodIndex = 0
    index = 0

    print(f'Checking keypoints for matches')
    for point1 in keypoints1:
        x1, y1 = int(point1.pt[0]), int(point1.pt[1])

        bestPoint = None
        bestSSD = np.Infinity
        secondBestSSD = np.Infinity

        # around1 = keypointImg1[y1-offset:y1+offset+1, x1-offset:x1+offset+1].flatten()
        around1 = img1[y1-offset:y1+offset+1, x1-offset:x1+offset+1].flatten()

        for point2 in keypoints2:
            x2, y2 = int(point2.pt[0]), int(point2.pt[1])
            
            # around2 = keypointImg2[y2-offset:y2+offset+1, x2-offset:x2+offset+1].flatten()
            around2 = img2[y2-offset:y2+offset+1, x2-offset:x2+offset+1].flatten()
            # compare each point to see if they match using ssd

            ssd = np.sum((around1 - around2)**2)

            if ssd < bestSSD:
                # print(f'A good match is found')
                secondBestSSD = bestSSD
                bestSSD = ssd
                bestPoint = point2
                goodIndex = index
            
            elif ssd < secondBestSSD:
                secondBestSSD = ssd
            index += 1

        ratio = bestSSD/secondBestSSD

        if ratio < distanceThreshold:
            matchingPoints.append((point1, bestPoint))
            matches1to2.append(goodIndex)
        index = 0



    result = np.concatenate([keypointImg1, keypointImg2], axis=1)

    # connect keypoints from accross the images
    for point in matchingPoints:
        x1, y1 = int(point[0].pt[0]), int(point[0].pt[1])
        x2, y2 = int(point[1].pt[0]), int(point[1].pt[1])

        result = cv2.line(result, (x1, y1), (x2+img1.shape[1], y2), (0, 255, 0), 1)
    # matches = cv2.drawMatches(img1, keypoints1, img2, keypoints2, matches1to2, result)

    return result#, matches

def p23():
    print(f'Running Part 2')

    # mImage = cv2.imread('test.png')
    mImage = cv2.imread('image_sets/yosemite/Yosemite1.jpg')
    mImage2 = cv2.imread('image_sets/yosemite/Yosemite2.jpg')

    print(f'Running Harris Corner Detection')
    start = time.time()
    keypointImg, _, cornerResponse, grads = HarrisCornerDetection(mImage, windowSize=3, k=0.03, threshold=10**10)
    print(f'Time taken: {time.time() - start:.2f}s')
    cv2.imshow('lx, lxy, and ly', grads)
    cv2.imshow('Corner Response', cornerResponse.astype(np.uint8))
    cv2.imshow('Harris Corner Detection', keypointImg)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    

    print(f'Running Part 3')
    # WARNING this part takes ~4-5 minutes to run with the given parameters
    print(f'Running SIFT-like descriptors')
    start = time.time()
    matched = MatchPoints(mImage, mImage2, windowSize=3, threshold=10**10, distanceThreshold=0.7)
    # matched, matchers = MatchPoints(mImage, mImage2, windowSize=3, threshold=10**11)
    print(f'Time taken: {time.time() - start:.2f}s')
    cv2.imshow('Matched Points', matched)
    # cv2.imshow('Matchers Points', matchers)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


if __name__ == "__main__":
    p23()
