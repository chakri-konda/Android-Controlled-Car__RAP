const int IN1 = 5;
const int IN2 = 6;
const int IN3 = 10;
const int IN4 = 11;

char data = 'h';    //Variable for storing the recieved data
int velocity = 255; //Keeping motors on HIGH, Ranges(0-255) 

void setup() {
  Serial.begin(9600);
  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);
}

void loop() {
  if(Serial.available()) {
    data = Serial.read()     //Reading the incoming data
    Serial.println(data); //Printing value to serial monitor
  }
  
  //Forward
  if(data == 'f') {
    analogWrite(IN1, velocity);
    analogWrite(IN2, 0);
    analogWrite(IN3, velocity);
    analogWrite(IN4, 0);
  }
  //Backward
  if(data == 'b') {
    analogWrite(IN1, 0);
    analogWrite(IN2, velocity);
    analogWrite(IN3, 0);
    analogWrite(IN4, velocity);
  }
  //Right
  if(data == 'r') {
    analogWrite(IN1, velocity);
    analogWrite(IN2, 0);
    analogWrite(IN3, 0);
    analogWrite(IN4, velocity);
  }
  //left
  if(data == 'l') {
    analogWrite(IN1, 0);
    analogWrite(IN2, velocity);
    analogWrite(IN3, velocity);
    analogWrite(IN4, 0);
  }
  //Brake...Hold
  if(data == 'h') {
    analogWrite(IN1, 0);
    analogWrite(IN2, 0);
    analogWrite(IN3, 0);
    analogWrite(IN4, 0);
  }
}
