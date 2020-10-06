import csv
import qrcode
import subprocess
from threading import Thread
from PIL import Image,ImageDraw, ImageFont
import tkinter as tk
from PrintQR import QRstoPDF
from tkinter import filedialog
from tkinter import messagebox
height = 290
width = 290
root = tk.Tk()
root.withdraw()
def QrMaker(path):
    try:
        with open(path, "r") as csv_file:
            csv_reader = csv.reader(csv_file)
            next(csv_reader)
            try:
                for line in csv_reader:
                    qr = qrcode.QRCode(error_correction=qrcode.constants.ERROR_CORRECT_M)
                    # StudentNo =""
                    # faceName = ""
                    # fileName = ""

                    StudentNo = line[0]
                    StudentName = line[1]
                    Section = line[2]
                    faceName = "Faces/" + StudentNo + ".jpg"

                    try:
                        face = Image.open(faceName)
                        faceWidth, faceHeight = face.size
                        if faceHeight != 60 or faceWidth != 60:
                            face = Image.open("")

                        # print(StudentNo)
                        # print(StudentName)

                        qr.add_data(StudentNo)
                        qr.make()

                        # img_qr = qr.make_image()
                        #    fileName = "QRimages/" + StudentNo + "QR" + ".jpg"
                        img_qr = qr.make_image().convert('RGB')
                        pos = ((img_qr.size[0] - face.size[0]) // 2, (img_qr.size[1] - face.size[1]) // 2)
                        img_qr.paste(face, pos)
                    except FileNotFoundError:
                        # print(StudentNo + " has no image inserted so default qr is created...")
                        qr = qrcode.QRCode(version=1, error_correction=qrcode.constants.ERROR_CORRECT_M)
                        qr.add_data(StudentNo)
                        qr.make()
                        img_qr = qr.make_image()
                    #    fileName = "QRimages/" + StudentNo + "QR" + ".jpg"
                    #    img_qr.save(fileName)
                    except AttributeError:
                    #force to error to generate something with wrong format image
                        qr = qrcode.QRCode(version=1, error_correction=qrcode.constants.ERROR_CORRECT_M)
                        qr.add_data(StudentNo)
                        qr.make()
                        img_qr = qr.make_image()
                    finally:
                        draw = ImageDraw.Draw(img_qr)
                        fontName = ImageFont.truetype('Tinos-Regular.ttf', size=18)
                        nameSize = (fontName.getsize(StudentName)[0])

                        if nameSize > width:
                            fontName = ImageFont.truetype('Tinos-Regular.ttf', size=10)
                            nameSize = (fontName.getsize(StudentName)[0])

                        varX = abs((width - nameSize) / 2)
                        (x, y) = (varX, 250)
                        message = StudentName
                        color = 'rgb(0,0,0)'
                        draw.text((x, y), message, fill=color, font=fontName)

                        fontSection = ImageFont.truetype('Tinos-Regular.ttf', size=14)
                        sectionSize = (fontSection.getsize(line[2])[0])
                        varX = (width - sectionSize) / 2
                        (x, y) = (varX, 270)
                        message = line[2]
                        color = 'rgb(0,0,0)'
                        draw.text((x, y), message, fill=color, font=fontSection)

                        fontUniversity = ImageFont.truetype('Tinos-Regular.ttf', size=10)
                        UnivSize = (fontUniversity.getsize("POLYTECHNIC UNIVERSITY OF THE PHILIPPINES")[0])
                        varX = abs((UnivSize - width) / 2)
                        (x, y) = (varX, 25)
                        message = "POLYTECHNIC UNIVERSITY OF THE PHILIPPINES"
                        color = 'rgb(0,0,0)'
                        draw.text((varX, y), message, fill=color, font=fontUniversity)

                        fontCollege = ImageFont.truetype('Tinos-Regular.ttf', size=15)
                        CollegeSize = (fontCollege.getsize("CCIS")[0])
                        varX = abs((width - CollegeSize) / 2)
                        (x, y) = (varX, 8.5)
                        message = "CCIS"
                        color = 'rgb(0,0,0)'
                        draw.text((varX, y), message, fill=color, font=fontCollege)

                        fileName = "QRimages/" + StudentNo + "QR" + ".jpg"
                        img_qr.save(fileName)

            except KeyError:
                print("Please Name the first Column as (case-sensitive) \"StudentNumber\"")
            except PermissionError:
                print("No such file Directory to put")
            except FileNotFoundError:
                pass
                #print("No storage for such file")
            finally:
                print("Process completed")

            #messagebox.showinfo("QR Code Maker","QR's has been successfully created!")

    except UnicodeDecodeError:
        messagebox.showerror("QR Code Maker","Program cannot open this type of file, *.csv file are only accepted")
    except IndexError:
        messagebox.showerror("QR Code Maker","Data Formatting is incorrect in *.csv file \nPlease use this format StudentNo,Name,Section\n"
                                             "2018-04633-MN-0,Roline John L. Aguilar,BSIT 2-1")
def GIFs():
    import Table


def FileOpen():
    path = filedialog.askopenfilename(initialdir = "C:/Users/Documents",title="Select a File",filetypes=(("csv files","*.csv"),("all files","*.*")))
    QrMaker(path)
    QRstoPDF(path)

def Database():
    QrMaker("QRData.csv")
    QRstoPDF("QRData.csv")

