
import io
import PIL.Image as Image
import platform
import sys
import socket
import struct
import math
from PIL import ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True
print("Starting MTADRONESERVICE's server for data manipulation\n")
'''
vcap = cv2.VideoCapture('https://mtadroneservice.loca.lt/?action=stream')
viewVideo=True

if len(sys.argv)>1:
	viewVideo=sys.argv[1]
	if viewVideo=='0' or viewVideo=='False' or viewVideo=='false':
		viewVideo=False

id_to_find=72
marker_size=18

horizontal_res = 640
vertical_res = 480

aruco_dict = aruco.getPredefinedDictionary(aruco.DICT_ARUCO_ORIGINAL)
parameters = aruco.DetectorParameters_create()

cameraMatrix = np.loadtxt('/home/chirita_gabi/Communication-link-drones/cameraMatrix.txt', delimiter=',')
cameraDistortion = np.loadtxt('/home/chirita_gabi/Communication-link-drones/cameraDistortion.txt', delimiter=',')

horizontal_fov = 62.2 * (math.pi / 180 )
vertical_fov = 48.8 * (math.pi / 180)

'''
def create_sockets():
	try:
		global hostBackend
		global portBackend
		global sockBackend
		hostBackend = ""
		portBackend = 9999
		sockBackend = socket.socket()
	except socket.error as msg:
		print("[!]Socket for backend creation error: " + str(msg))
	print("[+]Socket for backend created.")

	try:
		global hostDrone
		global portDrone
		global sockDrone
		hostDrone = ""
		portDrone = 80
		sockDrone = socket.socket()
	except socket.error as msg:
		print("[!]Socket for drone creation error: " + str(msg))
	print("[+]Socket for drone created.")

def bind_socket_backend():
	try:
		global hostBackend
		global portBackend
		global sockBackend
		sockBackend.bind((hostBackend, portBackend))
		sockBackend.listen(5)
	except socket.error as msg:
		print("[!]Socket backend binding error" + str(msg) + "\n" + "Retrying...")
	print("[+]Socket backend binded.")
def bind_socket_drone():
	try:
		global hostDrone
		global portDrone
		global sockDrone
		sockDrone.bind((hostDrone, portDrone))
		sockDrone.listen(5)
	except socket.error as msg:
		print("[!]Socket drone binding error" + str(msg) + "\n" + "Retrying...")
	print("[+]Socket drone binded.")

def sockets_accept():
	global connDrone
	global connBackend
	'''connDrone, addressDrone = sockDrone.accept()
	print("[+]Connection has been established for drone!")'''
	connBackend, addressBackend = sockBackend.accept()
	print("[+]Connection has been established for backend!")

'''
def lander():
	global connDrone
	frame = vcap.read()
	frame = cv2.resize(frame,(horizontal_res,vertical_res))
	frame_np = np.array(frame[1])
	gray_img = cv2.cvtColor(frame_np, cv2.COLOR_BGR2GRAY)
	ids = ''
	corners, ids, rejected = aruco.detectMarkers(image=gray_img, dictionary=aruco_dict, parameters=parameters)
	try:
		if ids is not None and ids[0] == id_to_find:
			ret = aruco.estimatePoseSingleMarkers(corners,marker_size,cameraMatrix=cameraMatrix,distCoeffs=cameraDistortion)
			(rvec, tvec) = (ret[0][0, 0, :], ret[1][0, 0, :])
			x = '{:.2f}'.format(tvec[0])
			y = '{:.2f}'.format(tvec[1])
			z = '{:.2f}'.format(tvec[2])

			y_sum = 0
			x_sum = 0

			x_sum = corners[0][0][0][0] + corners[0][0][1][0] + corners[0][0][2][0] + corners[0][0][3][0]
			y_sum = corners[0][0][0][1] + corners[0][0][1][1] + corners[0][0][2][1] + corners[0][0][3][1]

			x_avg = x_sum*.25
			y_avg = y_sum*.25

			x_ang = (x_avg - horizontal_res*.5)*(horizontal_fov/horizontal_res)
			y_ang = (y_avg - vertical_res*.5)*(vertical_fov/vertical_res)
			x_ang_string = str(x_ang)
			y_ang_string = str(y_ang)
			stringToSend = x_ang_string + "|" + y_ang_string
			bytesToSend = bytes(stringToSend)
			connDrone.send(bytesToSend)
	except Exception as e:
		print('[!]Target likely not found. Error: ' + str(e))
'''

def getNextByteBlock(totalBytes, start, end):
	currentBlock = bytearray(8192)
	iterator = 0
	for i in range(start,end):
		currentBlock[iterator] = totalBytes[i]
		iterator = iterator + 1
	return currentBlock

def fillSpaces(nrBytes):
	for i in range(len(nrBytes),4):
		nrBytes = nrBytes + "!"
	return nrBytes


def sendImage(image):
	global connBackend
	with open(image, "rb") as image:
		frame = image.read()
		bytes_image = bytearray(frame)
		nr_bytes = len(bytes_image)
		connBackend.send(bytes(fillSpaces(str(nr_bytes)),"utf-8"))
		current_length = 0
		difference = -1
		while difference != 0:
			difference = nr_bytes - current_length
			if difference < 8192 and difference != 0:
				current_length = current_length + difference
				connBackend.send(bytes(fillSpaces(str(difference)),"utf-8"))
				connBackend.send(getNextByteBlock(bytes_image,current_length-difference,current_length))
			else:
				if difference != 0:
					current_length = current_length + 8192
					connBackend.send(bytes(str(8192), "utf-8"))
					connBackend.send(getNextByteBlock(bytes_image,current_length-8192,current_length))



def main():
	global connDrone
	global sockDrone
	global connBackend
	global sockBackend

	create_sockets()
	bind_socket_drone()
	bind_socket_backend()
	sockets_accept()

	sendImage("jjj.png")
	'''byte_image_len = 0
	byte_image = bytearray(1000000)'''
	'''while(1):
		if(conn.recv(1024) == "landing"):
			lander()'''


main()
