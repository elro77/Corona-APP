import face_recognition
import numpy as np
import cv2
from PIL import Image
import base64
import io

def main(insertedImage,imageDataSet,labelsDataSet):

    print("hello from script1")

    known_face_encoding=[]

    for i in range(len(imageDataSet)):
        imgArray=strToImg(imageDataSet[i])
        face_locations=face_recognition.face_locations(imgArray)
        if(len(face_locations)>1):
            print("to many")
            return "to many"
        face_encoding=face_recognition.face_encodings(imgArray,face_locations)[0]
        known_face_encoding.append(face_encoding)


    print("start encoding of inserted")

    imgArray=strToImg(insertedImage)
    face_locations=face_recognition.face_locations(imgArray)
    if(len(face_locations)>1):
        print("to many")
        #return "to many"
    face_encoding=face_recognition.face_encodings(imgArray,face_locations)
    print("len of encoded insert is = "+str(len(face_encoding)))
    #    print(face_encoding.shape)
    print(face_encoding)


    if (len(face_locations) == 0):
        print("no face found ")
        return "no face found"
    print("finish encoding of inserted")

    if(len(face_locations)>1):
        print("to many")
        #return "to many"

    name="Unknown Person"
    for (top,right,bottom,left),face_encoding in zip(face_locations,face_encoding):
        matches=face_recognition.compare_faces(known_face_encoding,face_encoding)
        print (type(matches))
        print (len(matches))
        print (matches)
        if True in matches:
            first_match_index=matches.index(True)
            name = labelsDataSet[first_match_index]
            print(name)
            return name
    return name


def strToImg(data):
    decoded_data=base64.b64decode(data)
    np_data=np.fromstring(decoded_data,np.uint8)
    img=cv2.imdecode(np_data,cv2.IMREAD_UNCHANGED)

    imgArray=np.array(img, copy=True)
    return imgArray

