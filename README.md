# Photogram

<details>
<summary>회원가입</summary>

### 시큐리티 세팅

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

- 클라이언트가 서버에 요청하면 /login 으로 redirect 된다.

![img.png](img.png)

### 시큐리티 커스텀 하기

```java
@EnableWebSecurity
@Configuration // IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter {
}

```

- WebSecurityConfigureAdapter 를 상속받으면 시큐리티 설정파일로 인식이 된다.
- @Configuration 으로 해당 클래스를 IoC 컨테이너에 등록
- @EnableWebSecurity 로 시큐리티 설정파일로 인식된 파일을 활성화 시킴

```java
@EnableWebSecurity
@Configuration // IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**,/api/**") // 다음과 같은 URL은..
                .authenticated() // 인증이 필요합니다.
                .anyRequest() // 그 외의 URL은..
                .permitAll() // 허용 해주겠습니다.
                .and()
                .formLogin() // 인증이 필요한 URL은
                .loginPage("/auth/signin") // 로그인 창으로 리다이렉션 해주고 URL은 다음과 같다.
                .loginProcessingUrl("/auth/signin")// post
                .defaultSuccessUrl("/"); // 로그인에 성공하면 다음 URL로 이동
    }
}
```

- HttpSecurity http 의 authorizeRequests() 메서드
- .andMatchers() : 요청이 들어오는 경로 지정
- .authenticated() : 해당 경로로 들어온 요청은 인증이 필요하다고 지정
- .anyRequest() : 인증이 필요하지 않는 모든 요청
- .permitAll() : 모두 허용함
- .and() : 그리고
- .formLogin() : 로그인이 필요한 요청
- .loginPage() : 해당 URL에서 로그인 요청 처리
- .defaultSuccessUrl() :  로그인 응답 후 이동할 페이지 

### CSRF 토큰 해제

- 클라이언트가 웹 서버로 회원가입 데이터를 전송한다.
- 웹 서버를 보호하고 있는 시큐리티가 입구에서 시큐리티 CSRF 토큰 검사를 실시한다.
- CSRF 토큰 검사는 클라이언트가 
웹 서버가 응답해준 회원가입창을 통해서 정상적인 경로로 회원가입을 진행했는지 확인하는 것이다.
- 클라이언트가 응답 받았을 때 CSRF가 붙어서 전해지는 것이다.

```java
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 토큰 검사 비활성화
    }
```

- 시큐리티가 제공하는 CSRF 토큰 검사 기능을 비활성화 하자



</details>

![회원가입2](https://user-images.githubusercontent.com/66653324/223017753-c0536161-e25e-4a97-963d-b9fd1b2a0a59.gif)
<br/>




<details>
<summary>팔로우 하기/취소</summary>

### 팔로우 모델

```java
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "fromUserId")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;

    @PrePersist // 디비에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
```
같은 사람을 계속해서 팔로우 하면 안되기 때문에 Unique 제약 조건을 설정하였다.
```java
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscribe_uk",
                        columnNames = {"fromUserId" ,"toUserId"}
                )
        }
)
public class Subscribe {
}
```

실제 데이터베이스 컬럼명을 적어야 함

### 팔로우 API 

#### Controller


```java
@RestController
@RequiredArgsConstructor
public class SubscribeApiController {

    private final SubscribeService subscribeService;
    
    @PostMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails , @PathVariable int toUserId){
        subscribeService.subscribe(principalDetails.getUser().getId() , toUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로우 성공",null), HttpStatus.OK);
    }

    @DeleteMapping("/api/subscribe/{toUserId}")
    public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails , @PathVariable int toUserId) {
        subscribeService.unSubscribe(principalDetails.getUser().getId() , toUserId);
        return new ResponseEntity<>(new RespDto<>(1, "팔로우 취소 성공",null), HttpStatus.OK);
    }
}
```


#### Repository

네이티브 쿼리 사용

- 팔로우 하기
- 팔로우 취소
- 팔로우 했는지 여부
- 팔로워 숫자
- 팔로잉 숫자

```java
public interface SubscribeRepository extends JpaRepository<Subscribe,Integer> {

    @Modifying
    @Query(value = "insert into subscribe(fromUserid ,toUserId , createDate) values(:fromUserId, :toUserId , now())", nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "delete from  subscribe where fromUserId =:fromUserId and toUserId =:toUserId", nativeQuery = true)
    void mUnSubscribe(int fromUserId , int toUserId);

    @Query(value = "SELECT count(*) FROM subscribe WHERE fromUserId =:principalId and toUserId= :pageUserId" , nativeQuery = true)
    int mSubscribeState(int principalId, int pageUserId);

    @Query(value = "SELECT count(*) FROM subscribe WHERE fromUserId =:pageUserId" , nativeQuery = true)
    int mSubscribeCount(int pageUserId);

    @Query(value = "SELECT count(*) FROM subscribe WHERE toUserId =:pageUserId" , nativeQuery = true)
    int mSubscribedCount(int pageUserId);

}
```

</details>

![팔로우성공](https://user-images.githubusercontent.com/66653324/223059521-186b42f1-b07f-428d-8fd6-44e9af9a82ec.gif)

<br/>


<details>
<summary>예외 처리하기</summary>

### ExceptionHandler

```java
@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String  validationException(CustomValidationException e) {
        if (e.getErrorMap() == null){
            return Script.back(e.getMessage());
        }
        return Script.back(e.getErrorMap().toString());
    }
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(new RespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(new RespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String CustomException(CustomException e) {
        return Script.back(e.getMessage());
    }
}
```

### 팔로우

팔로우하기 예외처리

```java
    @Transactional
    public void subscribe(int fromUserId , int toUserId){
        try {
            subscribeRepository.mSubscribe(fromUserId,toUserId);
        }catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }

    }
```

</details>