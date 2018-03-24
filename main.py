import roombaApi as roomba
from armageddon import Armageddon 
import threading
import time


def fire():
    time.sleep(1)
    launcher.send_cmd(launcher.FIRE)

def asyncTargetDetected():
    roomba.targetDetected()

fireThread = threading.Thread(target=fire,args=(),kwargs={})
targetedThread = threading.Thread(target=asyncTargetDetected,args=(),kwargs={})

launcher = Armageddon()
roomba.clean()
time.sleep(5)
roomba.initialize()
#roomba.targetDetected()
targetedThread.start()
fireThread.start()



targetedThread.join()
fireThread.join()
roomba.shutdown()



