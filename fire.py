import roombaApi as roomba
from missileApi import MissileApi 
import threading
import sys
import time


def asyncTargetDetected():
    roomba.targetDetected()

launcher = MissileApi()
targetedThread = threading.Thread(target=asyncTargetDetected,args=(),kwargs={})
fireThread = threading.Thread(target=launcher.fire,args=(),kwargs={})

roomba.initialize()


targetedThread.start()
time.sleep(4)
file=open("middle.txt","r")
middle = float(file.readline())
roomba.MoveToTarget(middle)
fireThread.start()

targetedThread.join()
fireThread.join()
time.sleep(2)

roomba.clean()
time.sleep(5)



