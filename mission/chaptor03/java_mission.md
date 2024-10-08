# ✚ 추가미션

## **1. 제네릭과 와일드카드**

### 제네릭이 필요한 이유

중복코드를 제거하기 위해서, 다형성을 통해 중복 해결을 시도할 수 있다.
```java
 public class ObjectBox {
     private Object value;
     public void set(Object object) {
         this.value = object;
	} 
     public Object get() {
         return value;
	} 
} 
```

- 위와 같은 코드는 **타입 안정성**이 매우 떨어지게 된다.
- 실제 데이터를 ObjectBox에 담고 이를 가져오면 리턴타입은 Object 여서 이를 다운캐스팅을 통해서 사용해야 한다.
- 매개변수도 Object 이기 때문에 컴파일 타임에 어떤 값이 들어오더라도 컴파일 에러가 발생하지 않는다.


### 제네릭 적용

```java
 public class GenericBox<T> {
     private T value;
     public void set(T value) {
         this.value = value;
	}	 
     public T get() {
         return value;
	} 
} 
```

- T라는 타입 매개 변수를 지정해서 객체를 생성하게 되면, 컴파일 타임에 타입 안정성 보장이 가능하다.
  우리는 제네릭을 통해서 **코드 재사용과 타입 안정성** 두 마리 토끼를 잡을 수 있다.
- **제네릭을 통해서 사용할 타입에 대한 결정을 나중으로 미루는 것이다.**


**제네릭 타입을 지정하지 않을 경우**
```java
GenericBox integerBox = new GenericBox(); // 비추천

GenericBox<Object> integerBox = new GenericBox<>(); // 동일한 코드
```
- 제네릭 타입을 지정하지 않고 사용가능하긴 하지만, 추천하지 않는다.



### 타입 추론

- 제네릭 타입
```java
GenericBox<Integer> integerBox = new GenericBox<Integer>() // 타입 직접 입력 GenericBox<Integer> integerBox2 = new GenericBox<>() // 타입 추론 

```
- 제네릭 메서드
```java
Integer i = 10;

Integer result = GenericMethod.<Integer>genericMethod(i); // 타입 직접 입력
Integer result2 = GenericMethod.genericMethod(i); // 타입 추론

```


### 타입 매개변수 상한 제한
```java
public class AnimalHospitalV2<T> {

     private T animal;

     public void set(T animal) {
         this.animal = animal;
	}
 
	public void checkup() { 		// T의 타입을 메서드를 정의하는 시점에는 알 수 없다. Object의 기능만 사용 가능	  		System.out.println("동물 이름: " + animal.getName()); // 컴파일 오류
		animal.sound(); // 컴파일 오류
	}
 
	public T getBigger(T target) { 
		// 컴파일 오류 
		return animal.getSize() > target.getSize() ? animal : target;
    }
} 

```

위와 같은 제네릭 클래스를 만들면, `Animal` 이라는 클래스의 `getName`, `sound`, `getSize` 와 같은 기능들은 사용하지 못한다. 실제 객체가 생성될 때, 어떤 타입 인자가 들어올지 모르기 때문이다.


따라서 우리는 `extends` 를 통해 **타입 매개변수의 상한선을 지정**한다.
```java
 public class AnimalHospitalV3<T extends Animal> {

...
```

이를 통해, 타입 인자로 들어오는 객체는 Animal의 기능을 가지고 있다는 보장이 생기기 때문에 Animal이 제공하는 기능들을 사용 가능하다.


### 제네릭 메서드
```java
     public static <T> T genericMethod(T t) {
         System.out.println("generic print: " + t);
         return t;
	}
 
     public static <T extends Number> T numberMethod(T t) {
         System.out.println("bound print: " + t);
         return t;
	} 

```

제네릭 타입과 제네릭 메서드는 별개의 기능이다. 하지만 함께 사용은 가능하다.

**제네릭 메서드의 특징**
- 제네릭 메서드는 메서드 내에서 제네릭을 도입할 때 사용한다.
- 리턴 타입 왼쪽에 제네릭 타입을 명시한다.
- static 메소드를 제네릭 메소드로 만들 때, (클래스)제네릭 타입과 호환 불가능하다.
    - 왜냐하면, 실제 제네릭 클래스가 생성되기 전까지는 (클래스)제네릭 타입에 어떤 타입이 들어올 지 모르기 때문이다.
