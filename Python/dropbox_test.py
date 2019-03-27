import dropbox
import RPi.GPIO as GPIO
import time
from picamera.array import PiRGBArray
from picamera import PiCamera
import string
import random

serialNumber = "01"
random = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(10))
print(random)
camPin = 17
camera = PiCamera()
triggered = False

class TransferData:

    def __init__(self, access_token):
        self.access_token = access_token
        print("hier wii is")

    def upload_file(self, file_from, file_to):
        print("Making db shit")
        dbx = dropbox.Dropbox(self.access_token)

        print("opening file")
        with open(file_from, 'rb') as f:
            print("attempting upload")
            dbx.files_upload(f.read(), file_to, mode=dropbox.files.WriteMode("overwrite"))

        print("getting url") 
        result = dbx.share(file_to)
        print(result)

        triggered = True

def take_picture(channel):
    print("Cat got")
    if triggered == False:
        camera.capture('/home/pi/catproject/picture_' + random + '.jpg')
    
        file_from = 'picture_' + random + '.jpg'
        file_to = '/Tunnistus/picture_' + random + '.jpg'

        transferData.upload_file(file_from, file_to)
    else:
        print("too many pics")

access_token = 'vh3aWw_IEX8AAAAAAAACVNfnHfGI82qV-I6sHVOlATnMuilvnyAhT1NSxrjhnQcg'
transferData = TransferData(access_token)

GPIO.setmode(GPIO.BCM)
GPIO.setup(camPin, GPIO.IN)
GPIO.add_event_detect(camPin,GPIO.RISING,callback=take_picture)
