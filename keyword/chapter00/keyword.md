
# 🎯 핵심 키워드

### **IP**
- 인터넷 망 내에서 특정 컴퓨터 식별자 역할을 한다.
- OSI 7계층에서 network Layer에서 필요
- TCP/IP 4계층에서 internet Layer에서 필요
---
### **PORT**
* 하나의 컴퓨터 내에서 프로세스를 식별하는 역할을 한다.
* OSI 7계층에서 transport Layer에서 필요
- TCP/IP 4계층에서 transport Layer에서 필요
- --
### **CIDR**
* Classless Inter-Domain Routing

인터넷 상의 데이터 라우팅 효율성을 향상시키는 **IP 주소 할당 방법**이다. 조직에서 CIDR을 사용하여 네트워크에 유연하고 효율적으로 IP 주소를 할당한다.

**다른 IP 주소형식으로는 어떤 것?**
- 클래스 기반 주소 지정 시스템
- IP 주소는 네트워크 주소, 호스트 주소로 나뉜다.
- 클래스 기반 주소 방식은 클래스 A,B,C로 나뉘어 네트워크 주소, 호스트 주소의 비트 수가 고정된다.

CIDR은 가변 길이 서브넷 마스킹(VLSM)을 사용하여 네트워크 주소와 호스트 주소 비트 비율을 변경한다.
서브넷 마스크는 호스트 주소를 0으로 변환하여 네트워크 주소 값을 반환하는 값이다.


#### CIDR의 장점

- **IP 주소 낭비 감소**
  VLSM을 이용하여 네트워크 관리자는 다양한 크기의 서브넷을 부여 가능하다. 네트워크 주소 접두사 비트 수를 나타내는 접미사 값을 일반 IP 주소에 추가한다.
  ex. 192.0.2.0/24
  192.0.2 -> 네트워크 주소
  클래스 기반 배치는 IP 주소 공간의 낭비로 이어진다. CIDR은 이를 해결할 수 있다. 네트워크 주소의 길이를 지정가능하기 때문에 특정 네트워크에 필요한 IP 주소 개수를 유연하게 할당 가능 하다.

- **슈퍼넷을 유연하게 생성**
  VPC는 연결된 디바이스 간에 데이터 패킷을 전송할 때 CIDR IP 주소를 사용한다.

----
### **TCP와 UDP 차이**
* TCP는 IP 만으로 통신했을 때 부족한 점을 보완한다.
  * 연결 지향
  * 순서 보장
  * 전달 보장
* UDP는 TCP에서 많은 기능이 제외된다.
  * IP, Port, Checksum 정도만 포함한다.
  * application Layer에서 많은 부분을 담당해야 한다.

---
### **Web Server와 WAS의 차이**
- Web Server
  - 정적 리소스 제공
- WAS
  - 동적 리소스 제공
  - Web Server의 기능 수행 가능
* Web Server와 WAS의 혼용
  * Web Server는 보안, reverse proxy, 정적 리소스 제공, WAS의 에러 발생 시 클라이언트에게 에러 페이지 전달 등 여러 기능을 대신 수행한다.
  - WAS는 무겁고 에러가 빈번하며 비싼 서버이다. 따라서 WAS가 할 수 있더라도, Web Server가 잘 담당할 수 있는 기능은 Web Server에게 책임 분리한다.

---
## 추가 정리

### **OSI 7계층과 TCP/IP 4계층 모델**

**웹 브라우저 통신과정**
* 크롬 브라우저 검색창에서 url 을 입력
* 크롬 브라우저는 이를 네트워크에서 http 형식에 맞게 데이터 생성
* 인터넷을 통해 네이버 서버로 전달된다.
* 네이버 서버는 이 패킷을 풀어서, 데이터를 해석한다.

**송신 호스트**
- 우리는 브라우저라는 응용 계층을 통해서 요청한다.
- 각 데이터들은 순서를 지키면서 한 계층씩 내려간다.
  - 각 계층에서 필요한 데이터를 기존 데이터에 추가하면서 내려간다.

**수신 호스트**
- 인터넷을 통해서 서버 컴퓨터에 데이터가 전달된다.
- 그러면 송신 호스트와 **역순으로** 각 계층에서 필요한 데이터를 분리하여 해석한다.
- 실제 서버 어플리케이션에서는 클라이언트가 요청한 http 형식의 데이터가 전달된다.
- 그리고 http 형식의 데이터를 서버가 해석하고, 그에 따른 응답 메세지를 http 형식으로 다시 만들어서 클라이언트에게 응답한다.


### 프로토콜이란?
- 규약

네트워크에서 필요한 프로토콜은 크게 HTTP, TCP, UDP 가 있을 것이다.
해당 프로토콜들은 각각 다른 계층에서 필요한 규약이다.



