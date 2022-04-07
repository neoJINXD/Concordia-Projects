'''
Computer Vision Winter 2022
Assignment 1

Anik Patel - 40091908
'''

import cv2
import numpy as np
import time

# make Debug True to save images to a file instead of showing it
debug = False
func = cv2.imshow
if debug:
    func = cv2.imwrite

# Part 1

def downscale(image, factor):
    return image[::factor, ::factor, :]

def p1():
    mImage = cv2.imread('santorini.jpeg')

    #downscaling using my function
    print(f'Running Downscale')
    start = time.time()
    mImage2 = downscale(mImage, 2)
    mImage4 = downscale(mImage, 4)
    mImage8 = downscale(mImage, 8)
    mImage16 = downscale(mImage, 16)
    print(f'Time taken: {time.time() - start:.2f}s')

    func('downscaled2.jpg', mImage2)
    func('downscaled4.jpg', mImage4)
    func('downscaled8.jpg', mImage8)
    func('downscaled16.jpg', mImage16)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    #upscaling using opencv
    print(f'Running Upscale')
    start = time.time()
    upscaledN = cv2.resize(mImage16, (2160,1410), interpolation=cv2.INTER_NEAREST)
    upscaledL = cv2.resize(mImage16, (2160,1410), interpolation=cv2.INTER_LINEAR)
    upscaledB = cv2.resize(mImage16, (2160,1410), interpolation=cv2.INTER_CUBIC)
    print(f'Time taken: {time.time() - start:.2f}s')


    func('upscaledNearest.jpg', upscaledN)
    func('upscaledBilinear.jpg', upscaledL)
    func('upscaledBicubic.jpg', upscaledB)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    # saves the 8 times downscaled image for future use
    cv2.imwrite('downscaled8.jpg', mImage8)

# Part 2

def shiftImage(image, x, y):
    rows, cols = image.shape[:2]
    newImage = np.zeros((rows, cols, 3), np.uint8)
    for i in range(rows):
        for j in range(cols):
            if i+y >= rows or j-x >= cols or i+y < 0 or j-x < 0:
                continue
            newImage[i, j] = image[i+y, j-x]
    return newImage

def gaussian(x, y, sigma):
    return 1/(2*np.pi*sigma**2) * np.exp(-(x**2 + y**2)/(2*sigma**2))

def applyGaussianFilter(image, neighbourhoodSize, sigma):
    rows, cols, color = image.shape[:3]
    
    newImage = np.zeros_like(image, dtype=np.float32)

    offset = neighbourhoodSize // 2
    gaussianFilter = np.zeros((neighbourhoodSize, neighbourhoodSize), np.float32)

    for x in range(0, neighbourhoodSize):
        for y in range(0, neighbourhoodSize):
            gaussianFilter[x, y] = gaussian(x, y, sigma)
    
    for x in range(offset, rows-offset):
        for y in range(offset, cols-offset):
            for c in range(color):
                t = image[x-offset:x+offset+1, y-offset:y+offset+1, c]
                newImage[x, y, c] = np.sum(np.multiply(gaussianFilter, t)) / np.sum(gaussianFilter)

    return newImage.astype(np.uint8)

def gaussianDifference(image, sigma1, sigma2):
    gaus1 = applyGaussianFilter(image, 5, sigma1)
    gaus2 = applyGaussianFilter(image, 5, sigma2)
    return gaus1 - gaus2

def p2():
    mImage = cv2.imread('downscaled8.jpg')

    print(f'Running Image Shift')
    start = time.time()
    mImageShifted = shiftImage(mImage, 100, 100)
    print(f'Time taken: {time.time() - start:.2f}s')
    func('shifted.jpg', mImageShifted)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    print(f'Running Gaussian Blur')
    start = time.time()
    mImageGaussian = applyGaussianFilter(mImage, 5, 5)
    print(f'Time taken: {time.time() - start:.2f}s')
    func('gaussian.jpg', mImageGaussian)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    
    print(f'Running Difference of Gaussian')
    start = time.time()
    mImageGaussianDifference = gaussianDifference(mImage, 1, 5)
    print(f'Time taken: {time.time() - start:.2f}s')
    func('gaussianDifference.jpg', mImageGaussianDifference)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# Part 3

def sobel(image):
    rows, cols, color = image.shape[:3]
    kernelHorizontal = np.array([[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]])
    kernelVertical = np.array([[-1, -2, -1], [0, 0, 0], [1, 2, 1]])

    newImageH = np.zeros_like(image, dtype=np.float32)
    newImageV = np.zeros_like(image, dtype=np.float32)
    newImage = np.zeros_like(image, dtype=np.float32)

    for i in range(1, rows-1):
        for j in range(1, cols-1):
            for c in range(color):
                newImageH[i, j, c] = np.sum(np.multiply(kernelHorizontal, image[i-1:i+2, j-1:j+2, c]))
                newImageV[i, j, c] = np.sum(np.multiply(kernelVertical, image[i-1:i+2, j-1:j+2, c]))

    newImage = np.concatenate((newImageH, newImageV), axis=1).astype(np.float32)

    return newImage, newImageH, newImageV;

