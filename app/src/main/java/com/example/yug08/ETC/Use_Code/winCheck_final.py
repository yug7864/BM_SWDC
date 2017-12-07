import bluetooth
import time
import pymysql

#b'0'

selectSQL = "select state from admin where port = 2"
updateSQL = "UPDATE admin SET state = %s WHERE port = 2"

port = 2

bd_addr ="98:D3:31:40:98:27"
BTport = 1

sock1 = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
sock1.connect((bd_addr,BTport))

#init SQL
conn = pymysql.connect(host='localhost', user='root', password='blackm12', db='BM', charset='utf8')
curs = conn.cursor()

#init pre Buffer
curs.execute(selectSQL)
preBuffer = curs.fetchall()
preBuffer = preBuffer[0][0]
print ("preBuffer:",preBuffer)#Debug
conn.close()

while 1:
	conn = pymysql.connect(host='localhost', user='root', password='blackm12', db='BM', charset='utf8')
	curs = conn.cursor()
	
	buffer = sock1.recv(4096)
	currentState = chr(buffer[0])
	print (currentState)
	
	if(currentState!=preBuffer) :
			curs.execute(updateSQL,(int(currentState)))
			conn.commit()
			conn.close()
	
	
	preBuffer = currentState
sock1.close()
            
