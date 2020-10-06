import csv
import pathlib
from tkinter import messagebox

path = "C:/Users/ROLINE JOHN AGUILAR/eclipse-workspace/QRCODE/new.csv"
url = ""
row = list()
try:
    with open(path, "r") as csv_file:
        csv_reader = csv.reader(csv_file)
        #next(csv_reader)
        i = 0
        for line in csv_reader:
            if (i==0):
                url = line[0]
            else:
                row.append(int(line[0]))
                row.append(int(line[1]))
                row.append(int(line[2]))
                row.append(int(line[3]))
            i+=1
        print(url)
        url = pathlib.PureWindowsPath(url)
        url = url.as_posix()

except UnicodeDecodeError:
    messagebox.showerror("CSV Reading","Program cannot open this type of file, *.csv file are only accepted")

def getPath():
    return url
def getTest():
    return row