def sobelQ(image):
    rows, cols, color = image.shape[:3]
    kernelHorizontal = np.array([[1, 0, -1], [2, 0, -2], [1, 0, -1]])
    kernelVertical = np.array([[1, 2, 1], [0, 0, 0], [-1, -2, -1]])

    newImageH = np.zeros_like(image, dtype=np.float32)
    newImageV = np.zeros_like(image, dtype=np.float32)
    newImage = np.zeros_like(image, dtype=np.float32)

    for i in range(1, rows-1):
        for j in range(1, cols-1):
            for c in range(color):
                newImageH[i, j, c] = np.sum(np.multiply(kernelHorizontal, image[i-1:i+2, j-1:j+2, c]))
                newImageV[i, j, c] = np.sum(np.multiply(kernelVertical, image[i-1:i+2, j-1:j+2, c]))

    newImage = np.concatenate((newImageH, newImageV), axis=1).astype(np.float32)

    return newImage, newImageH, newImageV;

def orientation(image):
    _, sobelHorizontal, sobelVertical = sobelQ(image)

    return np.arctan2(sobelVertical, sobelHorizontal, dtype=np.float32).astype(np.uint8)

def gradientMagnitude(image):
    _, sobelHorizontal, sobelVertical = sobelQ(image)

    mag = np.sqrt(np.square(sobelHorizontal) + np.square(sobelVertical), dtype=np.float32)
    # temp = temp * 255.0 / temp.max()
    return (mag * 255.0 / mag.max()).astype(np.uint8)


def p3():
    mImage = cv2.imread('downscaled8.jpg')

    # display filtered image wuth sorbel operators
    print(f'Running Sobel')
    start = time.time()
    sobelImage, _, _ = sobel(mImage)
    print(f'Time taken: {time.time() - start:.2f}s')
    func('edgeSobel.jpg', sobelImage)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    # display orientation map for each pixel
    print(f'Running Orientation Map')
    start = time.time()
    orientationImage = orientation(mImage)
    print(f'Time taken: {time.time() - start:.2f}s')
    func('edgeOrientation.jpg', orientationImage)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    # display gradient magniture at eaach pixel
    print(f'Running Gradient Magnitude')
    start = time.time()
    gradientImage = gradientMagnitude(mImage)
    print(f'Time taken: {time.time() - start:.2f}s')
    func('edgeGradient.jpg', gradientImage)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    # display edge map with opencv canny function
    print(f'Running OpenCV Canny')
    start = time.time()
    edgeImage = cv2.Canny(mImage, 50, 200)
    print(f'Time taken: {time.time() - start:.2f}s')
    func('edgeCanny.jpg', edgeImage)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# Part 4

import torch as T
import torchvision as tv
import matplotlib.pyplot as plt

def downloadCifar10():
    return tv.datasets.CIFAR10(root='./data', train=True, download=True, transform=tv.transforms.ToTensor())

def imshow(img, name='image'):
    img = img / 2 + 0.5
    npimg = img.numpy()
    plt.title(name)
    plt.imshow(np.transpose(npimg, (1, 2, 0))) 
    plt.show()

def showUnique(image, name, label, tracking, count):
    tracking.append(label)
    count[0] += 1
    imshow(image, name)

def p4():
    print(f'Running Cifar10 with PyTorch')
    temp = downloadCifar10()
    print(f'Downloading dataset if not found')
    start = time.time()
    trainloader = T.utils.data.DataLoader(temp, batch_size=100, shuffle=True)
    print(f'Time taken: {time.time() - start:.2f}s')
    
    # list of all classes for reference
    classes = ['plane', 'car', 'bird', 'cat', 'deer', 'dog', 'frog', 'horse', 'ship', 'truck']
    
    tracking = []
    count = [0]

    print(f'Displaying images from each class')
    for i, data in enumerate(trainloader):
        if count[0] == 10: 
            return
        inputs, labels = data
        if (labels[i] == 0 or labels[i] == 1 or labels[i] == 2 or labels[i] == 3 or labels[i] == 4 or labels[i] == 5 or labels[i] == 6 or labels[i] == 7 or labels[i] == 8 or labels[i] == 9) and not labels[i] in tracking:
            showUnique(inputs[i], classes[labels[i]], labels[i], tracking, count)

