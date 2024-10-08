🔥 **미션**
---
- 1번 (너디너리 홈페이지 접속하는 과정 적어보기)
1. **URL 입력 및 리다이렉트 확인**
   - 리다이렉트를 확인
      - 예를 들어 HTTP -> HTTPS 리다이렉트 처리
2. **캐시 확인**
   - 브라우저 내 캐싱 가능한 데이터인지 확인한다. 캐싱 불가능 데이터는 요청한다.
3. **HTTP 요청 메세지 생성**
   - 현재 예시는 너디너리 홈페이지 접속이기 때문에 GET 요청 메세지 생성
   - 사용자의 요청에 따라 POST 요청, key value 형태의 쿼리 파라미터 생성 가능
4. **DNS 조회**
   - 도메인 이름을 통해서 IP 주소를 가져온다.
   - DNS 또한 캐싱이 일어날 수 있다. 브라우저 캐싱과 OS 캐싱이 있다.
5. **TCP 3 way handshake**
   - 클라이언트 서버 간 연결 확인
6. **SSL/TLS handshake**
   - HTTPS 인 경우 수행
7. **HTTP 요청 메세지 전송**
   - Internet Layer, Network Interface Layer 순서대로 내려가서 필요 데이터를 추가한다.
   - IP 라우팅과 ARP 프로세스 등이 발생
   - 인터넷 망을 통해 요청 데이터가 서버에게 도달된다.
8. **서버 컴퓨터에게 요청 데이터 도달**
   - 서버 컴퓨터는 요청 데이터를 아랫 계층에서 윗 계층 순으로 확인
   - 너디너리 서버 어플리케이션에게 필요한 요청 데이터 전달
9. **너디너리 서버의 응답 메세지 생성**
   - 너디너리 서버는 응답 HTTP 데이터를 생성하고, 아래 계층으로 보냄
   - 각 계층들은 필요한 데이터를 추가
   - 인터넷 망으로 보내짐
10. **클라이언트에게 응답 데이터 도달**
   - 최하위 계층부터 위 계층 순으로 필요한 데이터를 추출하고 웹 브라우저는 HTTP 응답 메세지 확인
11. **브라우저 렌더링**
   - 혹시 필요한 추가 데이터가 있다면 추가 요청을 보냄.
   - HTTP/1.1 기준으로 연결을 끊지 않고(keep-alive), 이미지, JS, CSS를 추가 요청


**캐싱이란?**
- 크게 브라우저캐시와 공유캐시로 나뉜다.

1. 브라우저 캐시
   - 쿠키, 로컬스토리지 등을 포함한 캐시(private cache)
   - 브라우저 자체가 사용자를 위해 가지고 있는 데이터를 의미한다.
2. 공유 캐시
   - 클라이언트와 서버 사이에 사용자 간 공유할 수 있는 응답을 저장
   - 대표적 예로는 프록시 서버가 캐싱하는 것
      - 리버스 프록시를 둬서 내부서버로 포워드 한다고 호칭
   - CDN 서버를 둬서 캐싱 가능

---
- 2번
  <img width="1016" alt="스크린샷 2024-09-16 오후 6 00 33(2)" src="https://github.com/user-attachments/assets/ae8b4f0b-9db8-4846-8c38-7323de0d559f">