- 인스턴스 메서드는 (클래스)제네릭 타입과 호환 가능하다. 우선순위는 인스턴스 메서드가 먼저 가져간다. 그러나 혼동을 줄이기 위해 중복해서 사용하지 않도록 하자.
- 제네릭 메서드도 동일하게 `extends` 로 상한을 지정가능하다.
    - 상한을 지정하면 지정한 클래스의 기능을 사용가능하다.



### 와일드 카드

- 제네릭 타입을 조금 더 편리하게 사용할 수 있도록 제공하는 기능이다.
- `?` 을 통해서 정의한다.
- 동일하게 상한선 지정이 가능하다.
- 와일드카드는 하한선 지정도 가능하다.

**제네릭 메서드 vs 와일드 카드**
```java
    static <T> void printGenericV1(Box<T> box) {
        System.out.println("T = " + box.get());
	} 

    static void printWildcardV1(Box<?> box) {
        System.out.println("? = " + box.get());
	} 

	// 상한선 지정
   	static <T extends Animal> T printAndReturnGeneric(Box<T> box) {
        T t = box.get();
		System.out.println("이름 = " + t.getName()); 
		return t; 
	} 

	// 상한선 지정
    static Animal printAndReturnWildcard(Box<? extends Animal> box) {
        Animal animal = box.get();
		System.out.println("이름 = " + animal.getName()); 
        return animal;
    }
} 

```

**와일드 카드는 제네릭 타입이나, 제네릭 메서드를 선언하는 것이 아니다. 와일드 카드는 이미 만들어진 제네릭 타입을 활용할 때 사용한다.**

그렇다면 와일드 카드, 제네릭 메소드 중 무엇을 써야할까? 보통 단순한 와일드 카드를 사용하되, 제네릭 메서드가 필요한 경우가 있다. 이럴 땐 제네릭 메서드를 써야한다.

**타입 매개변수가 꼭 필요한 경우**
```java
 static <T extends Animal> T printAndReturnGeneric(Box<T> box) {
     T t = box.get();
	System.out.println("이름 = " + t.getName()); 
	return t; 
} 

static Animal printAndReturnWildcard(Box<? extends Animal> box) { 
	Animal animal = box.get(); 	System.out.println("이름 = " + animal.getName()); 	return animal; 
} 

...

Dog dog = WildcardEx.printAndReturnGeneric(dogBox) 

Animal animal = WildcardEx.printAndReturnWildcard(dogBox) 

```

와일드 카드는 리턴타입을 명확하게 지정할 수 없는 반면, 제네릭 메서드는 명확한 타입을 리턴할 수 있다.


## 하한 와일드 카드
```java
 public class WildcardMain2 {
     public static void main(String[] args) {
         Box<Object> objBox = new Box<>();
         Box<Animal> animalBox = new Box<>();
         Box<Dog> dogBox = new Box<>();
         Box<Cat> catBox = new Box<>();

		// Animal 포함 상위 타입 전달 가능 
		 writeBox(objBox); 
		 writeBox(animalBox); 
		 writeBox(dogBox); // 컴파일 에러, 하한이 Animal 
		 writeBox(catBox); // 컴파일 에러, 하한이 Animal 

         Animal animal = animalBox.get();
         System.out.println("animal = " + animal);
     }

	static void writeBox(Box<? super Animal> box) {
		  box.set(new Dog("멍멍이", 100)); 
	} 
} 

```

- 하한을 Animal로 제한했기 때문에, Animal의 하위 타입인 Dog, Cat은 전달할 수 없다.


### 타입 이레이저

제네릭은 자바 컴파일 단계에서만 사용되고, 컴파일 이후에는 제네릭 정보가 삭제된다. 컴파일 전인 `.java`
에는 제네릭의 타입 매개변수가 존재하지만, 컴파일 이후인 자바 바이트코드 `.class`에는 타입 매개변수가 존재하지 않는 것이다.

