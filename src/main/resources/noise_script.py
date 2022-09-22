from skimage.io import imsave, imread
from skimage.util import random_noise

image_path = "C:\\IDEA_Projects\\tpzrpLab1\\src\\test\\picture.jpg"
image = imread(image_path)
noise_image = random_noise(image, mode='s&p', amount=0.1)
imsave("C:\\IDEA_Projects\\tpzrpLab1\\src\\test\\noisy_picture.jpg", noise_image)
print("image has successfully changed")

