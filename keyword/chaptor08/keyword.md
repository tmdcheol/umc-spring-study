# 

## Java의 Exception 종류들

Java에서 **예외(Exception)**는 프로그램 실행 중 발생할 수 있는 오류를 처리하기 위해 사용됩니다. 예외는 Throwable 클래스를 상속하며 크게 두 가지로 나뉩니다.

### **Checked Exception**
* 컴파일 타임에 반드시 처리해야 하는 예외입니다.
* 발생 가능성이 높은 예외를 컴파일러가 미리 체크하도록 강제합니다.
* **try-catch로 처리하거나 throws로 선언해야 합니다.**

⠀**주요 예**
* IOException 파일 입출력 도중 오류 발생 시 던집니다. 예: 파일이 존재하지 않거나 읽기/쓰기 권한이 없는 경우.
* SQLException 데이터베이스와 상호작용하는 동안 발생하는 예외입니다. 예: 잘못된 SQL 구문이나 데이터베이스 연결 실패.
* ClassNotFoundException 프로그램이 실행 중 특정 클래스를 찾지 못할 때 발생합니다. 예: 리플렉션을 사용할 때 발생.
* ParseException 문자열을 특정 형식으로 변환(parsing)할 때 실패하면 발생합니다. 예: 날짜 포맷 오류.


### **Unchecked Exception**
* 런타임(Runtime) 중에 발생하며 컴파일러가 체크하지 않습니다.
* 반드시 처리할 필요는 없지만, 발생 시 프로그램이 비정상 종료될 수 있습니다.
* 주로 사용합니다.

⠀**주요 예**
* NullPointerException 참조가 없는 객체를 호출하려고 할 때 발생.
* ArrayIndexOutOfBoundsException 배열의 범위를 초과하는 인덱스를 접근하려고 할 때 발생.
* ArithmeticException 수학적 계산 오류, 예를 들어 0으로 나누기.
* IllegalArgumentException 메서드에 전달된 인수가 부적합할 때 발생.
* NumberFormatException 문자열을 숫자로 변환하려 할 때 실패하면 발생. 예: "abc"를 Integer.parseInt로 변환.


> 참고: **Error**
> * 프로그램이 복구할 수 없는 심각한 문제를 나타냅니다.
> * 예외와 달리 일반적으로 처리하지 않으며, **시스템 관련 오류**입니다.
    > ⠀**주요 예**
> * StackOverflowError 메서드의 무한 재귀 호출로 스택 메모리 초과 시 발생.
> * OutOfMemoryError 힙 메모리가 부족한 경우 발생.
> * VirtualMachineError JVM 동작에 심각한 문제가 생긴 경우 발생.

⠀
## @Valid
Java에서 **@Valid**는 데이터 검증(Validation)을 자동화하는 데 사용되는 어노테이션입니다. 주로 Spring Framework와 Bean Validation(Jakarta Validation, Javax Validation)을 함께 사용할 때 사용됩니다.

**주요 사용 목적**
* 객체 내부 필드에 대한 **데이터 유효성 검증**을 선언적으로 정의합니다.
* 컨트롤러, 서비스, 또는 기타 계층에서 데이터를 검증할 수 있습니다.

⠀**주요 검증 어노테이션**
* @NotNull 값이 null이 아니어야 함.
* @NotBlank **문자열이** null, 빈 문자열, 공백 문자로만 이루어지지 않아야 함.
* @NotEmpty 배열, 컬렉션, 맵, 또는 문자열이 비어 있지 않아야 함.
* @Size(min = x, max = y) 문자열, 배열, 컬렉션의 크기가 x 이상 y 이하여야 함.
* @Pattern(regexp = "regex") 특정 정규식에 부합하는지 검증.
* @Min(value) / @Max(value) 숫자가 특정 최소값/최대값을 만족해야 함.
* @Email 올바른 이메일 형식인지 확인.

**검증 실패 처리**
* 보통 @Valid를 사용해 발생한 에러를 Global Exception Handler로 받아서, 실패 응답을 커스터마이징

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		...
    }
}

```


**커스텀 검증 어노테이션**
* 필요시 직접 어노테이션을 정의 가능합니다.

```java
@Documented
@Constraint(validatedBy = CustomValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomValid {
    String message() default "default message";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

```

⠀

