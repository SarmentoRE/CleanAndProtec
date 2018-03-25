import roombaApi as roomba
from missileApi import MissileApi 
import threading
import sys


def asyncTargetDetected(middle):
    roomba.targetDetected(middle)

launcher = MissileApi()
fireThread = threading.Thread(target=launcher.fire,args=(),kwargs={})
targetedThread = threading.Thread(target=asyncTargetDetected,args=(),kwargs={'middle': sys.argv[1]})

roomba.initialize()
targetedThread.start()
fireThread.start()



targetedThread.join()
fireThread.join()



