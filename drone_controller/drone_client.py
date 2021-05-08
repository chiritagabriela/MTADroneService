#####################LIBRARIES################################
import requests
import time
import socket
import exceptions
import math
import argparse
from dronekit import connect, VehicleMode,LocationGlobalRelative,APIException
from pymavlink import mavutil
import cv2
import cv2.aruco as aruco
import numpy as np
from imutils.video import WebcamVideoStream
import imutils
import threading
import os
import json
import re
import uuid
import subprocess

####################GLOBAL VARIABLES##########################

id_to_find = 82
marker_size = 28
takeoff_height = 3
velocity = .5

aruco_dict = aruco.getPredefinedDictionary(aruco.DICT_ARUCO_ORIGINAL)
parameters = aruco.DetectorParameters_create()

horizontal_res = 640
vertical_res = 480

horizontal_fov = 62.2 * (math.pi / 180 )
vertical_fov = 48.8 * (math.pi / 180)

calib_path="/home/pi/MTADroneService/drone_controller/"
cameraMatrix   = np.loadtxt(calib_path+'cameraMatrix.txt', delimiter=',')
cameraDistortion   = np.loadtxt(calib_path+'cameraDistortion.txt', delimiter=',')
ready_to_land = 1
mission_finished = 0
video_url = ""
stream_locally = 0
video_sent = 0
lat_base = ""
lon_base = ""
mission_started = 0

####################FUNCTIONS################################

def connectMyCopter():

	parser = argparse.ArgumentParser(description='commands')
	parser.add_argument('--connect')
	args = parser.parse_args()

	connection_string = args.connect

	if not connection_string:
		connection_string='127.0.0.1:14550'

	vehicle = connect(connection_string,wait_ready=True)

	return vehicle

def arm_and_takeoff(targetHeight):

	while vehicle.is_armable!=True:
		print("[+]Waiting for vehicle to become armable.")
		time.sleep(1)
	print("[+]Vehicle armable.")

	vehicle.mode = VehicleMode("GUIDED")

	while vehicle.mode!='GUIDED':
		print("[+]Waiting for drone to enter GUIDED flight mode/")
		time.sleep(1)
	print("[+]Vehicle in GUIDED MODE.")

	vehicle.armed = True

        while vehicle.armed==False:
        	print("[+]Waiting for vehicle to become armed.")
        	time.sleep(1)
    	print("[+]Props are spinning!")
	vehicle.simple_takeoff(targetHeight)
	while True:
		print("Current Altitude: %d"%vehicle.location.global_relative_frame.alt)
		if vehicle.location.global_relative_frame.alt>=.95*targetHeight:
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

def send_land_message(x,y):

	msg = vehicle.message_factory.landing_target_encode(
        	0,
        	0,
       		mavutil.mavlink.MAV_FRAME_BODY_OFFSET_NED,
       	 	x,
        	y,
        	0,
        	0,
        	0,)
    	vehicle.send_mavlink(msg)
    	vehicle.flush()

def lander(cap):

    	frame = cap.read()
    	frame = cv2.resize(frame,(horizontal_res,vertical_res))
    	frame_np = np.array(frame)
    	gray_img = cv2.cvtColor(frame_np,cv2.COLOR_BGR2GRAY)
    	ids=''
    	corners, ids, rejected = aruco.detectMarkers(image=gray_img,dictionary=aruco_dict,parameters=parameters)

	if vehicle.mode!='LAND':
        	vehicle.mode=VehicleMode("LAND")
        	while vehicle.mode!='LAND':
            		print('[+]Waiting for drone to enter landing mode.')
            		time.sleep(1)
	try:

        	if ids is not None and ids[0] == id_to_find:
            		ret = aruco.estimatePoseSingleMarkers(corners,marker_size,cameraMatrix=cameraMatrix,distCoeffs=cameraDistortion)
            		(rvec, tvec) = (ret[0][0, 0, :], ret[1][0, 0, :])
            		x = '{:.2f}'.format(tvec[0])
            		y = '{:.2f}'.format(tvec[1])
            		z = '{:.2f}'.format(tvec[2])

            		y_sum = 0
            		x_sum = 0

            		x_sum = corners[0][0][0][0]+ corners[0][0][1][0]+ corners[0][0][2][0]+ corners[0][0][3][0]
            		y_sum = corners[0][0][0][1]+ corners[0][0][1][1]+ corners[0][0][2][1]+ corners[0][0][3][1]

            		x_avg = x_sum*.25
            		y_avg = y_sum*.25

            		x_ang = (x_avg - horizontal_res*.5)*(horizontal_fov/horizontal_res)
            		y_ang = (y_avg - vertical_res*.5)*(vertical_fov/vertical_res)

            		if vehicle.mode!='LAND':
                		vehicle.mode = VehicleMode('LAND')
                	while vehicle.mode!='LAND':
                    		time.sleep(1)
                	print("[+]Vehicle now in LAND mode.")
                	send_land_message(x_ang,y_ang)
            	else:
                	send_land_message(x_ang,y_ang)
                	pass
    	except Exception as e:
        	print('[-]Target likely not found. Error: '+str(e))


