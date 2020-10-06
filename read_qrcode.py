#import necessary packages
from imutils.video import VideoStream
from pyzbar import pyzbar
import argparse
import imutils
import time
import cv2
import pygame
import DateandTime as dT
import re
from threading import Thread
import DataClean as dC
from Check import getListofStud

def detectIntruder():
    pygame.mixer.init()
    pygame.mixer.music.load("siren.ogg")
    pygame.mixer.music.play()

#Construct the argument parser and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-o", "--output", type=str, default="present.csv",)
args = vars(ap.parse_args())

#Initialize the VideoStream and allow the camera sensor to warm up
print("[INFO] Starting the videoStream")
vs = VideoStream(src=0).start()
# vs = VideoStream(usePiCamera=True).start() // for PI cameras
time.sleep(2.0)

#open the output csv file for writing and initialize the set of barcodes found thus far
csv = open(args["output"], "w")
found = set()
students = list(getListofStud())
global notInFound
regexp = re.compile(r"[20][2|1]\d[\-]\d\d\d\d\d[\-]MN-0")
pygame.mixer.init()
#Write in the first List is the StudNo and AT for pandas processing
csv.write("StudNo,At\n")

# loop over the frames from the video stream
while True:
#grab the frame from the frame the threaded video stream and resize it into have a maximumm width of 400 pixels
    frame = vs.read()
    frame = imutils.resize(frame, width=400)
#Display Date and Time
    cv2.putText(frame, dT.Date_phil, (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 1)

#find the barcodes in the frame and decode each of the barcodes
    barcodes = pyzbar.decode(frame)

#loop over the detected barcodes
    for barcode in barcodes:

#Extract the bounding box location of the barcode and draw the bounding box surrounding the barcode on the image
        (x, y, w, h) = barcode.rect
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)
#The barcode data is a bytes object so if we want to draw it, our outpit image we need  to convert it to a string first
        notInFound = False
        barcodeData = barcode.data.decode("utf-8")
        barcodeType = barcode.type
        text = "{}: {}".format(barcodeType, barcodeData)
        cv2.putText(frame, text, (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
        if barcodeData not in found:
            notInFound = True

#if the barcode text is currently not in our CSV file, write the timestamp + barcode to disk and update the set
        if regexp.search(barcodeData):
            if  barcodeData in students:
                if notInFound is True:
                    # Draw the barcode data and the barcode type on the image
                    text_P = "ATTENDANCE RECORDED!!"
                    cv2.putText(frame, text_P, (x, y - 30), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)
                    # Blinking Date when the student is present,Time Recorded
                    cv2.putText(frame, dT.Date_phil, (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 1)
                    pygame.mixer.music.load("notif.ogg")
                    pygame.mixer.music.play()
                    # Write Data into the csv
                    timenow = time.strftime("%H:%M")
                    csv.write("{},{}\n".format(barcodeData, timenow))
                    csv.flush()
                else:
                    text_P = "ALREADY SCANNED! PLEASE MOVE"
                    cv2.putText(frame, text_P, (x, y - 30), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)

                found.add(barcodeData)

            elif barcodeData not in students and notInFound is True:
                if barcodeData not in students:
                    text_P = "INTRUDER ALERT!!"
                cv2.putText(frame, text_P, (x, y - 30), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 2)
                Thread(target =detectIntruder).start()
                if barcodeData not in found:
                    found.add(barcodeData)
                    csv.write("{},{}\n".format(barcodeData, timenow))
                    csv.flush()
                # Blinking Date when the student is present,Time Recorded
            else:
                if barcodeData not in students:
                    text_P = "INTRUDER ALERT!!"
                    cv2.putText(frame, text_P, (x, y - 30), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 2)
        else:
            text_P = "This is not a valid STUDENT NUMBER"
            cv2.putText(frame, text_P, (x, y - 30), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 255), 2)

            if notInFound is True:
                pygame.mixer.music.load("error.ogg")
                pygame.mixer.music.play()

            found.add(barcodeData)


#show the output frame and waiting is unlimited
    cv2.imshow("Scan Your QR Code", frame)
    key = cv2.waitKey(1) & 0xFF
#if the user press q or exit, the program will exit
    if key == ord("q") or key == 27:
        break


#close the output csv, do a bit a of a clean up
print("[INFO] cleaning up... ")
csv.close()
cv2.destroyAllWindows()
vs.stop()
dC.clean()
