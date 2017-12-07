import bluetooth
import time
import pymysql

selectSQL = "select state from admin where port = 1"
updateOnSQL = "UPDATE admin SET state = 1 WHERE port =1"
updateOffSQL = "UPDATE admin SET state = 0 WHERE port =1"

bd_addr ="98:D3:32:20:5B:96" 
port = 1
sock1 = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
sock1.connect((bd_addr,port))


#init SQL
conn = pymysql.connect(host='localhost', user='root', password='blackm12', db='BM', charset='utf8')
curs = conn.cursor()

#init pre Buffer
curs.execute(selectSQL)
preBuffer = curs.fetchall()

preBuffer = preBuffer[0][0]
print ("preBuffer:",preBuffer)#Debug
conn.close()

#socek Loop
while 1:
	conn = pymysql.connect(host='localhost', user='root', password='blackm12', db='BM', charset='utf8')
	curs = conn.cursor()    
	curs.execute(selectSQL)
	currentState = curs.fetchall()
	currentState = currentState[0][0]
	
	print ("currentState:",currentState)#Debug
	# DB to Senser
	tosend = chr(currentState)
	if currentState != preBuffer :
		if(currentState == 1) :
			tosend='1'
		elif (currentState == 0) :
			tosend='0'
	print ("tosend:",tosend)#Debug
	sock1.send(tosend)
	sock1.send(tosend)
	preBuffer = currentState
		
	# modify menualy sangun
	if sock1.recv(4096) >= b'0' :
            buffer = sock1.recv(4096)
            currentState = chr(buffer[0])
            print("now",currentState)
            if currentState != preBuffer :
                if(currentState == '1') :
                    curs.execute(updateOnSQL)
                    #conn.commit()
                    #conn.close()
                    preBuffer = 1
                elif (currentState == '0') :
                    curs.execute(updateOffSQL)
                    #conn.commit()
                    #conn.close()
                    prtBuffer=0
            #buffer = 0
	conn.commit()	
	conn.close()
	time.sleep(0.3)
 #END
conn.commit()
conn.close()
sock1.close()