def get_mac_address():

	string_mac = ""
	mac = re.findall('..', '%012x' % uuid.getnode())
	for i in range(0,len(mac)-1):
		string_mac = string_mac + mac[i] +  ":"
	return string_mac + mac[len(mac)-1]

def kill_process(name):

	try:
		for line in os.popen("ps ax | grep " + name + " | grep -v grep"):
			fields = line.split()
			pid = fields[0]
			os.system("sudo kill -9 " + pid)
	except:
		print("[-]Error killing process "+name+".")

	print("[+]"+"Process " + name + " was killed.")

def start_stream_locally_thread2(name):

	print("[+]Sending video to local host..")
	os.system('./launchStreamer.sh')

def update_status(status):

	print("[+]Mission status updated to " + status)
	headers = {'Content-Type':'application/json'}
	url_to_send = 'http://172.20.10.2:8888/communication/update_mission_status/' + get_mac_address() + '/' + status
	response = requests.post(url_to_send, headers = headers)

def main_programme_thread4(name):

	global lon_base
	global lat_base
	global mission_finished
	global mission_started
	lat_to_go = ""
	lon_to_go = ""
	mission_type = ""

	#waiting for mission to be started
	while(lat_to_go == ""):
		time.sleep(3)
		response_coord = requests.get('http://172.20.10.2:8888/communication/coordinates_to_go/' + get_mac_address())
		lat_to_go = response_coord.json()['latitudeEnd']
		lon_to_go = response_coord.json()['longitudeEnd']
		mission_type = response_coord.json()['missionType']

	#sending video URL to cloud virtual machine
	mission_started = 1
	print("[+]Mission SAR started at coordinates:{lat:"+lat_to_go+"-lon:"+lon_to_go+"}.")
	send_video_url()

	#arming and taking off
	arm_and_takeoff(takeoff_height)
	time.sleep(1)
	print("[+]Drone up and running.")

	#going to first point UP-RIGHT
	update_status("FLYING_TO_INTEREST_POINT")
	point_up_right_search = LocationGlobalRelative(float(lat_to_go), float(lon_to_go), float(takeoff_height))
	vehicle.simple_goto(point_up_right_search)

	update_status("SEARCHING_PERSON")

	#going UP_LEFT
	new_lon = float(lon_to_go) - 0.0000449/math.cos(float(lat_to_go))
	point_up_left_search = LocationGlobalRelative(float(lat_to_go), new_lon, float(takeoff_height))
	vehicle.simple_goto(point_up_left_search)

	#going DOWN_LEFT
	new_lat = float(lat_to_go) - 0.0000449
	point_down_left_search = LocationGlobalRelative(new_lat, new_lon, float(takeoff_height))
	vehicle.simple_goto(point_down_left_search)

	#going DOWN_RIGHT
	point_down_right_search = LocationGlobalRelative(new_lat, new_lon + 0.0000449/math.cos(new_lat), float(takeoff_height))
	vehicle.simple_goto(point_down_right_search)

	#going UP_RIGHT
	point_up_right_search = LocationGlobalRelative(float(lat_to_go), float(lon_to_go), float(takeoff_height))
	vehichle.simple_goto(point_up_right_search)

	#returning to BASE
	update_status("FLYING_TO_BASE")
	point_to_return = LocationGlobalRelative(float(lat_base), float(lon_base), float(takeoff_height))
	vehicle.simple_goto(point_to_return)

	ready_to_land = 1
	update_status("LANDING")
	print("[+]Drone ready for landing.")
	if ready_to_land == 1:
		kill_process("mtadroneservice")
		kill_process("mjpg_streamer")
		cap =  WebcamVideoStream(src=0, width=horizontal_res, height=vertical_res).start()
		while(vehicle.armed == True):
			lander(cap)
		update_status("FINISHED")
		mission_finished = 1

