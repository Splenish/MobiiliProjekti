import dropbox
import RPi.GPIO as GPIO
import time
from picamera.array import PiRGBArray
from picamera import PiCamera
import string
import random
import serial
from firebase import firebase
from threading import Thread

serialNumber = "01"
random = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(10))
print(random)
camPin = 17
camera = PiCamera()
firebase = firebase.FirebaseApplication('https://catcatch-953a5.firebaseio.com', None)

class TransferData:

    def __init__(self, access_token):
        self.access_token = access_token
        print("hier wii is")

    def upload_file(self, file_from, file_to):
        print("Making db thingy")
        dbx = dropbox.Dropbox(self.access_token)

        print("opening file")
        with open(file_from, 'rb') as f:
            print("attempting upload")
            dbx.files_upload(f.read(), file_to, mode=dropbox.files.WriteMode("overwrite"))

        print("getting url") 
        picUrl = dbx.sharing_create_shared_link_with_settings(file_to).url
        picUrl = picUrl[:-4]
        picUrl = picUrl + "raw=1"
        
        print(picUrl)

        picUrl = picUrl[:-4]
        picUrl += "?raw=1"

        firebase.put('traps/' + serialNumber,"triggered", True)
        firebase.put('traps/' + serialNumber,"urlString", picUrl)

def take_picture():
    print("Cat got")
    camera.capture('/home/pi/MobiiliProjekti/Python/picture_' + random + '.jpg')
    
    file_from = 'picture_' + random + '.jpg'
    file_to = '/Tunnistus/picture_' + random + '.jpg'
    transferData.upload_file(file_from, file_to)

def button_pressed(channel):
    if(firebase.get('traps/' + serialNumber,"triggered") == False):
        print("Button pressed")
        Thread(target=take_picture).start()
    

access_token = 'vh3aWw_IEX8AAAAAAAACVNfnHfGI82qV-I6sHVOlATnMuilvnyAhT1NSxrjhnQcg'
transferData = TransferData(access_token)

GPIO.setmode(GPIO.BCM)
GPIO.setup(camPin, GPIO.IN)
GPIO.add_event_detect(camPin,GPIO.RISING,callback=button_pressed)
ser = serial.Serial ("/dev/ttyACM0",9600)
ser.baudrate=9600

while ser.inWaiting:
    GPSString = str(ser.readline())
    GPSString = GPSString[2:]
    GPSString = GPSString[:-5]
    print(GPSString)
    firebase.put('traps/' + serialNumber,"pos", GPSString)
