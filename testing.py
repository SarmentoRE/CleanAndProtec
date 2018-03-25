import platform 
import time
import usb.core
import usb.util
import sys


DEVICE = usb.core.find(idVendor=0x0a81,idProduct=0x0701)
try:
    DEVICE.detach_kernel_driver(0)
except Exception, e:
    pass
