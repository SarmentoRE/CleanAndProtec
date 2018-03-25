import roombaApi as roomba
from missileApi import MissileApi 
import threading


def asyncTargetDetected(middle):
    roomba.targetDetected(middle)

launcher = MissileApi()
fireThread = threading.Thread(target=launcher.fire,args=(),kwargs={})
targetedThread = threading.Thread(target=asyncTargetDetected,args=(sys.argv[1]),kwargs={})

roomba.initialize()
targetedThread.start()
fireThread.start()



targetedThread.join()
fireThread.join()



