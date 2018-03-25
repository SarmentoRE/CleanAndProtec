import platform
import time
import usb.core
import usb.util


class MissileApi(object):
    """
    Based on https://github.com/codedance/Retaliation
    """
    DOWN = 0x01
    UP = 0x02
    LEFT = 0x04
    RIGHT = 0x08
    FIRE = 0x10
    STOP = 0x20


    def __init__(self):
        self._get_device()
        self._detach_hid()
        self.DEVICE.set_configuration()

    def _get_device(self):
            self.DEVICE = usb.core.find(idVendor=0x0a81, idProduct=0x0701)
            if self.DEVICE is None:
                raise ValueError('Missile device not found')

    def _detach_hid(self):
        if "Linux" == platform.system():
            try:
                self.DEVICE.detach_kernel_driver(0)
            except Exception, e:
                pass

    def send_cmd(self, cmd):
            self.DEVICE.ctrl_transfer(0x21, 0x09, 0x0200, 0, [cmd])

    def send_move(self, cmd, duration_ms):
        self.send_cmd(cmd)
        time.sleep(duration_ms / 1000.0)
        self.send_cmd(self.STOP)

    def fire(self):
        self.send_cmd(self.FIRE)
        time.sleep(5)
        self.send_move(self.DOWN,1000)
        self.send_move(self.UP,1000)
        self.send_cmd(self.STOP)

