 ## SpringBoot + Security + Oauth2 + JWT 模式

### 一、概述


### 二、请求资源

* 申请access_token
```shell
$ curl -i -X POST -d "username=demoUser1&password=123456&grant_type=password&client_id=demoApp&client_secret=demoAppSecret" http://localhost:8080/oauth/token
```

* 返回 access_token
```json
{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6ImRlbW9Vc2VyMSIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE1MzAzNDA3NzUsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiOGI3MmU5MjEtOGExNi00ZWVmLWEzNGMtN2U4NTU1ODNiMjdmIiwiY2xpZW50X2lkIjoiZGVtb0FwcCJ9.rcC7bR1Ww6B2rrggGtZLkf5gkCRzWhorkRH2_Hpy4RY","token_type":"bearer","refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6ImRlbW9Vc2VyMSIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiI4YjcyZTkyMS04YTE2LTRlZWYtYTM0Yy03ZTg1NTU4M2IyN2YiLCJleHAiOjE1MzAzODM1NzUsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiOGRlMDIxMDgtYWEzNy00ZjBiLThjNmYtMzJiOGJlMWVjMWU0IiwiY2xpZW50X2lkIjoiZGVtb0FwcCJ9.ilwtqswzYPCf8tmg00XNGtXGBI7qZ9AUtuh-ciQyo5c","expires_in":7199,"scope":"all","jti":"8b72e921-8a16-4eef-a34c-7e855583b27f"}
```


### 三、请求资源

```shell
$ curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6ImRlbW9Vc2VyMSIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE1MzAzNDA3NzUsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiOGI3MmU5MjEtOGExNi00ZWVmLWEzNGMtN2U4NTU1ODNiMjdmIiwiY2xpZW50X2lkIjoiZGVtb0FwcCJ9.rcC7bR1Ww6B2rrggGtZLkf5gkCRzWhorkRH2_Hpy4RY" -X POST http://localhost:8080/api/sayHello
```


### 四、相关参考

Spring Security源码分析十一：Spring Security OAuth2整合JWT
https://blog.csdn.net/dandandeshangni/article/details/79146127