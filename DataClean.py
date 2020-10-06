import pandas as pd
import re
import openpyxl
def clean():
    df = pd.read_csv("present.csv")
    df = df.loc[df["StudNo"].str.contains(r"[20][2|1]\d[\-]\d\d\d\d\d[\-]MN-0",regex=True)]
    print(df)
    df.to_csv("cleanData.csv",index = False)
