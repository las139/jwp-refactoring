# 키친포스

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |

## 요구사항

### 메뉴그룹 (Menu Group)
- `메뉴그룹`을 등록할 수 있다
- `메뉴그룹` 목록을 조회할 수 있다

### 메뉴 (Menu)
- `메뉴`를 등록할 수 있다
    - `메뉴`의 가격은 필수이고 0원 이상이어야한다
    - `메뉴`는 `메뉴그룹`이 지정되어 있어야한다
    - `메뉴`에 등록된 `메뉴그룹`은 등록된 `메뉴그룹`이어야한다
    - `메뉴`에 포함된 `상품`은 등록된 `상품`이어야한다
    - `메뉴`의 가격은 포함된 `상품`들의 총 금액보다 클 수 없다
- `메뉴` 목록을 조회할 수 있다

### 주문 (Order)
- `주문`을 등록할 수 있다
    - `메뉴`없이 `주문`할 수 없다
    - `메뉴`는 등록된 `메뉴`여야한다
    - `주문 테이블`없이 `주문`할 수 없다
    - `주문` 등록시 `주문` 상태는 `조리`상태이다
- `주문` 목록을 조회할 수 있다
- `주문` 상태를 변경할 수 있다
    - 계산이 완료되면 `주문` 상태를 변경할 수 없다

### 상품 (Product)
- `상품`을 등록할 수 있다
- `상품`의 가격은 필수이고 0원 이상이어야한다
- `상품` 목록을 조회할 수 있다

### 단체지정 (Table Group)
- `단체지정`을 등록할 수 있다
    - `주문 테이블`이 두 `테이블` 이상이어야 `단체지정`을 할 수 있다
    - `주문 테이블`은 미리 등록되어 있어야한다
- `단체지정`을 해제할 수 있다
    - 식사중이거나 조리중이거나 식사중인 `주문 테이블`이 있다면 해제할 수 없다

### 테이블 (Table)
- `테이블`을 등록할 수 있다
- `테이블` 목록을 조회할 수 있다
- `테이블`을 빈 `테이블`로 변경할 수 있다
    - 등록된 `테이블`만 빈 `테이블`로 변경할 수 있다
    - `단체 지정`이 된 `테이블`은 빈 `테이블`로 변경할 수 없다
    - 조리중이거나 식사중인 `테이블`은 빈 `테이블`로 변경할 수 없다
- `테이블`의 방문한 손님 수를 변경할 수 있다
    - `테이블`의 손님 수는 0명 이상이어야 한다
    - 등록된 `테이블`만 방문 손님 수를 지정할 수 있다
    

## 🚀 1단계 - 테스트를 통한 코드 보호
## 요구사항
- [x] kitchenpos 패키지의 코드를 보고 키친포스의 요구 사항을 README.md에 작성한다.
    - [마크다운(Markdown) - Dooray! 참고](https://dooray.com/htmls/guides/markdown_ko_KR.html)
- [x] 정리한 키친포스의 요구 사항을 토대로 테스트 코드를 작성한다. 모든 Business Object에 대한 테스트 코드를 작성한다.

## 🚀 2단계 - 서비스 리팩터링
## 요구사항
- 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드에 대해 단위 테스트를 구현한다.
- Spring Data JPA 사용 시 spring.jpa.hibernate.ddl-auto=validate 옵션을 필수로 준다.

## 구현목록
- [ ] JPA 설정 정보 추가
- [ ] JDBC -> JPA로 변경
- [ ] domain 정리
    - [ ] 생성자 정적팩토리 메서드
    - [ ] setter 제거
- [ ] 일급콜렉션 추가
- [ ] 서비스 로직을 도메인 로직으로 이동
- [ ] Domain 단위 테스트 추가
- [ ] 메서드 이름 의미있게 짓기 (setter 메서드 네이밍 변경)
