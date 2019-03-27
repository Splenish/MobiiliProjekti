from firebase import firebase
from google.cloud import storage
import urllib

#def upload_picture():
picture = open("picture.jpg","rb")
myBytes = picture.read()
url = "https://firebasestorage.googleapis.com/v0/b/gs://catcatch-953a5.appspot.com%2Fpicture.jpg"
myHeaders = {"Content-Type": "text/plain"}
myRequest = urllib.request.Request(url, data=myBytes, headers=myHeaders, method="POST")

try:
    loader = urllib.request.urlopen(myRequest)
except urllib.error.URLError as e:
    message = json.loads(e.read())
    print(message["error"]["message"])
else:
    print(loader.read())
    
firebase = firebase.FirebaseApplication('https://catcatch-953a5.firebaseio.com/', None)
client = storage.client()
'gs://catcatch-953a5.appspot.com'
result = firebase.get('/traps', None)
print (result)
