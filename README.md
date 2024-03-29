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
                ├── config
                ├── error
                ├── result
                ├── util
        
```


## 기능 요구 사항

### 회원

- 회원 등록
    - 이름 : 한글 또는 영문으로 된 회원명 , 최소 2자리 이상 최대 10자리 문자
    - 유저네임 (닉네임)
        - 최소 2자리 이상 최대 10자리 문자 이어야 한다.
        - 영문 으로만 이루어져야 한다.
        - 중간에 공백이 있으면 안된다.
        - 다른 회원과 동일한 유저네임이 아니어야 한다.
    - 이메일 : 바른 이메일 형식이면서 기존에 가입한 다른 회원과 동일한 이메일이 아니어야 한다.
    - 비밀번호 : 최소 4자리 이상 최대 12자리 문자 이어야 한다.
- 회원 프로필 조회
    - 유저네임으로 해당 회원의 프로필을 조회 한다.
    - 해당 회원의 개인 정보,  팔로우/팔로잉 수 , 팔로우/팔로워 목록을 볼 수 있다.
- 회원 수정
    - 이름, 유저네임 , 웹 사이트 , 소개 글을 수정 한다.
    - 유저네임은 다른 회원과 동일한 유저네임이 아니어야 한다.
- 회원 삭제
    - 회원이 작성한, 게시글, 게시글 좋아요,댓글,  팔로우 정보도 함께 삭제 된다.

### 팔로우

- 팔로우/ 팔로우 취소
    - 자기 자신을 제외 한 다른 회원을 팔로우 / 팔로우 취소 할 수 있다.
- 특정 회원의 팔로잉, 팔로워 목록을 조회 할 수 있다.


### 게시글

- 게시글을 업로드 할 수 있다.
- 자신이 팔로우 한 회원의 게시물 목록을 볼 수 있다.
- 게시물을 클릭하면 게시물 상세 정보를 볼 수 있다.
- 게시글 좋아요/ 좋아요 취소
- 자신이 작성한 게시글 삭제 할 수 있다. 
  - 게시글 좋아요 , 댓글도 함께 삭제한다.
 

### 댓글

- 게시글에 댓글을 작성할 수 있다.
- 자신이 작성한 댓글을 삭제할 수 있다.
- 댓글에 좋아요/좋아요 취소를 할 수 있다.
