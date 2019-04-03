import serial
import time
import pynmea2

ser = serial.Serial ("/dev/ttyS0", baudrate = 9600, timeout = 0.5)

while True:
    received_data = str(ser.readline())
    print(str(received_data))
    if "GPGGA" in received_data:
        msg = pynmea2.parse(str(received_data))
        latval = msg.lat
        concatlat = "lat: " + str(latval)
        print(concatlat)
        longval = msg.lon
        concatlon = "Long: " + str(longval)
        print(concatlon)
