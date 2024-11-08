### Domain
도메인의 정의는 보통 비즈니스 로직과 관련된 객체를 의미합니다.
Java는 객체지향 언어로 객체지향적으로 작성된 프로그램의 객체를 보통 Domain이라고 칭합니다.

Spring Boot + RDBMS 로 개발을 진행할 때 문제가 발생할 수 있습니다.
보통 편리한 Mapping을 위해서 Table 형식에 맞게 Java 객체를 만들어서 조회를 하게 됩니다.
Table 형태와 객체지향의 Domain 형태는 목적이 다르기 때문에 각각의 형태가 불일치할 수 밖에 없습니다.

이를 해결하기 위해서 만들어진 기술이 바로 ORM 입니다. 대표적으로 Spring에는 JPA 기술을 사용하는데,
ORM을 사용함으로써 Domain 객체를 RDBMS의 table에 잘 매핑하면 이 둘의 부조화를 해결할 수 있습니다.

### 양방향 매핑

Spring ORM 기술인 JPA를 사용할 때 서로 참조할 수 있는 mapping 기술입니다.

예를 들어, `Member`와 `Team`이라는 Table이 존재한다고 가정합니다. RDBMS의 관점으로 바라보면, `Member` : `Team` 은 **N:1** 의 관계입니다. Member table에 Team의 foregin key가 존재한다는 것입니다.

이 때, 단방향 매핑만 사용한다면 `Member` 객체에 `Team`이라는 객체만 생성되고, `Team`이라는 객체에는 `Member`와 관련된 정보가 존재하지 않습니다.

만약 `Team`에도 `List<Member>` 와 같은 자료형을 통해 해당 팀에 존재하는 Member들을 가지고 있는 형태를 원할 수도 있습니다. 이 때 사용하는 것이 바로 양방향 매핑입니다.

양방향 매핑은 주의할 점이 존재하는데, 무한루프가 발생할 수 있습니다. `toString()`, `equals()`, `hashCode()` 메서드를 사용할 때 주의해야 합니다. 조회할 때 또한 무한 루프가 발생할 수 있으니 주의하여 사용해야 합니다.

양방향 매핑은 `mappedBy` 를 통해 연관관계의 주인을 정할 수 있습니다. 위의 예시를 다시 가져온다면 Team에 `List<Member>`가 존재할 때, `mappedBy = team` 을 `@OneToMany` 의 어노테이션에 적어준다면 어떤 일이 일어날까요?

위의 의미는 `Member` 객체에 있는 `Team team`에 따라서  `List<Member>` 가 거울처럼 사용된다는 것으로 Member가 연관관계의 주인이 되어 Member의 Team team을 수정해야만 연관관계가 수정될 수 있다는 것입니다. `Team` 객체의 `List<Member>`를 수정하더라도 연관관계는 변동되지 않습니다.

cascade 옵션도 알아야 합니다. 만약 ALL로 설정이 되어있다면 위의 예시에서 Team을 삭제하면 Team에 소속된 Member들이 삭제되는 현상이 발생합니다. 따라서 매우 주의해서 사용해야하는 옵션입니다.


### N + 1 문제

하나의 쿼리로 인해서 N개의 쿼리가 수행되어 성능저하가 발생하는 것을 N+1 문제라고 이야기 합니다.
JPA를 사용하면 N + 1 문제가 발생할 수 있습니다.

예를 들어 Member와 Team의 예시로 다시 가보면, 만약 모든 Member들을 조회하는 쿼리를 수행했다고 합니다. 그리고 이때 Lazy 로딩 방식으로 설정해놓은 상황입니다.

Lazy 로딩으로 하였을 때는, Member의 Team 객체는 프록시로 들어가 있습니다. 만약 가져온 모든 Member list를 for문을 돌면서 Team의 필드에 접근하게 된다면 이 때 지연로딩이 수행되면서 JPA는 DB에 접근해 Team을 조회해서 가져오게 됩니다. Member가 10000명이라면 10000번의 쿼리가 수행되게 되는 것입니다. 물론 JPA가 영속성 컨텍스트가 존재하기 때문에 영속성 컨텍스트에 존재하는 Team이라면 DB에 접근하지 않아 실제로 10000번을 수행하진 않겠지만, 이는 N+1 문제라고 이야기 할 수 있습니다.

이 때 해결할 수 있는 대표적인 방법으로는 Fetch Join과 @EntityGraph가 있습니다. 바로 Join을 수행해서 미리 Member에 관련된 Team 객체들을 가져오는 것입니다. 이렇게 되면 Member의 Team 객체 내에는 이미 데이터가 존재하기 때문에 쿼리를 추가로 수행하지 않습니다.

추가적으로 Lazy 방식을 사용하게 되면 무조건 프록시 방식을 사용하는 것이 아닙니다.
Member의 Team 객체가 @ManyToOne으로 되어있고, Lazy 방식으로 설정되어있어도,

```java
@Query("SELECT m FROM Member m JOIN FETCH m.team")
List<Member> findAllMembersWithTeams();

```
만약 아래처럼 JPQL fetch join을 사용해서 가져온다면 Member 내부의 Team 객체는 프록시 객체가 아닌 실제 객체가 들어갑니다.