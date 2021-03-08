import socket
import pickle
import time

import socket
import exceptions
import math
import argparse

from dronekit import connect, VehicleMode, LocationGlobalRelative, APIException
from pymavlink import mavutil

import os
import cv2
import cv2.aruco as aruco
import numpy as np

os.system("./launchStreamer.sh")
os.system("lt --port 8080 -s mtadroneservice")
sock = socket.socket()
host = "35.242.255.174"
port = 80
sock.connect((host, port))

takeoff_height = 2
velocity = .5
ready_to_land = 0


def connectMyCopter():
    parser = argparse.ArgumentParser(description='commands')
    parser.add_argument('--connect')
    args = parser.parse_args()

    connection_string = args.connect

    if not connection_string:
        connection_string = '127.0.0.1:14550'

    vehicle = connect(connection_string, wait_ready=True)

    return vehicle


def arm_and_takeoff(targetHeight):
    while vehicle.is_armable != True:
        print("[?]Waiting for vehicle to become armable.")
        time.sleep(1)
    print("[+]Vehicle is now armable.")

    vehicle.mode = VehicleMode("GUIDED")

    while vehicle.mode != 'GUIDED':
        print("[?]Waiting for drone to enter GUIDED flight mode.")
        time.sleep(1)
    print("[+]Vehicle now in GUIDED MODE.")

    vehicle.armed = True
    while vehicle.armed == False:
        print("[?]Waiting for vehicle to become armed.")
        time.sleep(1)
    print("[+]Look out!Props are spinning!")

    vehicle.simple_takeoff(targetHeight)

    while True:
        print("[+]Current Altitude: %d" % vehicle.location.global_relative_frame.alt)
        if vehicle.location.global_relative_frame.alt >= .95 * targetHeight:
            break
        time.sleep(1)
    print("[+]Target altitude reached!")

    return None


def send_local_ned_velocity(vx, vy, vz):
    msg = vehicle.message_factory.set_position_target_local_ned_encode(
        0,
        0, 0,
        mavutil.mavlink.MAV_FRAME_BODY_OFFSET_NED,
        0b0000111111000111,
        0, 0, 0,
        vx, vy, vz,
        0, 0, 0,
        0, 0)
    vehicle.send_mavlink(msg)
    vehicle.flush()


def send_land_message(x, y):
    msg = vehicle.message_factory.landing_target_encode(
        0,
        0,
        mavutil.mavlink.MAV_FRAME_BODY_OFFSET_NED,
        x,
        y,
        0,
        0,
        0, )
    vehicle.send_mavlink(msg)
    vehicle.flush()


def landing():
	#if vehicle.mode != 'LAND':
     #   vehicle.mode = VehicleMode("LAND")
      #  while vehicle.mode != 'LAND':
       #     print('Waiting for drone to enter landing mode.')
        #    time.sleep(1)

    #if vehicle.mode != 'LAND':
        #vehicle.mode = VehicleMode('LAND')
        #while vehicle.mode != 'LAND':
            #time.sleep(1)

        #landMessage = bytes("landing", "utf-8")
        #sock.send(landMessage)
        #bytes_angles = str(sock.recv(100))
        #angles_array = bytes_angles.split("'")
        #angles = angles_array[1].split("|")
        #send_land_message(angles[0], angles[1])
    #else:
	while(1):
        	landMessage = bytes("landing", "utf-8")
        	sock.send(landMessage)
        	bytes_angles = str(sock.recv(100))
        	angles_array = bytes_angles.split("'")
        	angles = angles_array[1].split("|")
        	print(angles)
        	send_land_message(angles[0], angles[1])


#vehicle = connectMyCopter()

#vehicle.parameters['PLND_ENABLED'] = 1
#vehicle.parameters['PLND_TYPE'] = 1
#vehicle.parameters['PLND_EST_TYPE'] = 0
#vehicle.parameters['LAND_SPEED'] = 20


#arm_and_takeoff(takeoff_height)

#time.sleep(1)
#ready_to_land = 1

#if ready_to_land == 1:
   # while vehicle.armed == True:
	#landing()

landing()

