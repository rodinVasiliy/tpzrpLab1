import cv2
import numpy as np
import scipy.ndimage
from skimage.io import imread, imsave

image_path = "C:\\IDEA_Projects\\tpzrpLab1\\src\\test\\new_picture.jpg"
footprint = np.array([[0, 1, 0], [1, 3, 1], [0, 1, 0]])
image = imread(image_path)
b, g, r = cv2.split(image)
median_image_b = scipy.ndimage.median_filter(b, footprint=footprint)
median_image_g = scipy.ndimage.median_filter(g, footprint=footprint)
median_image_r = scipy.ndimage.median_filter(r, footprint=footprint)
result_median_image = cv2.merge([median_image_b, median_image_g, median_image_r])
imsave("C:\\IDEA_Projects\\tpzrpLab1\\src\\test\\processed_picture.jpg", result_median_image)