**컴파일 전**
```java
 public class GenericBox<T> {

     private T value;

     public void set(T value) {
         this.value = value;
	}
 
     public T get() {
         return value;
	} 
} 

```

**컴파일 이후**
```java
 public class GenericBox {

     private Object value;

     public void set(Object value) {
         this.value = value;
	} 

     public Object get() {
         return value;
} 

```
- 컴파일 이후에는 T가 Object 타입으로 대체된다.
- 상한 제한이 있을 경우, T가 명시한 상한으로 대체된다.


```java
Integer result = (Integer) box.get(); //컴파일러가 캐스팅 추가 
```

- 그리고 실제로 이를 생성한 클래스에서는 지정한 타입으로 캐스팅한 코드를 추가해준다.
- 자바 컴파일러는 컴파일 시점에 제네릭을 사용한 코드에 문제가 없는지 검증을 하기 때문에 문제가 발생하지 않는다.
- 런타임 시에는 제네릭 정보가 지워지는 것을 타입 이레이저라고 한다.

### 타입 이레이저 방식의 한계
자바는 자바 아래버전과 호환성을 위해서 제네릭을 타입 이레이저 방식으로 지원한다. 이에 따라서 발생하는 한계점이 존재한다.

```java
 class EraserBox<T> {

	public boolean instanceCheck(Object param) { 
		return param instanceof T; // 오류 
	}
 
	public void create() { 
		return new T(); // 오류 
	} 
} 

```

- `instance of T` 는 결국 런타임에는 `instance of Object`로 바뀌어버리기 때문에 모든 값이 true로 반환되어 버린다. 따라서 instance of 를 허용하지 않는다.
- `new T()` 는 결국 런타임에는 `new Object()` 로 바뀌어버리기 때문에 개발자가 의도한 바와 달라서 `new`를 허용하지 않는다.


----
## **2. 래퍼 클래스**

### 기본형의 한계
자바는 객체 지향 언어이다. 자바에는 객체가 아닌 기본형이 존재하는데, 기본형에는 한계가 존재한다.
- **객체가 아님**
    - 메서드를 제공하지 못한다.
    - 객체 참조가 필요한 컬렉션 프레임워크, 제네릭을 사용하지 못한다.
- **null 값을 가질 수 없음**
    - 때로는 데이터가 없는 상태를 표현하고 싶지만 이를 표현하지 못한다.

### 래퍼 클래스의 특징
- 기본형의 객체 버전
- 불변
- equals로 비교해야함

int, Integer를 예시로 들어 설명한다.

**래퍼 클래스 생성 - 박싱(Boxing)**
```java
Integer i = Integer.valueof(10);
```
- 최적화: 개발자들이 일반적으로 자주 사용되는 -128 ~ 127 범위의 Integer 클래스를 미리 생성해두고, 해당 범위의 값을 조회하면 미리 생성된 Integer 객체를 반환한다.
    - 마치 문자열 풀과 비슷하게 자주 사용되는 숫자를 미리 생성하고 재사용한다.

**언박싱(UnBoxing)**
```java
Integer i = Integer.valueof(10);
int unBoxedInt = i.intValue();
```


**비교는 equals() 사용**
- 래퍼 클래스는 객체이기 때문에 == 비교를 하면 인스턴스의 참조값을 비교
- 래퍼 클래스는 객체 내부의 값을 비교하도록 equals()를 재정의해두었다. 값 비교를 위해서는 equals()를 사용해야 한다.


### 오토 박싱, 언박싱
- 자바 1.5부터 오토 박싱, 오토 언박싱을 지원한다.
```java
int value = 7;
Integer boxedValue = value // 오토 박싱

int unboxedValue = boxedValue // 오토 언박싱
```
- 컴파일러가 valueOf, xxxValue() 등의 코드를 추가해주는 기능이다.


### 래퍼 클래스의 주요 메서드

- `valueOf()` : 래퍼 타입을 반환한다. 숫자, 문자열을 모두 지원한다.
- `parseInt()` : 문자열을 기본형으로 변환한다.
- `compareTo()` : 값 비교를 지원한다. 내 값이 크면 1, 같으면 0, 내 값이 작으면 -1
- `sum(), min(), max()` : 간단한 덧셈, 작은 값, 큰 값을 비교하는 static 메소드를 지원


