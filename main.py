import roombaApi as roomba
from missileApi import MissileApi 
import threading
import time


def fire():
    time.sleep(1)
    launcher.send_cmd(launcher.FIRE)

def asyncTargetDetected():
    roomba.targetDetected()

launcher = MissileApi()
fireThread = threading.Thread(target=launcher.fire,args=(),kwargs={})
targetedThread = threading.Thread(target=asyncTargetDetected,args=(),kwargs={})

roomba.clean()
time.sleep(5)
roomba.initialize()
targetedThread.start()
fireThread.start()



targetedThread.join()
fireThread.join()
roomba.shutdown()