def send_pos_to_server_thread1(name):
	global mission_finished
	global mission_started
	while(mission_finished == 0):
		if( mission_started == 1):
			time.sleep(3)
			headers ={'Content-Type': 'application/json'}
			data_to_send = { 'currentLatitude':vehicle.location.global_relative_frame.lat,'currentLongitude':vehicle.location.global_relative_frame.lon }
			url_to_send = 'http://172.20.10.2:8888/communication/store_current_location/' + get_mac_address()
			response = requests.post(url_to_send, data=json.dumps(data_to_send), headers = headers)
			print("[+]Position:{lat:"+str(vehicle.location.global_relative_frame.lat)+"-lon:"+
				str(vehicle.location.global_relative_frame.lon)+"} sent to server.")

def send_video_url():
	global video_sent
	while(1):
		if(video_sent == 1):
			print("[+]Video URL sent to server.")
			headers = {'Content-Type':'application/json'}
			url_to_send = 'http://172.20.10.2:8888/communication/set_video_url/' + get_mac_address() + '/' + 'mtadroneservice.loca.lt'
			response = requests.post(url_to_send, headers=headers)
			break

def send_stream_to_server_thread3(name):
	global video_sent
	video_sent = 1
	print("[+]Sending video to a public IP..")
	time.sleep(5)
	os.system('lt --port 8080 --subdomain mtadroneservice')


vehicle = connectMyCopter()
vehicle.parameters['PLND_ENABLED'] = 1
vehicle.parameters['PLND_TYPE'] = 1
vehicle.parameters['PLND_EST_TYPE'] = 0
vehicle.parameters['LAND_SPEED'] = 10


def main():

	global lat_base
	global lon_base
	print(" ___ ___  ______   ____  ___    ____    ___   ____     ___  _____   ___  ____   __ __  ____    __    ___")
	print("|   |   ||      | /    ||   \  |    \  /   \ |    \   /  _]/ ___/  /  _]|    \ |  |  ||    |  /  ]  /  _]")
	print("| _   _ ||      ||  o  ||    \ |  D  )|     ||  _  | /  [_(   \_  /  [_ |  D  )|  |  | |  |  /  /  /  [_")
	print("|  \_/  ||_|  |_||     ||  D  ||    / |  O  ||  |  ||    _]\__  ||    _]|    / |  |  | |  | /  /  |    _]")
	print("|   |   |  |  |  |  _  ||     ||    \ |     ||  |  ||   [_ /  \ ||   [_ |    \ |  :  | |  |/   \_ |   [_")
	print("|   |   |  |  |  |  |  ||     ||  .  \|     ||  |  ||     |\    ||     ||  .  \ \   /  |  |\     ||     |")
	print("|___|___|  |__|  |__|__||_____||__|\_| \___/ |__|__||_____| \___||_____||__|\_|  \_/  |____|\____||_____|")

	lat_base = str(vehicle.location.global_relative_frame.lat)
	lon_base = str(vehicle.location.global_relative_frame.lon)

	thread1 = threading.Thread(target=send_pos_to_server_thread1, args=("thread1",))
	thread2 = threading.Thread(target=start_stream_locally_thread2, args=("thread2",))
	thread3 = threading.Thread(target=send_stream_to_server_thread3, args=("thread3",))
	thread4 = threading.Thread(target=main_programme_thread4, args=("thread4",))

	thread4.start()
	print("[+]Thread for the main programme started.")
	thread3.start()
	print("[+]Thread for sending video to public IP started.")
	thread2.start()
	print("[+]Thread for sending video locally started.")
	thread1.start()
	print("[+]Thread for sending position to server started.")

	thread1.join()
	thread2.join()
	thread3.join()
	thread1.join()

main()
