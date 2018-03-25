import roombaApi as roomba
from missileApi import MissileApi 
import threading
import time


def asyncTargetDetected():
    roomba.targetDetected()
#    pass

launcher = MissileApi()
fireThread = threading.Thread(target=launcher.fire,args=(),kwargs={})
targetedThread = threading.Thread(target=asyncTargetDetected,args=(),kwargs={})

roomba.clean()
time.sleep(10)
roomba.initialize()
#roomba.targetDetected()
targetedThread.start()
fireThread.start()



targetedThread.join()
fireThread.join()
roomba.shutdown()



