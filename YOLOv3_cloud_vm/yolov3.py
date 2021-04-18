import numpy as np
import argparse
import time
import cv2
import os
import requests

image_number = 0
count = 0

print("[+]Waiting to receive video..")
while(1):
        time.sleep(3)
        response = requests.get('http://172.20.10.2:8888/communication/get_video_url')
        video_url_incomplete = response.json()['videoURL']
        drone_id = response.json()['droneID']
        if(video_url_incomplete != ""):
                break

print("[+]Video received.")
time.sleep(5)
video_url = "https://" + video_url_incomplete + "/?action=stream"
video = cv2.VideoCapture(video_url)
ap = argparse.ArgumentParser()
ap.add_argument("-y", "--yolo", required=True, help="base path to YOLO directory")
ap.add_argument("-c", "--confidence", type=float, default=0.5, help="minimum probability to filter weak detections")
ap.add_argument("-t", "--threshold", type=float, default=0.3, help="threshold when applying non-maxima suppression")
args = vars(ap.parse_args())

labelsPath = os.path.sep.join([args["yolo"], "coco.names"])
LABELS = open(labelsPath).read().strip().split("\n")

np.random.seed(42)
COLORS = np.random.randint(0, 255, size=(len(LABELS), 3), dtype="uint8")


weightsPath = os.path.sep.join([args["yolo"], "yolov3_training_last.weights"])
configPath = os.path.sep.join([args["yolo"], "yolov3_testing.cfg"])

print("[+]Loading YOLO from disk...")
net = cv2.dnn.readNetFromDarknet(configPath, weightsPath)

def mission_still_running(drone_id):
    response = requests.get('http://172.20.10.2:8888/communication/get_mission_status/' + drone_id)
    status = response.json()['missionStatus']
    return status

while video.isOpened():
    if(mission_still_running(drone_id) == "SEARCHING_PERSON"):
        ret, image = video.read()
        count = count + 1
        if(count % 15 == 0):
            (H, W) = image.shape[:2]

            ln = net.getLayerNames()
            ln = [ln[i[0] - 1] for i in net.getUnconnectedOutLayers()]

            blob = cv2.dnn.blobFromImage(image, 1 / 255.0, (416, 416), swapRB=True, crop=False)
            net.setInput(blob)
            start = time.time()
            layerOutputs = net.forward(ln)
            end = time.time()

            print("[INFO] YOLO took {:.6f} seconds".format(end - start))

            boxes = []
            confidences = []
            classIDs = []

            for output in layerOutputs:
                for detection in output:
                    scores = detection[5:]
                    classID = np.argmax(scores)
                    confidence = scores[classID]
                    if confidence > args["confidence"]:
                        box = detection[0:4] * np.array([W, H, W, H])
                        (centerX, centerY, width, height) = box.astype("int")

                        x = int(centerX - (width / 2))
                        y = int(centerY - (height / 2))

                        boxes.append([x, y, int(width), int(height)])
                        confidences.append(float(confidence))
                        classIDs.append(classID)

            idxs = cv2.dnn.NMSBoxes(boxes, confidences, args["confidence"], args["threshold"])

            if len(idxs) > 0:
                print(image)
                image_name = str(image_number) + '.png'
                cv2.imwrite(image_name, image)
                image_number = image_number + 1
                for i in idxs.flatten():
                    (x, y) = (boxes[i][0], boxes[i][1])
                    (w, h) = (boxes[i][2], boxes[i][3])
                    cv2.rectangle(image, (x, y), (x + w, y + h), (255,0,0), 2)
                    text = "{}: {:.4f}".format(LABELS[classIDs[i]], confidences[i])
                    cv2.putText(image, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,0,0), 2)
                    url = "http://172.20.10.2:8888/communication/upload/" + drone_id
                    files = {'files': open(image_name,'rb')}
                    headers = {}
                    response = requests.post(url, files=files, headers=headers)

    if (mission_still_running(drone_id) == "FLYING_TO_BASE"):
        break