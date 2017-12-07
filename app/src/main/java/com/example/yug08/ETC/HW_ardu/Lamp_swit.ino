#include<Servo.h>

Servo myservo;
char data;
int swit = 5;
int state = 0;

void setup() {
  data=0;
  myservo.attach(10);
  myservo.write(95);
  pinMode(swit,INPUT_PULLUP);
  Serial.begin(9600);
}

void loop() {
  if(Serial.available()>0){
    data = Serial.read();
    Serial.print(data);

    //전등켠다
    if(data =='1' && state == 0){
      onSwitch();
    }//전등끈다
    else if(data == '0' && state == 1){
      offSwitch();
    }
  }

  if(digitalRead(swit) == LOW){
    if(state==1){
       offSwitch();
       Serial.write('0');
       Serial.write('0');
    }
    else if(state == 0){
       onSwitch();
       Serial.write('1');
       Serial.write('1');
    }
    delay(1000);
  }
}

void onSwitch(){
    myservo.write(165);
    delay(500);
    myservo.write(95);
    delay(500);
    state=1;
}

void offSwitch(){
    myservo.write(25);
    delay(500);
    myservo.write(95);
    delay(500);
    state=0;
}
