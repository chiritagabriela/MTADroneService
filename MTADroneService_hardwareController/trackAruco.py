#!/usr/bin/env python
import rospy
from sensors_msgs.msg import Image
import cv2
import cv2.aruco as aruco
import sys
import time
import math
import numpy as np
import ros_numpy as rnp

newImage = rospy.Publisher('newImage',Image,queue_size=10)
idToFind = 72
markerSize = 20

arucoDictionary = aruco.getPredefinedDictionary(aruco.DICT_ARUCO_ORIGINAL)
parameters = aruco.DetectorParameters_crete()

horResolution = 640
verResolution = 480

horFov = 62.2 * (math.pi / 180)
verFov = 48.8 * (math.pi / 180)

found = 0
notFound = 0

distCoef = []
cameraMatrix = [[],[],[]]

npDistCoef = np.array(distCoef)
npCameraMatrix = np.array(cameraMatrix)

def subscriber():
    rospy.init_node('droneNode',anonymous=False)