# Photogram  - Backend

## 인스타그램 클론코딩 프로젝트 

- Java 11
- Spring Boot 2.4.5
- Spring Security
- Spring Data JPA
- Springfox Swagger UI
- Querydsl
- MySQL
- H2 DataBase
- Mockito


## 패키지 구조 

기능이 증가해서 프로젝트가 이전보다 커짐에 따라 도메인형 구조로 변경

```
src
├──main
  ├──java
     ├──com
       ├──cos
          ├──photogramstart
          │  ├──domain
          │  │  ├── comment
          │  │  │  ├── application
          │  │  │  ├── domain
          │  │  │  ├── infrastructure
          │  │  │  ├── presentation
          │  │  ├── follow
          │  │  │  ├── application
          │  │  │  ├── domain
          │  │  │  ├── infrastructure
          │  │  │  ├── presentation
          │  │  ├── user
          │  │  │  ├── application
          │  │  │  ├── domain
          │  │  │  ├── infrastructure
          │  │  │  ├── presentation
          │  │  ├── post
          │  │  │  ├── application
          │  │  │  ├── domain
          │  │  │  ├── infrastructure
          │  │  │  ├── presentation
          │  │──global
        
```


