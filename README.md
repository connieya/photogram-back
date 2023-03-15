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
    http.
            formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .addFilter(corsConfig.corsFilter())
            .addFilterBefore(new JWTAuthenticationFilter(tokenHelper) , UsernamePasswordAuthenticationFilter.class)

            .authorizeRequests()
            .antMatchers("/api/user/**","/user/**","/image/**","/subscribe/**","/comment/**,/api/**")
            .authenticated()
            .anyRequest().permitAll();
  }
}
```


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

![팔로우우](https://user-images.githubusercontent.com/66653324/223716096-9d773d28-f015-430a-b529-1bcd093361b7.gif)


<br/>

<details>
<summary>팔로우한 유저 게시물 보기</summary>

### Querydsl 사용 - 서브쿼리 

- 팔로우 한 유저 아이디 가져오기 (Follow 테이블)
```java
queryFactory
.select(follow.toUser.id)
.from(follow)
.where(follow.fromUser.id.eq(principalId))
.fetch();
```
- Image 테이블에서 userId 가 팔로우 한 유저 아이디에 해당하는 정보 가져오기
  - 서브 쿼리 사용
  - 게시물 업로드 최신 순으로 가져오기
```java
queryFactory
.selectFrom(image)
.where(image.user.id.in(JPAExpressions.select(follow.toUser.id).from(follow)
.where(follow.fromUser.id.eq(principalId)))).orderBy(image.createDate.desc()).fetch();
```


</details>

![게시물보기](https://user-images.githubusercontent.com/66653324/224479239-59bb7d32-d4c9-4b48-ae15-74cbce1b69f9.gif)

<br/>


<details>
<summary>좋아요 많은 인기 게시물</summary>

### 응답 dto

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePopularDto {

    private int id;
    private String caption;
    private String postImageUrl;
    private long likeCount;
    private User user;
}
```

### querydsl 로 데이터 조회

- 좋아요 테이블 (likes) 에서 imageId로 그룹화하기
- 좋아요 테이블 (likes) 과 게시물 테이블 (Images) 조인화기
- imageId로 그룹화 한 뒤 count 개수로 정렬
- 좋아요 개수가 같을 경우 게시물 등록 최신 순 정렬
- 개수는 9개만 (limit = 9)

```java
queryFactory
                .select(Projections.fields(ImagePopularDto.class,
                        image.id, image.caption , image.postImageUrl, image.user
                        , likes.image.id.count().as("likeCount")))
                .from(image)
                .innerJoin(likes)
                .on(image.id.eq(likes.image.id))
                .groupBy(likes.image.id)
                .orderBy(likes.image.id.count().desc(), image.createDate.desc())
                .limit(9)
                .fetch();
```



</details>

![인기게시물](https://user-images.githubusercontent.com/66653324/225461487-075d202f-42ce-4548-8450-41a6577b45f5.gif)

<br/>