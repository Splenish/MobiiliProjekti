#include "Arduino.h"
#define WireSend(args) Wire.write(args)
#define WireRead(args) Wire.read(args)
#define printByte(args) Serial.write(args)
#define printlnByte(args)  Serial.write(args),Serial.println()

#include <Wire.h>

#define BUFFER_LENGTH 10//Define the buffer length

int GPSAddress = 0x42;//GPS I2C Address
double latitudeDouble;
double longitudeDouble;
String latitudeString;
String longitudeString;
String GPSData;

double Datatransfer(char *data_buf,char num)//Data type converter：convert char type to float
{                                           //*data_buf:char data array ;num:float length
  double temp=0.0;
  unsigned char i,j;

  if(data_buf[0]=='-')//The condition of the negative
  {
    i=1;
    //The date in the array is converted to an integer and accumulative
    while(data_buf[i]!='.')
      temp=temp*10+(data_buf[i++]-0x30);
    for(j=0;j<num;j++)
      temp=temp*10+(data_buf[++i]-0x30);
    //The date will converted integer transform into a floating point number
    for(j=0;j<num;j++)
      temp=temp/10;
    //Converted to a negative number
    temp=0-temp;
  }
  else//Positive case
  {
    i=0;
    while(data_buf[i]!='.')
      temp=temp*10+(data_buf[i++]-0x30);
    for(j=0;j<num;j++)
      temp=temp*10+(data_buf[++i]-0x30);
    for(j=0;j<num;j++)
      temp=temp/10 ;
  }
  return temp;
}
void rec_init()//initial GPS
{
  Wire.beginTransmission(GPSAddress);
  WireSend(0xff);//To send data address      
  Wire.endTransmission(); 

  Wire.beginTransmission(GPSAddress);
  Wire.requestFrom(GPSAddress,10);//Require 10 bytes read from the GPS device
}
char ID()//Receive the statement ID
{
  char i = 0;
  char value[7]={
    '$','G','P','G','G','A',','      };//To receive the ID content of GPS statements
  char buff[7]={
    '0','0','0','0','0','0','0'      };

  while(1)
  {
    rec_init();//Receive data initialization    
    while(Wire.available())   
    { 
      buff[i] = WireRead();//Receive serial data  
      if(buff[i]==value[i])//Contrast the correct ID
      {
        i++;
        if(i==7)
        {
          Wire.endTransmission();//End of receiving
          return 1;//Receiving returns 1

        }
      }
      else
        i=0;
    }
    Wire.endTransmission();//End receiving
  }
}
void rec_data(char *buff,char num1,char num2)//Receive data function
{                        //*buff：Receive data array；num1：Number of commas ；num2：The   length of the array
  char i=0,count=0;

  if(ID())
  {
    while(1)
    {
      rec_init();    
      while(Wire.available())   
      { 
        buff[i] = WireRead();
        if(count!=num1)
        {  
          if(buff[i]==',')
            count++;
        }
        else
        {
          i++;
          if(i==num2)
          {
            Wire.endTransmission();
            return;
          }
        }
      }
      Wire.endTransmission();
    }
  }
}
void latitude()//Latitude information
{
  char lat[10]={
    '0','0','0','0','0','0','0','0','0','0' };//Store the latitude data
  rec_data(lat,1 ,10);//Receive the latitude data
  latitudeDouble = Datatransfer(lat,5);
}
void  longitude()//Longitude information
{
  char lon[11]={
    '0','0','0','0','0','0','0','0','0','0','0' };//Store longitude data
  rec_data(lon,3,11);//Receive the longitude data
  longitudeDouble = Datatransfer(lon,5);
}
void setup()
{
  Wire.begin();//IIC Initialize
  Serial.begin(9600);//set baud rate
}
void loop()
{
  while(1)
  {
    latitude();
    latitudeDouble /= 100;
    latitudeDouble += 0.4;
    
    longitude();
    longitudeDouble /= 100;
    longitudeDouble += 0.205;
    
    latitudeString = String(latitudeDouble,5);
    longitudeString = String(longitudeDouble,5);
    GPSData = String(latitudeString + "," + longitudeString);
    Serial.println(GPSData);
  }
}
