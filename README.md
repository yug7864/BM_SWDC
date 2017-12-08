# BM_SWDC

반갑습니다. Black Mirror 입니다.  1차 버전을 올릴수 있어 매우 기쁩니다. 

- Home IoT System Environment 
- OS 				: raspbian-jessie
- OS version 		: Linux raspberrypi  4.9-35-v7+ 
- Apache2 version 	: 2.4.10 (Raspbian) 
- PHP5 version 		: 5.6.30-0+deb8u1 
- Mysql version 	: 14.14 
- Python version 	: 3.4.2
- 원격접속 설치		 : 1. tightvncserver 2. xrdp
- 네트워크 구성 		 : <IP static 설정>
- 방화벽 			   : 80, 22, 23 열어줌 

1. Android App 빌드 방법 입니다. 
	- 프로젝트 파일을 다운받아주세요.
			Clone or download -> Download ZIP

	- 다운받은 ZIP 파일의 압축을 풀어주세요
	- Android Studio 초기화면 에서 "Open an existing Android Studio project"을 통해 프로젝트 파일을 열어주세요.
	- Android Studio 에 프로젝트 파일을 열때 필요한 업데이트가 있다면 업데이트를 해야 합니다. 
	- 프로젝트 파일이 열리면 상단의 Build 탭 - Make Build 를 클릭하여 프로젝트 파일을 Build 해 주십시요 
	- 프로젝트 파일 Build 가 완료되면 축하드립니다. 이제부턴 프로젝트 파일을 사용하실 수 있습니다.
	
2. Home IoT System 설정 방법 입니다. 
	- APM 환경 설정이 먼저 해주셔야 합니다. 
	- BM_SWDC-master(Project Root)/app/src/main/java/com/example/yug08 의 경로에 들어가시면 "ETC" 디렉토리가 존제 합니다.
	- ETC 디렉토리 안의 "BM" 디렉토리를 복사하여 라즈베리파이의 var/www/html/ 경로로 복사해 주세요. ( 만약 실행이 안된다면 파일 권한을 변경하시기 바랍니다.)
	- 보고서의 HW 센서를 제작한뒤 "ETC" 디렉토리의 "HW_ardu" 디렉토리의 "Lamp_swit.ino" 는 전등 스위치
	- "Win_check.ino" 는 창문 스위치로 "Arduino IDE" 를 사용하여 컴파일후 업로드를 해주세요.
	- 두개의 HW에 업로드가 완료되면 전원을 넣어 HW를 작동시켜 주십시오.
	- HW에 전원이 들어온것을 확인하시면 "ETC" 디렉토리의 "Use_Code"디렉토리 안에 있는 [LampSwit_final.py 및 winCheck_final.py] 두 파일을 Python 3.4 version 을 사용해 실행하여 주십시오.
	
	
축하드립니다. 이 모든 과정을 무사히 마치셨다면 여러분은 외부 에서 IP 번호를 사용하여 전등을 끄거나! 켤수 있습니다. 
혹은 외출모드를 사용하여 방문자를 확인 할수도 있습니다. 
	 
			

			
