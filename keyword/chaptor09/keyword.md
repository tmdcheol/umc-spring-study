# 

## Spring Data JPA의 Paging

Spring Data JPA는 데이터 조회 결과를 효율적으로 관리하기 위해 **Paging**(페이징) 기능을 제공합니다. 페이징은 대량의 데이터를 페이지 단위로 나누어 클라이언트에 반환하는 기술로, **Page**와 **Slice**라는 두 가지 주요 개념이 있습니다.

### Page

Page는 페이징의 전체 정보를 포함하는 데이터 구조입니다. 페이징 처리 시 요청된 페이지의 데이터뿐만 아니라, **전체 페이지 수**, **전체 데이터 수** 등의 메타데이터를 포함합니다.

**특징**
* 요청한 페이지의 데이터 목록과 함께 페이징 정보를 포함.
* Page 객체는 데이터 외에 추가적인 페이징 정보도 함께 반환합니다.
* 데이터 총 개수를 알아야 하므로 COUNT **쿼리를 추가 실행**합니다.

**Page 인터페이스 주요 메서드**
* `List<T> getContent()`: 현재 페이지에 포함된 데이터.
* `int getNumber()`: 현재 페이지 번호 (0-based).
* `int getSize()`: 요청된 페이지 크기.
* `int getTotalPages()`: 전체 페이지 수.
* `long getTotalElements()`: 전체 데이터 개수.
* `boolean isFirst()`: 첫 번째 페이지 여부.
* `boolean isLast()`: 마지막 페이지 여부.
* `boolean hasNext()`: 다음 페이지가 있는지 여부.
* `boolean hasPrevious()`: 이전 페이지가 있는지 여부.

**사용예제**
```java
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

Pageable pageable = PageRequest.of(0, 10); // 첫 번째 페이지(0), 페이지 크기 10
Page<Member> members = memberRepository.findAll(pageable);

System.out.println("Total Elements: " + members.getTotalElements()); // 전체 데이터 개수
System.out.println("Total Pages: " + members.getTotalPages());       // 전체 페이지 수
System.out.println("Current Page: " + members.getNumber());          // 현재 페이지 번호
System.out.println("Page Size: " + members.getSize());               // 페이지 크기

List<Member> content = members.getContent(); // 현재 페이지 데이터

```
⠀
해당 데이터들을 클라이언트에게 전달해주면 됩니다. 필요한 데이터만 뽑아서 주는 것이 깔끔할 것이라고 생각은 합니다. 핵심은 Spring Data JPA가 위의 기능들을 제공해준다는 것입니다.


### Slice

Slice는 Page와 유사하지만, **전체 페이지 수나 데이터 총 개수를 계산하지 않는** 데이터 구조입니다. 필요한 데이터만 조회하여 클라이언트에 반환하므로, 성능이 중요한 경우 유리합니다.

**특징**
* 현재 페이지와 다음 페이지의 존재 여부 정보만 제공.
* COUNT **쿼리를 실행하지 않아** 성능이 더 우수.
* 전체 데이터 수나 전체 페이지 수는 알 수 없음.
* **무한 스크롤**이나 **다음 페이지 요청**이 필요한 상황에서 사용.

**Slice 인터페이스 주요 메서드**
* List<T> getContent(): 현재 페이지에 포함된 데이터.
* int getNumber(): 현재 페이지 번호 (0-based).
* int getSize(): 요청된 페이지 크기.
* boolean isFirst(): 첫 번째 페이지 여부.
* boolean hasNext(): 다음 페이지가 있는지 여부.

```java
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

Pageable pageable = PageRequest.of(0, 10); // 첫 번째 페이지(0), 페이지 크기 10
Slice<Member> members = memberRepository.findAllByStatus("ACTIVE", pageable);

System.out.println("Current Page: " + members.getNumber());   // 현재 페이지 번호
System.out.println("Page Size: " + members.getSize());        // 페이지 크기
System.out.println("Has Next: " + members.hasNext());         // 다음 페이지 여부

List<Member> content = members.getContent(); // 현재 페이지 데이터

```

| **특징**            | **Page**                          | **Slice**                          |
|----------------------|------------------------------------|-------------------------------------|
| **전체 데이터 수**    | 제공 (`getTotalElements()`)        | 제공하지 않음                       |
| **전체 페이지 수**    | 제공 (`getTotalPages()`)           | 제공하지 않음                       |
| **성능**             | 상대적으로 느림 (`COUNT` 필요)      | 상대적으로 빠름 (`COUNT` 필요 없음) |
| **사용 상황**         | 일반적인 페이징                   | 무한 스크롤, 간단한 페이지 네비게이션 |


**Page vs Slice**
* Page는 **전체 데이터 정보**를 제공하며 일반적인 페이징에 적합.
* Slice는 **성능 최적화**와 무한 스크롤에 적합.


---
## 객체 그래프 탐색

**객체 그래프 탐색**은 JPA에서 엔티티 간의 연관 관계를 탐색하며 데이터를 가져오는 작업을 의미합니다. JPA에서는 @OneToOne, @OneToMany, @ManyToOne, @ManyToMany 등의 연관 관계 매핑을 통해 객체 간 관계를 설정합니다.

### 객체 그래프 탐색 전략

**즉시 로딩 (Eager Loading)**
- 연관된 엔티티를 **즉시 로드**.
- 연관된 엔티티를 **JOIN 쿼리**로 한 번에 가져옴.
* 사용: @OneToOne, @ManyToOne 기본값.

```java
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "member_id")
private Member member;

```

**장점**
* 연관 데이터를 미리 가져와서 지연 로딩 문제 해결

**단점**
* 불필요한 데이터를 미리 가져오면 성능 저하 가능
* 쿼리가 예측하기 힘들어짐


**지연 로딩 (Lazy Loading)**
* 연관된 엔티티를 **실제 사용하는 시점**에 로드.
* 기본값: @OneToMany, @ManyToMany.

```java
@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
private List<Order> orders;

```

**장점**
* 필요한 데이터를 사용할 때만 가져옴.
* 메모리 및 성능 효율성.

⠀**단점**
* 실제 데이터 접근 시 추가 쿼리 발생.

**N+1 문제**
* 즉시 로딩 시 **연관된 데이터의 개수만큼 추가적인 SELECT 쿼리가 발생**.

예
```sql
SELECT * FROM member;
SELECT * FROM orders WHERE member_id = 1;
SELECT * FROM orders WHERE member_id = 2;

```


해결 방법

**Fetch Join**
```java
@Query("SELECT m FROM Member m JOIN FETCH m.orders")
List<Member> findAllWithOrders();
```

**EntityGraph**
```java
@EntityGraph(attributePaths = {"orders"})
List<Member> findAllWithOrders();
```


