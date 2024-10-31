# IoC (Inversion of Control)

IoC는 제어의 역전(Inversion of Control)입니다.

일반적인 코딩 방식에서는 객체 간 의존관계가 존재할 때, 객체 내에서 필요한 객체를 직접 생성합니다.

**기존 코드**

```java
public class MemberService {
    // MemberRepository는 interface 라고 가정합니다.
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    ...
}
```

MemberService에서 직접 `MemoryMemberRepository`라는 인스턴스를 생성합니다. 이는 사실 SOLID에서 DIP(Dependency Inversion Principle)를 위반하고 있는 코드입니다.

- **DIP**: 프로그래머는 "추상화에 의존해야지, 구체화에 의존하면 안된다."

`MemberRepository`가 interface일 때, MemberService라는 클라이언트는 `new MemoryMemberRepository();`라는 코드로 구체화에 의존하고 있습니다. 이는 `MemberRepository`라는 인터페이스의 구현체를 바꿔야 하는 상황에서 Client Code가 수정되어야 하는 문제를 야기합니다. 즉, `new MemoryMemberRepository();` 코드를 수정해야 하는 것입니다. 이는 SOLID의 OCP(Open/Closed Principle) 위반으로도 이어집니다.

- **OCP**: 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.

이를 극복하기 위해 IoC를 적용할 수 있습니다.

**IoC가 적용된 코드**

```java
public class MemberService {
    private final MemberRepository memberRepository;
    
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    ...
}

public class AppConfig {
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
```

객체를 생성하고 필요한 의존관계를 설정해주는 작업을 외부 코드에서 제어하게 됩니다. 이렇게 객체를 생성하고 설정하는 작업을 외부에서 하는 것이 바로 IoC입니다. `AppConfig` 클래스에서 객체를 생성하고 관리하는 책임을 담당하며, 기존의 `MemberService`에서 객체를 생성하고 주입하는 책임을 분리함으로써 SRP(Single Responsibility Principle) 개념도 적용된 것입니다.

---

# 프레임워크와 API의 차이

- **프레임워크**: 코드의 실행 흐름이 이미 정해져 있는 프로그램입니다.
- **API**: 특정 기능을 사용하기 위한 interface입니다.

우리는 Spring Framework의 정해진 동작 흐름에 맞춰서 코드 작업을 해야 합니다. 따라서 Framework를 사용해서 개발할 때는 Framework의 동작 방식을 이해하고 사용하는 것이 중요합니다.

API라는 말은 다양한 곳에서 쓰입니다. 예를 들어, 백엔드 개발 시 API 명세서를 작성할 때, 외부 API를 사용할 때, Java에서 제공하는 기능을 말할 때 등입니다. 이들의 공통점은 개발자가 API가 제공하는 인터페이스를 사용하여 특정 기능을 제공받는다는 것입니다.

- **Framework**: 동작 흐름에 맞춰서 개발해야 함.
- **API**: 특정 기능을 제공받기 위해 자유롭게 인터페이스를 사용함.

---

# AOP (Aspect-Oriented Programming)

AOP는 관점 지향 프로그래밍입니다. 여러 클래스에서 반복적으로 나타나는 공통적인 기능을 **횡단 관심사**라고 하며, 일반적으로 로깅, 보안, 트랜잭션 관리가 포함됩니다. AOP를 사용하면 이러한 공통 기능을 분리하여 코드의 유지보수성과 재사용성을 높일 수 있습니다.

- **Aspect**: 관심사를 의미합니다. 예: 로깅, 트랜잭션
- **JoinPoint**: Aspect가 삽입될 수 있는 지점. 예: 메서드 호출, 예외 발생
- **Pointcut**: JoinPoint 중 Aspect가 적용될 곳을 선정하는 표현식
- **Advice**: Aspect가 실제로 수행할 작업. 특정 Pointcut에서 실행됩니다.
    - **Before Advice**: 메서드 실행 전
    - **After Returning Advice**: 메서드 정상 실행 후
    - **After Throwing Advice**: 메서드 실행 중 예외 발생 후
    - **After (Finally) Advice**: 메서드 실행 후 (정상이든 예외든)
    - **Around Advice**: 메서드 실행 전후, 실행 여부 결정 가능
- **Weaving**: Aspect를 실제 코드에 적용하는 과정

**AOP의 장점**

- **모듈화**: 횡단 관심사를 Aspect로 분리해 코드의 응집도를 높이고, 특정 기능 변경이나 추가가 쉬워짐.
- **유지보수성**: 코드가 간결해지고, 핵심 로직과 부가적인 관심사가 분리되어 유지보수가 용이함.
- **재사용성**: 공통 기능을 재사용해 코드 중복을 줄일 수 있음.

**Spring AOP**

Spring Framework는 주로 **프록시 기반 AOP**를 사용합니다. **@Aspect** 어노테이션을 사용하여 정의합니다.

---

# 서블릿

Servlet는 공식 Java API로, 동적 웹 애플리케이션을 구축하는 데 주요 역할을 합니다. 이러한 Servlet을 편리하게 실행할 수 있는 환경을 제공하는 라이브러리가 Tomcat입니다. 따라서 Tomcat은 Servlet Container라고도 불립니다.

Tomcat은 크게 두 가지 작업을 수행합니다: **catalina**와 **coyote**.

1. **네트워크 요청을 받고 응답**: coyote
2. **요청을 받아 Servlet을 통해 동적 작업을 실행**: catalina (Servlet Container)

HTTP Protocol 형식에 맞게 요청 메시지를 파싱하고, 응답 메시지를 쉽게 생성하도록 돕는 역할을 하는 것이 Servlet Java API입니다. 이러한 Servlet을 관리하는 Servlet Container가 Tomcat의 catalina입니다.

Tomcat의 **coyote**는 네트워크 요청과 응답을 관리하며, 스레드를 이용해 다수의 클라이언트와 동시에 통신할 수 있는 기능을 제공합니다.

Spring Boot는 Tomcat을 내장하고, Servlet을 상속받은 `DispatcherServlet`을 제공하여 개발자가 편리하게 서버 개발을 할 수 있도록 많은 기능을 제공합니다. `DispatcherServlet`은 Spring Container와 연결되어, 등록된 `Controller` Bean 중 하나를 선택해 동작을 수행합니다.

---

**Servlet Container의 역할**

- HTTP Protocol 형식에 맞춰 요청 메시지를 쉽게 관리
- `HttpServletRequest`, `HttpServletResponse` 객체 제공
- Thread Pool을 이용해 다수의 클라이언트 요청 처리

