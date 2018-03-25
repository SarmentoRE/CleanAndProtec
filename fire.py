import roombaApi as roomba
from missileApi import MissileApi 
import threading
import sys
import time


def asyncTargetDetected(middle):
    roomba.targetDetected(middle)

launcher = MissileApi()
file=open("middle.txt","r")
middle = float(file.readline())
targetedThread = threading.Thread(target=asyncTargetDetected,args=(),kwargs={'middle': middle})
time.sleep(1)
fireThread = threading.Thread(target=launcher.fire,args=(),kwargs={})

roomba.initialize()
targetedThread.start()
fireThread.start()




targetedThread.join()
fireThread.join()