### 래퍼 클래스와 성능
래퍼 클래스가 기본형보다 좋은 것 같은데, 굳이 기본형이 존재하는 이유는?
성능차이를 비교해보자.

```java
// 기본형
int iterations = 1_000_000_000;
long sumPrimitive = 0;
for(int i = 0; i < iterations; i++) {
	sumPrimitive += i;
}

// 래퍼 클래스
Long sumWrapper = 0L;
for(int i = 0; i < iterations; i++) {
	sumPrimitive += i; // 오토 박싱 박생
}

```

- 이 둘의 성능차이를 비교해보면, 시스템마다 다르겠지만 기본형 계산이 5배 정도 빠르다.
- 래퍼 클래스는 객체이기 때문에 객체 메타데이터를 포함한다. 따라서 메모리를 추가로 사용한다.

사실 그렇게 유의미한 차이는 아니지만, CPU 연산을 아주 많이 수행하는 특수한 상황에는 기본형을 고려해보자. 일반적인 경우는 유지보수하기 더 나은 것을 선택하자.


---
## **3. Optional**

> **공식 API 문서 : Optional을 만든 의도**
>
> 메소드가 반환할 결과 값이 '없음'을 명백하게 표현할 필요가 있고, **null을 반환하면 에러가 발생할 가능성이 높은 상황에서 메소드의 반환 타입으로 Optional을 사용하자**는 것이 Optional을 만든 주된 목적이다.


### Optional 올바르게 사용하기

- `Optional`에 절대 `null`을 할당하지 않는다. 비어있다면 `Optional.empty()`를 사용
- `Optional.get()` 호출전에 반드시 객체가 값이 있는지 확인할 것
- 값이 없는 경우, `orElse()`를 통해 디폴트 값을 설정할 수 있다.
    - 주의할 점으로는 `orElse()`의 괄호 안에서 새로운 값을 생성하지 말자. 왜냐하면 이 코드는 항상 실행되기 때문에 값이 있는 상황에도 의미없는 객체가 계속 생성될 수 있다.
- `orElseGet(Supplier)` 는 `Optional`의 값이 없을 때만 실행된다. 따라서 새로운 값을 생성해도 된다.
- 값이 없는 경우, `orElseThrow()`를 통해서 예외를 던질 수 있따.
- `ifPresent()` : 값이 있는 경우에 이를 사용하고, 없는 경우에 아무 동작도 수행하지 않을 경우
- `Optional`을 필드 타입으로 사용하지 말자.
    - **`Optional`은 반환 타입을 위해 설계된 타입**이다.
    - 클래스의 필드, 혹은 메소드의 인자로 사용하는 것은 `Optional` 도입 의도에 반하는 패턴
- 단지 값을 얻을 목적이라면 `Optional` 대신 `null` 비교를 하자.
    - `Optional`은 비싼 연산이기 때문에 과도하게 사용하지 말아야 한다. 단순값 혹은 `null`을 얻을 목적이라면 `Optinoal` 대신 `null` 비교로도 충분하다.
- `Optinoal`을 빈 컬렉션이나 배열을 반환하는데 사용하지 말 것
    - 값이 없는 경우는 비어있는 컬렉션과 배열을 반환하면 된다.
- `Optional`을 컬렉션의 원소로 사용하지 말 것
    - 특히 `Map`과 같은 경우 `getOrDefault()` 와 같은 메소드로 `null` 방지를 충분히 할 수 있다.
- `Optional.of()`는 null이 아닌게 확실할 때, `Optinal.ofNullable()`은 `null`일 가능성이 있을 때 사용
- 내부 값 비교 시, `Optional.equals` 사용 고려
    - 내부 값 비교 가능하도록 이미 구현이 되어있다. 따라서 굳이 꺼내서 비교할 필요 없음
- 값에 대해 미리 정의된 규칙이 있는 경우 `filter` 사용 고려
    - `stream` 처럼 필터를 제공하여 내부에 사용자가 구현한 함수를 사용가능하다.

출처:
1. ****김영한의 실전 자바 중급 1편 - 자바의 다양한 중급 기능****
2. https://dev-coco.tistory.com/178

