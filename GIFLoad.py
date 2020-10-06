import tkinter as tk
from tkinter import*

def task():
    # The window will stay open until this function call ends.
    #sleep(2) # Replace this with the code you want to run
    root.destroy()
def update(ind):

    frame = frames[ind]
    ind += 1
    label.configure(image=frame)
    root.after(100,update,ind)

try:
    root = tk.Tk()
    windowWidth = root.winfo_reqwidth()
    windowHeight = root.winfo_reqheight()

    positionRight = int(root.winfo_screenwidth()/2-windowWidth/2)+(-150)
    positionDown = int(root.winfo_screenheight()/2-windowHeight/2)+(-140)

    root.configure(background = 'black')
    root.geometry("+{}+{}".format(positionRight,positionDown))
    frames = [PhotoImage(file ='QRProcess.gif', format = 'gif -index %i' %(i)) for i in range(28)]
    root.title("Exporting QR's into pdf, Please Wait")
    label = tk.Label(root, text="Exporting QR's into pdf, Please Wait")
    label.pack()
    root.after(0,update,0)

    root.after(5000,task)
    root.mainloop()
except IndexError:
    print("nothing")

#print("Main loop is now over and we can do other stuff.")