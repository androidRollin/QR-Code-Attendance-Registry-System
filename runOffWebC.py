
from subprocess import *
import time
Popen("python Loading.py")
time.sleep(1)
Popen('python read_qrcode.py')
time.sleep(1)
from threading import Thread

#def one():
#    import Loading
#def two():
 #   import read_qrcode

#Thread(target = one).start()
#Thread(target = two).start()