# Part 5 OPTIONAL

def applyGaussianFilterGray(image, neighbourhoodSize, sigma):
    rows, cols = image.shape[:2]
    
    newImage = np.zeros_like(image, dtype=np.float32)

    offset = neighbourhoodSize // 2
    gaussianFilter = np.zeros((neighbourhoodSize, neighbourhoodSize), np.float32)

    for x in range(0, neighbourhoodSize):
        for y in range(0, neighbourhoodSize):
            gaussianFilter[x, y] = gaussian(x, y, sigma)
    
    for x in range(offset, rows-offset):
        for y in range(offset, cols-offset):
            t = image[x-offset:x+offset+1, y-offset:y+offset+1]
            newImage[x, y] = np.sum(np.multiply(gaussianFilter, t)) / np.sum(gaussianFilter)

    return newImage.astype(np.uint8)

def sobelGray(image):
    rows, cols = image.shape[:2]
    kernelHorizontal = np.array([[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]])
    kernelVertical = np.array([[-1, -2, -1], [0, 0, 0], [1, 2, 1]])

    newImageH = np.zeros_like(image, dtype=np.float32)
    newImageV = np.zeros_like(image, dtype=np.float32)
    newImage = np.zeros_like(image, dtype=np.float32)

    for i in range(1, rows-1):
        for j in range(1, cols-1):
            newImageH[i, j] = np.sum(np.multiply(kernelHorizontal, image[i-1:i+2, j-1:j+2]))
            newImageV[i, j] = np.sum(np.multiply(kernelVertical, image[i-1:i+2, j-1:j+2]))

    newImage = np.concatenate((newImageH, newImageV), axis=1).astype(np.float32)

    return newImage, newImageH, newImageV;

def orientationGray(image):
    _, sobelHorizontal, sobelVertical = sobelGray(image)

    return np.arctan2(sobelVertical, sobelHorizontal, dtype=np.float32).astype(np.uint8)

def gradientMagnitudeGray(image):
    _, sobelHorizontal, sobelVertical = sobelGray(image)

    mag = np.sqrt(np.square(sobelHorizontal) + np.square(sobelVertical), dtype=np.float32)
    # temp = temp * 255.0 / temp.max()
    return (mag * 255.0 / mag.max()).astype(np.uint8)

def nonMaxSuppression(magnitude, orientation):
    rows, cols = magnitude.shape[:2]
    newImage = np.zeros_like(magnitude, dtype=np.float32)

    for i in range(1, rows-1):
        for j in range(1, cols-1):
            dir = orientation[i, j]

            # check left or right pixel
            if (0 <= dir < 180 / 8) or (15 * 180 / 8 <= dir <= 2 * 180):
                before_pixel = magnitude[i, j - 1]
                after_pixel = magnitude[i, j + 1]
 
            # check topleft or bottomright pixel
            elif (180 / 8 <= dir < 3 * 180 / 8) or (9 * 180 / 8 <= dir < 11 * 180 / 8):
                before_pixel = magnitude[i + 1, j - 1]
                after_pixel = magnitude[i - 1, j + 1]

            # check top or bottom pixel
            elif (3 * 180 / 8 <= dir < 5 * 180 / 8) or (11 * 180 / 8 <= dir < 13 * 180 / 8):
                before_pixel = magnitude[i - 1, j]
                after_pixel = magnitude[i + 1, j]
    
            # check bottomleft or topright pixel
            else:
                before_pixel = magnitude[i - 1, j - 1]
                after_pixel = magnitude[i + 1, j + 1]
    
            if magnitude[i, j] >= before_pixel and magnitude[i, j] >= after_pixel:
                newImage[i, j] = magnitude[i, j]

    return newImage

def hysteresisThreshold(image, low, high):
    pass


def cannyEdgeDetection(image, threshold):
    # apply gaussian blur
    gaussian = applyGaussianFilterGray(image, 3, 5)

    # find magnitude and orientation of gradient
    magnitude, ori = gradientMagnitudeGray(gaussian), orientationGray(gaussian)
    
    # apply non max suppression
    print(f'Applying non max suppression')
    start = time.time()
    nonMax = nonMaxSuppression(magnitude, ori)
    print(f'Time taken: {time.time() - start:.2f}s')

    func('nonMaxSuppression.jpg', nonMax)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    # apply Hysteresis threshold
    hysteresis = hysteresisThreshold(image, 5, 20)

def p5():
    mImage = cv2.imread('downscaled8.jpg', cv2.IMREAD_GRAYSCALE)
    cannyEdgeDetection(mImage, 50);


if __name__ == '__main__':
    # p1()
    # p2()
    p3()
    # p4()
    # p5()

