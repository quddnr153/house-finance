# CTO 및 인사팀 면접 (인성면접)

## 목차
- 지원서류 검토
- 지원 직무 검토
- 나의 역량과 지원 직무 연관성
- 카카오페이에 대한 이해
- 왜 카카오페이 여야 하는가?
- 1차 질문 내용


## 지원서류 검토

- 여러 프로젝트에 참여... 혹은 업무 변경이 잦은 이유
  - 1 ~ 2년 차 때 대부분 업무는 유지보수 업무로 특정 도메인 지식이 필요가 적었던 부분의 업무였음
  - 적재적소로 업무를 부여 받음
  - 그 후 신규 프로젝트에 참여
- 가장 최근 React 로 프론트엔드 개발
  - React 를 사용하게 된 계기: 프로젝트 참여 동료들과 논의 후 결정
    - 마크업과 협업이 필요
    - 유지보수가 쉬워야 함
    - 네이버의 트랜드
    - 대부분 동료들이 기존틀 (jsp, thymeleaf) 에서 벗어나 새로운 기술을 습득하여 적용해 보고 싶어 함
  - 쉽운길은 아니였다
    - 기존 업무 대로 했으면 금방 개발했을 요구사항들...
    - React 학습은 1주 ~ 2주 (개발을 하면서 습득)
    - 중점을 둔 비교점들
      - Functional component vs Class component
        - 가장큰 설계 포인트는 리엑트 가이드를 따름 (Functional)
      - State management library 가 필요한가?? React 16.8 부터 추가된 hooks feature 만으로 충분한가??
        - redux mobx 에서 제공하는 기능들이 대부분 필요하지 않음, react features (hooks) 만으로도 해결 가능한 문제들...
      - Architecture
        - 네이버와 서버를 따로 써야하는 제한 사항??? 으로.... 그림을 그려주자..
    - 어떡하면 마크업과 협업을 쉽게할 수 있을까?
      - storybook 을 통해 UI 검수 (test case)
      - 컴포넌트는 프론트 개발에서 먼저 쪼갠 후, 마크업 적용
  - 새로운 프론트 프로젝트에 참여한다면 React 를 사용할 것인가?
    - 지금 단언할 수 없다.. 요구사항에 따라 다른 선택을 할 것임...
    - 단순한 작업 마크업이 많지 않을 것 같다?? -> 서버 렌더링 jsp thymeleaf 와 같이 html 을 통자로 받아도 상관없음
    - 하지만, 프론트가 중요한 비지니스라면 React 와 같은 라이브러리를 사용할 것이다.
- xeye, 지식백과, 모니터링 시스템, 고객 시스템
  - 흠.... 정리할 필요가 있으려나... 들어가기전에 기술 스택 리마인드만 하자..
  - 언어: Java, Javascript, Python, Scala, SQL... + Shell script
  - 프레임워크: Spring, Spark, ORM...
  - 시스템: RDBMS, HDFS, nas, owfs
  - 기타: Junit, Git,


## 지원 직무 검토

1. 지원자격
  - 전산 관련학과 학사이상 또는 동일한 자격, 경렬 무관: 통과
  - Java 또는 Scala 개발언어 활용 능력: 통과 (JVM 공부해가기, Java 버전들...)
  - Play/Spring/Akka 등 프레임워크 활용 능력: 뭐.. 그럭저럭...
  - MySQL 기반의 웹어플리ㅔ이션 모델링/개발/튜닝 능력: 튜닝 까지는 아니지만, 모델링과 쿼리 plan 을 하며 성능향상 가능
  - OOP 또는 Functional Programming 기반의 소프트웨어 디자인/개발 능력: Spring을 사용하는 것은 OOP 에 해당.. FP 는 업무특성상 경험해 보지 못함
  - 다양한 분야의 사람과의 협업 능력: ...? 동료끼리 뒷담화 소통 가능
  - 논리적이고 체계적인 문제해결 능력 및 커뮤니케이션 능력: 뭐... 혼난적은 없는듯....??

2. 담당업무
  - 카카오 머니/송금 시스템
  - 카카오페이 결제 시스템
  - 카카오페이 서비스 플랫폼
  - 청구서 서비스
  -> 플랫폼이 가장 장애를 덜 맞을 것 같은 느낌이든다.... 저기로 어떻게 어필을 해볼까

3. 우대사항
  - MSA 기반에서 서비스 개발 경험: 강연... 문서 들로 주워들은 정도...
  - Secure Coding 및 암호화 관련 개발 경험: ...?? 은연중 하지 않았을까??? 하지만 설명은 불가능 간접적으로만 접했다고 말하자
  - 블록체인???? 꺼져
  - 영어 커뮤니케이션 능력: 가능쓰~~ 와썹맨
  - 글로벌 페이먼트에 대한 열망???? 연봉많이주면 내 열망 불태워주지
  - JVM Optimization / Tunning 능력: 이거 외우는 사람이 있을까?? 가장 경험에 의한 능력이라 생각:,,, 그떄 그때 문서 봐가며 작업 가능
  - 대용량 트래픽을 처리하는 시스템 개발/운영 경험...... 이게 NTS 에서 몇이나 될까?
  - 오프라인 결제 기술 경험.... 도메인지식이네....

## 나의 역량과 지원 직무 연관성

결제... 및 포인트 등 도메인과 상관없는 프랫폼 개발이 가작 적합하다고 생각

## 카카오페이에 대한 이해

- 오프라인 진출
- 카카오 프레임워크를 이용하여 친구들에게 간편 송금들
- 카카오페이 앱
- 금융상품들...
- 접근성 카카오사용자들이많으니...

## 왜 카카오페이 여야 하는가?

- 네이버페이 와 비교했을때, 사용자수는 아주 조금 적지만 개인적으로 훨씬 간편하고 접근성이 쉽다 생각
  - 카카오 라는 접근성
  - 친구들의 가입여부가 판단하기 쉬움 (메신저)
- ... 뭐가 더 있을까...

## 1차 질문 내용

- 자기소개해봐라 (경력위주)
- 개발을 할 때, 테스트 코드는 작성하는가?
- 경험했던 프로젝트들의 테스트 커버리지는?
- 테스트 코드 먼저 작성하는 것이 익숙 한가?
- 비지니스 로직이 변경 되었을 때, 어떤 방법론으로 테스트 코드를 리펙토링 하였는가?
- 예전 레거시의 Java 버전을 올린다든가, 프레임웤 버전을 올린다든 가 할 때, 어떤식으로 작업할 것인가?
- 프론트엔드 개발에서는 테스트 코드를 작성하였는가?
- 코드리뷰는 어떻게 진행했는가?
-> 위내용들 꼬리 물기.... 질문들.....

- 사전과제 코드리뷰 진행
  - exception 설계는 어떻게 하나?? checked excpetion unchecked exception 의 견해를 물음 -> 라이브러리 개발자 입장에선 무엇을 사용하는게 사용자에게 좋을까??
  - controller 에서 response 할 때, 객체들이 JSON 으로 변환되는 과정??? message converter 를 통해 변환된다 설명함...
  - 예측 알고리즘으로 linear regression 을 선택한 이유?? -> 당연하지 새꺄!!!!! 선형데이터에 가장 쉽게 적용할 수 있는거니까!!!!!
  - ResponseEntity 는 generic 인데 type 을 명시하지 않은 이유???
  - 