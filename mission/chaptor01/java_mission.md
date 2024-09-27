 🔥 추가미션
---
### 자바의 접근 제어자 4가지
- **public**
    - 모든 파일에서 접근 허용
- **protected**
    - 같은 패키지에서 접근 허용
    - 상속받은 클래스에서 접근 허용
- **default(package-private)**
    - 접근 제어자를 명시하지 않으면 default
    - 같은 패키지에서 접근 허용
- **private**
    - 클래스 내에서만 접근 가능


**protected 사용 예시**
```java

@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public abstract class AdditionalInfo {

    @JsonProperty("추가정보")
    @Builder.Default
    protected Map<String, Object> addtionalInfoMap = new HashMap<>();

    public Optional<Object> getAdditionalInfo(String key) {
        return Optional.ofNullable(addtionalInfoMap.get(key));
    }
}

@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@ToString
public class CurriculumCode extends AdditionalInfo {

    @JsonProperty("교과과정")
    @Builder.Default
    private Map<Category, Set<String>> codeMap = new HashMap<>();

...
}

```


---
### 클래스와 인터페이스

- **클래스**
    - 인스턴스 객체의 설계도
    - 필드, 메소드를 정의
    - 상속을 통해 확장 가능

> 참고: 추상클래스
> 추상클래스는 해당 클래스를 인스턴스화 할 수 없으며, 반드시 상속 받은 클래스만 인스턴스화 가능하다.

- **인터페이스**
    - 구현되지 않은 메소드들의 집합
    - 메소드의 접근제어자는 자동으로 public
    - 다중 상속 지원

> 참고: 인터페이스는 ‘**default 메소드**’ 구현 가능
>
> Java의 Function package -> 추상 메소드 하나와 default 메소드를 가지고 있는 인터페이스
> 객체화된 함수형 인터페이스에 default 메소드가 존재하여 추가 기능을 제공한다.

---
### 상속
- 상위 클래스의 필드, 메소드들을 물려받을 수 있음
- 확장성, 코드의 재사용성, 다형성 제공
- 클래스는 다중 상속이 불가능
- 인터페이스는 다중 상속이 가능
- 상위 클래스의 필드, 메소드에 접근할 때 **super** 가 사용된다. 특히 생성자 호출 시 사용된다.


---
### 면접 예상 질문

**Q. protected 접근 제어자를 사용할 때 어떤 상황에서 유용할까요?**
- 상속 시, 하위 클래스에서 상위 클래스의 필드, 메소드에 접근가능 하도록 할 때 유용합니다.


**Q. 자바에서 다중 상속을 직접 지원하지 않는 이유는 무엇인가요?**

- **다이아몬드 문제**가 발생 가능합니다.
- 두 부모 클래스가 동일한 메소드를 가지고 있고, 하위 클래스가 두 부모 클래스를 모두 상속 받으면, 어떤 부모의 메소드를 상속받을 지 모호해집니다.

**다이아몬드**
```
       A
     /   \
    B     C
     \   /
       D

```

상위클래스 A를 상속받는 B,C가 있고, B,C를 상속받는 D가 있다고 가정합니다.
D 클래스는 A 클래스의 메소드를 두 번 상속받아 B와 C중 어떤 것을 상속받아야 할 지 곤란해집니다.

java interface는 **super** 키워드를 통해서 어떤 부모 클래스의 메소드를 상속받을 지 선택가능합니다.

```java
interface A {
    default void method() {
	...
    }
}

interface B extends A {
}

interface C extends A {
}

class D implements B, C {

    @Override
    public void method() {
        B.super.method();
    }
}

```