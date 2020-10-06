import pandas as pd
from pandas import read_csv

def getListofStud():
    df =read_csv("CheckStudent.csv")
    list_of_students = df["StudentNo"]
    return list(list_of_students)
