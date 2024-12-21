
### 1. Spring Security
인증(Authentication)과 인가(Authorization)을 제공하기 위한 강력하고 확장 가능한 보안 프레임워크

- 인증(Authentication) : 사용자 확인
- 인가(Authorization) : 인증된 사용자의 접근 권한 확인
- 보호 : CSRF 방어, 세션 고정 공격 방어, HTTP 응답 헤더 설정 등을 통해 어플리케이션 보호

**Servlet Filter** 체인을 활용하여 보안 처리를 수행하는 것이 특징. 따라서 이는 요청(Request)와 응답(Response)을 가로채고 필요한 인증 및 인가 로직을 처리하기에 적합하다.

Spring Security는 Servlet Filter를 기반으로 동작하며, 필터 체인(Filter Chain)을 사용해 요청과 응답을 가로채고 보안 로직을 적용한다.

**Spring Security의 주요 필터 체인**

- **SecurityContextPersistenceFilter**
    * SecurityContext를 생성하거나 복원하여 요청에 적용.
    * 요청 완료 후 컨텍스트를 저장.
- **UsernamePasswordAuthenticationFilter**
    * 사용자의 자격 증명을 처리 (기본적으로 폼 기반 로그인 처리).
- **BasicAuthenticationFilter**
    * HTTP Basic 인증을 처리
- **AnonymousAuthenticationFilter**
    * 인증되지 않은 사용자에게 익명 권한을 부여
- **FilterSecurityInterceptor**
    * 인가(Authorization) 결정을 수행.
    * AccessDecisionManager를 호출하여 요청한 리소스에 대한 권한을 확인.


----

### 2. 인증(Authentication)
사용자의 신원을 확인하는 과정.

**Spring Security 주요 개념**
- Authentication 객체
    - 사용자의 인증 상태를 나타낸다.
    - SecurityContext에 저장되어 애플리케이션 전역에서 사용된다.
    - 주요 필드
        - Principal : 인증된 사용자 정보 (UserDetails)
        - Credential : 사용자의 인증 자격 증명 (ex. 비밀번호)
        - Authorities : 사용자가 가진 권한 목록
- SecurityContext
    - 요청의 보안 정보를 저장하는 컨텍스트
    - SecurityContextHolder를 통해 접근 가능하다.
- AuthenticationManager
    - 인증 로직을 처리하는 인터페이스
    - 일반적으로 ProviderManager가 구현체
    - 여러 AuthenticationProvider를 위임받아 인증을 수행



**인증 흐름**
- 사용자가 애플리케이션에 요청을 보냄 (로그인 폼, API 요청 등).
- UsernamePasswordAuthenticationFilter
    - 요청의 인증 정보를 파싱하여 Authentication 객체를 생성.
    - 이 객체를 AuthenticationManager에 전달.
- AuthenticationManager는 여러 AuthenticationProvider 중 적합한 것을 선택하여 인증을 수행.
- AuthenticationProvider
    - 데이터베이스, LDAP, OAuth 등 인증 방식을 구현.
    - 사용자가 제공한 자격 증명(Credentials)과 저장된 정보 비교.
- 인증
    * 성공 시 Authentication 객체를 생성하여 SecurityContext에 저장.
    * 실패 시 예외를 던지로 실패 처리 로직으로 이동


---
### 3. 인가(Authorization)
**Authorization**은 사용자가 특정 리소스에 접근할 수 있는 권한이 있는지 확인하는 과정.

**주요 개념**
* **GrantedAuthority**
    * 사용자의 권한을 나타내는 인터페이스.
    * 일반적으로 ROLE_USER, ROLE_ADMIN 등과 같은 역할(Role)을 정의.
- **AccessDecisionManager**
    - 인가 여부를 판단하는 핵심 컴포넌트.
    - AccessDecisionVoter를 사용하여 투표 방식으로 권한을 판단.

**인가 흐름**
- 인증된 사용자가 요청한 리소스를 FilterSecurityInterceptor가 가로챔.
- 요청 URL과 HTTP 메서드에 따라 적용할 보안 정책을 결정 (SecurityConfig에 정의된 설정 참조).
- **AccessDecisionManager**가 호출되어 인가 여부를 판단
    - 요청한 리소스에 대한 권한과 사용자의 권한을 비교
- 권한 검사가 성공하면 요청이 처리됨.
- 실패 시, AccessDeniedException이 발생

