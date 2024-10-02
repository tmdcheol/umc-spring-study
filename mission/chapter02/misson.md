## 1

```sql
SELECT 
    r.name AS restaurant_name,
    m.content AS mission_content,
    mm.status AS mission_status,
    m.point
FROM 
    member_mission mm
JOIN 
    mission m ON mm.id2 = m.id
JOIN 
    restaurant r ON m.id2 = r.id
WHERE 
    mm.member_id = (사용자의 id)
ORDER BY 
    mm.updated_at DESC
LIMIT ? OFFSET ?;
```

## 2 
```sql
INSERT INTO review (
    restaurant_id, 
    member_id, // erd에 누락되었지만, 있어야 할 것 같음
    score, 
    content, 
    created_at, 
    updated_at
) VALUES (
    (가게의 ID),
    (사용자의 ID),
    (리뷰 점수),
    (리뷰 내용),
    NOW(),
    NOW()
);

```
## 3
```sql
// 선택한 지역 내에서 성공한 미션 개수

SELECT 
    COUNT(*)
FROM 
    member_mission mm
JOIN 
    mission m ON mm.id2 = m.id
JOIN 
    restaurant r ON m.id2 = r.id
WHERE 
    r.address = (지역의 이름)
    AND mm.member_id = (회원의 id)
    AND mm.status = '완료';
    

// (현재 선택 된 지역에서 도전이 가능한 미션 목록, 페이징 포함)

SELECT 
    r.name AS restaurant_name,
    r.type AS restaurant_type,
    m.content AS mission_content
FROM 
    restaurant r
JOIN 
    mission m ON r.id = m.id2
LEFT JOIN 
    member_mission mm ON m.id = mm.id2 AND mm.status != '완료'
WHERE 
    r.address = (지역의 이름)
ORDER BY 
    m.created_at DESC
LIMIT ? OFFSET ?;

```

## 4
```sql
SELECT 
    m.name,
    m.email,
    m.phone_number,
    m.point
FROM 
    member m
WHERE 
    m.id = (memberId);
```