### OSI 7계층 모델이란?
- 각 계층에서 해야할 일을 책임분리한다.

**응용 계층(Application Layer)**
- 최상위 계층

**표현 계층(Presentation Layer)**
- 계층 간 데이터를 적절히 표현하는 부분을 담당한다.
- 이미지를 압축, 데이터를 암호화

**세션 계층(Session Layer)**
- 통신 세션을 구성

**전송 계층(Transport Layer)**
- 컴퓨터로 들어온 네트워크 데이터를 어느 포트(어느 프로세스)로 보낼지 담당
- TCP protocol을 사용한다면?
  - 신뢰성 있는 데이터를 보장하는 역할
    - 논리적으로 연결 - 3 way handshake
    - 데이터 순서 보장

**네트워크 계층(Network Layer)**
- IP 주소를 통해 어느 컴퓨터로 보낼지 담당

**데이터링크 계층(Data Link Layer)**
- Mac 주소

**물리 계층(Physical Layer)**
- 디지털 신호



### TCP/IP 4계층 모델이란?
- 실제 대부분의 인터넷 통신은 TCP/IP 통신을 사용한다. OSI 7계층을 단순화 한다.

**응용 계층(Application Layer)**
- Http, Telnet, ssh, ftp 같은 프로토콜이 여기에서 사용

> **개인적인 생각 정리**
> 
> 우리가 개발하고 있는 Spring Boot Server는 tomcat을 내장하고 있다.
> 개발자는 tomcat 및 Spring Web을 기능을 이용해 코드 수준에서 Session(tomcat이 지원하는 session), Presentation(데이터 압축), Application(thymeleaf, tomcat의 데이터 입출력) 를 건드릴 수 있다.
> 그래서 경계가 모호하다. 따라서 OSI 7 계층에서 5,6,7의 계층이 하나로 합쳐진 것 같다.


**전송 계층(Transport Layer)**
- 신뢰성 있는 데이터 전송 -> **TCP 를 사용해야만**
- UDP는 TCP의 많은 기능들이 제외된다. 신뢰성을 확보하지 못하며 Application 계층에서 이를 담당 해야한다.

**인터넷 계층(Internet Layer)**
- IP
- 컴퓨터간 라우팅을 담당한다.

**네트워크 인터페이스 계층(Network Interface Layer)**
- 물리적 계층


### HTTP vs HTTPS
- Application Layer와 Transport Layer 사이에 SSL/TLS 계층이 존재한다.
- HTTP는 보안에 취약하기 때문에 HTTPS가 이를 보완한다.


---
### 인터넷
- 데이터를 전달해주는 거대한 network 망

**인터넷 구성**
- 인터넷은 여러가지 형태의 network와 그 안의 sub-network로 구성된다.
- network edge, access network, network core

**network edge**
- 끝에 있는 entity
- end system
- web browser, 스마트폰, 개발할 server

**access network**
- internet과 연결되는 첫 부분
- 랜선, 와이파이 등

**network core**
- 데이터를 실어 나르는 역할
- router

---
### 인터넷 통신
- end system 간 데이터(Packet)를 주고 받는 것
- 주고 받는 데이터 형식은 TCP or UDP, IPv4 or IPv6 에 따라 형태가 달라진다.

**IP**
- 인터넷 상에서 대상을 식별하는 수단
- 문제점
  - 라우터들은 OSI 7계층에서 3계층까지만 보고 데이터를 응답 요청.
  - 패킷이 소실된다면?

**Port**
- 같은 IP 내 프로세스 구분 용도

**TCP**
- 3 way-handshake를 통해 상태확인을 함으로써 논리적 연결 확인
- 데이터 전달 보증
  - 패킷이 손실될 수 있기 때문에 데이터를 받았다는 것을 상대에게 알려줌
- 데이터 순서 보장
  - 패킷에 sequence number를 붙히고 패킷 간의 순서를 특정한다.
  - 도착 순서가 잘못되었다면 잘못된 곳부터 다시 달라고 요청한다.

**UDP**
- IP, PORT, checksum 정도만 있다.
- TCP와 같은 계층이지만 많은 기능이 제외
- Application에서 많은 제어가 필요
- HTTP/3는 UDP 기반


---
### Web Server와 WAS

**web server**
- 정적인 자원 제공
- Apache, Nginx

**WAS**
- 동적 리소스 제공
- web server 기능도 제공
- Tomcat

요즘은 web server와 WAS를 혼용한다.
Web Server는 WAS 앞단에 위치하여 많은 역할을 담당한다.
- reverse proxy
- 로드 밸런싱
- 보안
- 정적 리소스 제공
- WAS 에러 시 클라이언트에게 에러 페이지 제공 등