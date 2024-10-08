**1. 홈 화면**  
**GET /api/v1/home**
* **설명**: 홈 화면을 조회합니다.
* **URL**: /api/v1/home
* **Method**: GET
* **요청 헤더**:
    * Authorization: token
* **응답**
```javascript
{
  "isSuccess": true,
  "code": "COMMON200",
  "message": "성공",
  "result": {
    "completedMissionCount": 7,
    "missions": [
      {
        "restaurantName": "식당이름",
        "restaurantCategory": "중식",
        "missionExpirationDate": "2024-12-31",
        "missionDescription": 10000,
        "points": 500
      },
      {
        "restaurantName": "식당이름",
        "restaurantCategory": "중식",
        "missionExpirationDate": "2024-12-31",
        "missionDescription": 10000,
        "points": 500
      },
    ]
  }
}

```
<br></br>
**2. 마이 페이지 리뷰 작성**  
**POST /api/v1/my-page/reviews**
* **설명**: 마이 페이지 리뷰를 작성합니다.
* **URL**:  /api/v1/my-page/reviews
* **Method**: POST
* **요청 헤더**:
    * Authorization: token
- **요청 BODY**:
```javascript
{
  "missionId": 1,
  "restaurantId" : 1
  "rating": 5,
  "content": "내용"
}

```
* **응답**
```javascript
{
  "isSuccess": true,
  "code": "COMMON200",
  "message": "성공",
  "result": {
    "reviewId": 1
  }
}

```
<br></br>
**3. 미션 목록 조회(진행중, 진행 완료)**  
**GET /api/v1/missions**
* **설명**: 미션 목록을 조회합니다.
* **URL**: /api/v1/missions
* **Method**: GET
* **요청 헤더**:
    * Authorization: token
- **요청 쿼리 파라미터:**
    - status : progress or completed
- **요청 BODY**:
```javascript
{
  "regionName": "안암동"
}
```
* **응답**
```javascript
{
  "isSuccess": true,
  "code": "COMMON200",
  "message": "성공",
  "result": {
    "missions": [
      {
        "restaurantId": 1,
        "restaurantName": "식당이름",
        "restaurantCategory": "한식",
        "missionPoints": 500
      },
      {
        "restaurantId": 2,
        "restaurantName": "식당이름",
        "restaurantCategory": "양식",
        "missionPoints": 500
      }
    ]
  }
}


```
<br></br>
**4. 미션 성공 누르기**  
**POST /api/v1/missions/{mission-id}/completed**
* **설명**: 특정 미션의 성공 후 사장님께 요청합니다.
* **URL**: /api/v1/missions/{mission-id}/completed
* **Method**: POST
* **요청 헤더**:
    * Authorization: token
* **응답**
```javascript
{
  "isSuccess": true,
  "code": "COMMON200",
  "message": "성공",
  "result": {
    "missionId": 1,
    "bossAuthCode": "9201982309"
  }
}
```
<br></br>
**5. 회원 가입 하기(소셜 로그인 고려 X)**  
**POST /api/v1/users/sign-up**
* **설명**: 회원 가입 하기
* **URL**: /api/v1/users/sign-up
* **Method**: POST
* **요청 BODY**
```javascript
{
  "name": "홍길동",
  "gender": "남자",
  "birthYear": 2000,
  "birthMonth": 1,
  "birthDay": 1,
  "address": "인천광역시 OO구 OO길 OO",
  "detailedAddress": "OOO호",
  "preferredFoodCategories": ["한식", "양식", "일식"]
}
```
- **응답 BODY**
```javascript
{
  "isSuccess": true,
  "code": "COMMON200",
  "message": "성공",
  "result": {}
}
```
<br></br>
**6. 에러**
- **설명:** 에러 응답 형식을 설명
- **응답 BODY**
```javascript
{
  "isSuccess": false,
  "code": "COMMON500",
  "message": "서버 에러, 관리자에게 문의 바랍니다.",
  "result": null
}
```
