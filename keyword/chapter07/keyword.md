# 

### @RestControllerAdvice
@RestControllerAdvice는 Spring Framework에서 제공하는 애너테이션으로, 주로 **REST API 예외 처리를 전역적으로 처리할 때 사용**됩니다. 이 애너테이션은 @ControllerAdvice의 확장판으로, @RestController 또는 @RequestMapping이 사용된 컨트롤러에 대한 예외를 전역적으로 처리할 수 있습니다. REST 컨트롤러와 관련된 전역 예외 처리를 관리하며, JSON 포맷의 응답을 반환합니다.


**동작 구조**
* @RestControllerAdvice는 @ExceptionHandler 메서드를 정의하여 특정 예외가 발생했을 때 실행될 메서드를 지정합니다.
* 모든 컨트롤러 또는 특정 컨트롤러에 대해 전역적으로 예외 처리를 적용할 수 있습니다.
* 응답은 일반적으로 JSON 형태로 클라이언트에 전송됩니다.


```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // 예외 처리 및 응답 반환
        return new ResponseEntity<>("Illegal argument error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        // 일반 예외 처리
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

```


위 예시에서, IllegalArgumentException이나 다른 예외가 발생할 때 지정된 메서드가 실행되며, 클라이언트에 JSON 응답으로 에러 메시지를 전달합니다.

---
### Lombok

Lombok은 Java에서 반복적인 코드를 줄이기 위해 사용되는 라이브러리입니다. 주로 자주 사용되는 메서드나 필드를 자동으로 생성해주는 애너테이션을 제공하여 개발자가 더 적은 코드로 생산성을 높일 수 있습니다. Lombok은 컴파일 타임에 어노테이션을 처리하여 필요한 코드를 생성합니다. Java 소스 코드를 컴파일할 때, Lombok의 애너테이션을 사용하면 컴파일러가 Lombok이 추가해주는 메서드나 필드 등을 생성하여 최종 바이트코드에 반영합니다.

**동작 과정**
- Java 컴파일러가 소스 코드를 컴파일할 때, Lombok은 Annotation Processor로 동작하며, 소스 코드에 있는 Lombok 애너테이션을 처리해 실제 메서드나 필드를 생성합니다.
- 바이트 코드를 생성합니다. 컴파일된 .class 파일에는 Lombok이 생성한 메서드나 필드가 포함된 상태로 저장됩니다.


**주요 애너테이션**
* @Getter / @Setter: 클래스의 필드에 대해 getter 및 setter 메서드를 자동으로 생성합니다.
* @ToString: 클래스의 toString() 메서드를 자동 생성합니다.
* @EqualsAndHashCode: equals() 및 hashCode() 메서드를 자동 생성합니다.
* @NoArgsConstructor / @AllArgsConstructor / @RequiredArgsConstructor: 생성자 자동 생성.
* @Data: 위의 여러 애너테이션 (@Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode)의 기능을 포함하는 종합적인 애너테이션입니다.
* @Builder: 빌더 패턴을 사용할 수 있도록 생성자 및 메서드를 자동으로 생성합니다.

⠀**동작 구조**
* Lombok은 소스 코드를 분석하고 필요한 메서드를 자동으로 추가하여 보일러플레이트 코드를 줄여줍니다. 컴파일러가 코드를 컴파일할 때 Lombok이 처리하여 코드를 변경합니다.
* 주의할 점은 IDE에서 Lombok을 사용할 때, Lombok 플러그인을 설치해야 자동 생성된 메서드들을 문제없이 인식할 수 있습니다.


* 이 외에도 보일러 플레이트 코드를 줄이기 위해 java 14부터 지원하는 공식 스펙인 Record type도 있습니다.
