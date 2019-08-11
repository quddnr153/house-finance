## Environment

- Spring Boot: 2.1.7.RELEASE
- Java: 11 (openjdk 11.0.2)
- Build Tool: Maven 3+
- 빌드 및 실행 방법
  - `mvn clean install`
  - `mvn test`
  - `java -jar target/house-finance-0.0.1-SNAPSHOT.jar`
  - server.port: 3000
- **csv 파일 인코딩 문제**:
  - `ISO_8859_1` 으로 인코딩 되어 있는 파일이라 한글이 깨짐
  - `UTF-8` 로 다시 인코딩한 파일을 사용 -> `resources/csv/ex3-utf-8.csv` 참고
- JWT 구현으로 모든 api header 에 token 정보가 필요합니다.
  - token: string - required
  
---
### API 사용 flow
1. `/users/sign-up` 으로 사용자 추가
2. `/users/sign-in` 으로 로그인
3. 2 번에서 발급 받은 `token` 을 api header 에 추가 하여 api 요청

## API Spec

### 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API

- 문제해결 전략:
  - [apache commons-csv](https://commons.apache.org/proper/commons-csv/) 를 사용하여 csv 파일 파싱
  - `Bank` 와 `CreditGuarantee` 모델링
- METHOD: `POST`
- URL: `/api/house-finances/csv-upload`
- REQUEST
  - Form Data <`multipart/form-data`>

```text
csvFile: <file> (required)
```

- RESPONSE
  - 201: Created
    - ex:
    ```json
      {"code":  201}
    ```
  - 400: Bad Request
  - 500: Internal Server Error

### 주택금융 공급 금융기관(은행) 목록을 출력하는 API

- 문제해결 전략:
  - `Bank` entity 조회
- METHOD: `GET`
- URL: `/api/banks`

- RESPONSE
  - 200: OK
    - ex:
    ```json
    {
        "code": 200,
        "banks": [
            {
                "seq": 1,
                "name": "주택도시기금1)"
            },
            {
                "seq": 2,
                "name": "국민은행"
            },
            {
                "seq": 3,
                "name": "우리은행"
            },
            {
                "seq": 4,
                "name": "신한은행"
            },
            {
                "seq": 5,
                "name": "한국시티은행"
            },
            {
                "seq": 6,
                "name": "하나은행"
            },
            {
                "seq": 7,
                "name": "농협은행/수협은행"
            },
            {
                "seq": 8,
                "name": "외환은행"
            },
            {
                "seq": 9,
                "name": "기타은행"
            }
        ]
    }
    ```
  - 500: Internal Server Error

### 년도별 각 금융기관의 지원금액 합계를 출력하는 API

- 문제해결 전략:
  - 전체조회 외에 optional 로 기간 (`from`, `to`) 을 줄 수 있게 개발
- METHOD: `GET`
- URL: `/api/house-finances/current-state-of-supply`
  - Params:
    - from: Integer - optional
    - to: Integer - optional

- RESPONSE
  - 200: OK
    - ex:
    ```json
    {
        "currentStateOfSupply": [
            {
                "year": "2016 년",
                "totalAmount": 400971,
                "detailAmount": {
                    "농협은행/수협은행": 23913,
                    "하나은행": 45485,
                    "우리은행": 45461,
                    "국민은행": 61380,
                    "주택도시기금1)": 91017,
                    "신한은행": 36767,
                    "외환은행": 5977,
                    "기타은행": 90925,
                    "한국시티은행": 46
                }
            },
            {
                "year": "2017 년",
                "totalAmount": 295126,
                "detailAmount": {
                    "농협은행/수협은행": 26969,
                    "하나은행": 35629,
                    "우리은행": 38846,
                    "국민은행": 31480,
                    "주택도시기금1)": 85409,
                    "신한은행": 40729,
                    "외환은행": 0,
                    "기타은행": 36057,
                    "한국시티은행": 7
                }
            },
            {
                "year": "2005 년",
                "totalAmount": 48016,
                "detailAmount": {
                    "농협은행/수협은행": 1486,
                    "하나은행": 3122,
                    "우리은행": 2303,
                    "국민은행": 13231,
                    "주택도시기금1)": 22247,
                    "신한은행": 1815,
                    "외환은행": 1732,
                    "기타은행": 1376,
                    "한국시티은행": 704
                }
            },
            {
                "year": "2006 년",
                "totalAmount": 41210,
                "detailAmount": {
                    "농협은행/수협은행": 2299,
                    "하나은행": 3443,
                    "우리은행": 4134,
                    "국민은행": 5811,
                    "주택도시기금1)": 20789,
                    "신한은행": 1198,
                    "외환은행": 2187,
                    "기타은행": 1061,
                    "한국시티은행": 288
                }
            },
            {
                "year": "2007 년",
                "totalAmount": 50893,
                "detailAmount": {
                    "농협은행/수협은행": 3515,
                    "하나은행": 2279,
                    "우리은행": 3545,
                    "국민은행": 8260,
                    "주택도시기금1)": 27745,
                    "신한은행": 2497,
                    "외환은행": 2059,
                    "기타은행": 854,
                    "한국시티은행": 139
                }
            },
            {
                "year": "2008 년",
                "totalAmount": 67603,
                "detailAmount": {
                    "농협은행/수협은행": 9630,
                    "하나은행": 1706,
                    "우리은행": 4290,
                    "국민은행": 12786,
                    "주택도시기금1)": 35721,
                    "신한은행": 1701,
                    "외환은행": 941,
                    "기타은행": 759,
                    "한국시티은행": 69
                }
            },
            {
                "year": "2009 년",
                "totalAmount": 96545,
                "detailAmount": {
                    "농협은행/수협은행": 8775,
                    "하나은행": 1226,
                    "우리은행": 13105,
                    "국민은행": 8682,
                    "주택도시기금1)": 44735,
                    "신한은행": 3023,
                    "외환은행": 6908,
                    "기타은행": 10051,
                    "한국시티은행": 40
                }
            },
            {
                "year": "2010 년",
                "totalAmount": 114903,
                "detailAmount": {
                    "농협은행/수협은행": 10984,
                    "하나은행": 1872,
                    "우리은행": 15846,
                    "국민은행": 16017,
                    "주택도시기금1)": 50554,
                    "신한은행": 2724,
                    "외환은행": 11158,
                    "기타은행": 5726,
                    "한국시티은행": 22
                }
            },
            {
                "year": "2011 년",
                "totalAmount": 206693,
                "detailAmount": {
                    "농협은행/수협은행": 19847,
                    "하나은행": 9283,
                    "우리은행": 29572,
                    "국민은행": 29118,
                    "주택도시기금1)": 69236,
                    "신한은행": 11106,
                    "외환은행": 8192,
                    "기타은행": 30326,
                    "한국시티은행": 13
                }
            },
            {
                "year": "2012 년",
                "totalAmount": 275591,
                "detailAmount": {
                    "농협은행/수협은행": 27253,
                    "하나은행": 12534,
                    "우리은행": 38278,
                    "국민은행": 37597,
                    "주택도시기금1)": 84227,
                    "신한은행": 21742,
                    "외환은행": 19975,
                    "기타은행": 33981,
                    "한국시티은행": 4
                }
            },
            {
                "year": "2013 년",
                "totalAmount": 265805,
                "detailAmount": {
                    "농협은행/수협은행": 17908,
                    "하나은행": 15167,
                    "우리은행": 37661,
                    "국민은행": 33063,
                    "주택도시기금1)": 89823,
                    "신한은행": 21330,
                    "외환은행": 10619,
                    "기타은행": 40184,
                    "한국시티은행": 50
                }
            },
            {
                "year": "2014 년",
                "totalAmount": 318771,
                "detailAmount": {
                    "농협은행/수협은행": 20861,
                    "하나은행": 20714,
                    "우리은행": 52085,
                    "국민은행": 48338,
                    "주택도시기금1)": 96184,
                    "신한은행": 28526,
                    "외환은행": 11183,
                    "기타은행": 40697,
                    "한국시티은행": 183
                }
            },
            {
                "year": "2015 년",
                "totalAmount": 374773,
                "detailAmount": {
                    "농협은행/수협은행": 18541,
                    "하나은행": 37263,
                    "우리은행": 67999,
                    "국민은행": 57749,
                    "주택도시기금1)": 82478,
                    "신한은행": 39239,
                    "외환은행": 20421,
                    "기타은행": 51046,
                    "한국시티은행": 37
                }
            }
        ],
        "code": 200
    }
    ```
  - 500: Internal Server Error

### 각 년도별 각 기관의 전체지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API

- 문제해결 전략:
  - 년도를 입력 받아 해당 년도의 가장 큰 금액의 기관 조회
- METHOD: `GET`
- URL: `/api/house-finances/most-support-banks/{year}`
  - Path variable: {year: Integer}
- RESPONSE
  - 200: OK
    - ex:
    ```json
    {
        "mostSupportBank": {
            "year": 2015,
            "bankName": "주택도시기금1)"
        },
        "code": 200
    }
    ```
  - 500: Internal Server Error

### 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에 가장 작은 금액과 큰 금액을 출력하는 API

- 문제해결 전략:
  - 외환은행 외의 다른 기관도 조회 할 수 있도록 `seq` 와 년도 (`from`, `to`)를 입력받도록 개발
- METHOD: `GET`
- URL: `/api/house-finances/min-max-of-avg/{bankSeq}`
  - Path variable: {bankSeq: Long} (외환은행 seq = 8 (제공된 csv 상으로))
  - Params:
    - from: Integer - optional (default: 2005)
    - to: Integer - optional (default: 2016)
- RESPONSE
  - 200: OK
    - ex:
    ```json
    {
        "code": 200,
        "minMaxOfAverage": {
            "bankName": "외환은행",
            "supportAmount": {
                "min": {
                    "year": 2008,
                    "amount": 78
                },
                "max": {
                    "year": 2015,
                    "amount": 1702
                }
            }
        }
    }
    ```
  - 500: Internal Server Error

### 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API

- 문제해결 전략:
  - [Linear Regression](https://en.wikipedia.org/wiki/Linear_regression) 알고르즘 시용
  - 구현체는 [프린스턴 대학 수업에서 구현한 간단한 모델](https://introcs.cs.princeton.edu/java/97data/) 사용 ([ref](https://github.com/fracpete/princeton-java-algorithms/blob/master/src/main/java/edu/princeton/cs/algorithms/LinearRegression.java))
  - regression 에서 x 축은 년도 (year) y 축은 amount 를 의미
- METHOD: `GET`
- URL: `/api/house-finances/predictions/{bankSeq}`
  - Path variable: {bankSeq: Long}
  - Params:
    - trainingFrom: Integer - required (default: 2005)
    - trainingTo: Integer - required (default: 2017)
    - predictionYear: Integer - required (default: 2018)
    - predictionMonth: Integer - required (default: 2)
- RESPONSE
  - 200: OK
    - ex:
    ```json
    {
        "prediction": {
            "bankName": "국민은행",
            "year": 2018,
            "month": 2,
            "amount": 4576
        },
        "code": 200
    }
    ```
  - 500: Internal Server Error

### JWT 관련 API

#### signup 계정생성 API

- 문제해결 전략:
  - Spring security 에서 제공하는 `BCryptPasswordEncoder` 를 사용하여 password 암호화
- METHOD: `POST`
- URL: `/users/sign-up`
  - Body:
    - id: String - required
    - password: String - required
- RESPONSE
  - 201: Created
    - ex:
    ```json
    {
        "code": 201
    }
    ```
  - 500: Internal Server Error

#### signin 로그인 API

- 문제해결 전략:
  - [jjwt](https://github.com/jwtk/jjwt) 를 사용하여 token 생성
- METHOD: `POST`
- URL: `/users/sign-in`
  - Body:
    - id: String - required
    - password: String - required
- RESPONSE
  - 200: OK
    - ex:
    ```json
    {
        "code": 200,
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ1c2VybmFtZSIsImlhdCI6MTU2NTUyNzI3NSwibmJmIjoxNTY1NTI3Mjc1LCJleHAiOjE1NjU1MzA4NzUsInNjb3BlIjoiYXBpIHVzZXIifQ.xdotszx-0goXClayIm0Hpe-B6_Tjlcsta0PSKfAgSZ4"
    }
    ```
  - 406: Not acceptable (비밀번호가 틀릴 경우)
  - 500: Internal Server Error

#### refresh 토큰 재발급 API

- 문제해결 전략:
  - header parsing
- METHOD: `POST`
- URL: `/api/tokens/refresh`
  - Header:
    - authorization: Bearer {token}
- RESPONSE
  - 200: OK
    - ex:
    ```json
    {
        "code": 200,
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ1c2VybmFtZSIsImlhdCI6MTU2NTUyNzI3NSwibmJmIjoxNTY1NTI3Mjc1LCJleHAiOjE1NjU1MzA4NzUsInNjb3BlIjoiYXBpIHVzZXIifQ.xdotszx-0goXClayIm0Hpe-B6_Tjlcsta0PSKfAgSZ4"
    }
    ```
  - 500: Internal Server Error
