int doorPin = 10;
int doorState=0;
byte data;

void setup() {
  Serial.begin(9600);
  pinMode(doorPin,INPUT_PULLUP);
}

void loop() {
  data = Serial.read();
  doorState = digitalRead(doorPin);

  if(doorState == 1) {
    Serial.write('1');
    //Serial.println('1');
  }
  else if(doorState == 0){
    Serial.write('0');
    //Serial.println('0');
  }
  delay(300);
}
