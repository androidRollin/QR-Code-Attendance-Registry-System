import tkinter as tk
from ProjectAnalytics import getfeatures_test,getfeatures_train,gettarget_test,gettarget_train
def showTable(features_train,features_test,target_train,target_test):
    root = tk.Tk()
    root.iconbitmap("C:/Users/ROLINE JOHN AGUILAR/PycharmProject/Assignments/qricon.ico")
    root.title("Data Analytics")
    root.geometry("450x400+800+100")
    S = tk.Scrollbar(root)
    T = tk.Text(root,height =20, width =60)
    S.pack(side = tk.RIGHT, fill = tk.Y)
    T.pack(side = tk.LEFT, fill = tk.Y)
    S.config(command = T.yview)
    T.config(yscrollcommand = S.set)
    LongResult = " Training Features \n" + str(features_train) + "\n\n Testing Features \n" + str(features_test) +  \
                 "\n\n Training Target \n" + str(target_train) + "\n\n Testing Target \n" + str(target_test) +"\n"
    T.insert(tk.END,LongResult)
    tk.mainloop()
    

showTable(getfeatures_train(),getfeatures_test(),gettarget_train(),gettarget_test())
