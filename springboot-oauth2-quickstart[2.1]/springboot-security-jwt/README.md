## SpringBoot + Security + JWT 模式

### 一、概述


### 二、请求资源

* 调用登录地址获得Token
```shell
$ curl -i -X POST -d "username=admin&password=123456" http://localhost:8080/login
```

返回Jwt Token
```json
{"code":"2000","data":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyOTgyMDA3Nn0.kV6DihMNEuB9dD71jx5nhxkNvKxkbkv1o3HeNWNJ_-kuKSwzQGoTHxFBE9X91DFiOes-42bVs6pDDzc5KhphUw"}
```


* 带上Token请求资源
```shell
$ curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyOTkwNjc3OX0.XPiYDz0amUtXjdjVQw40OeNW9UuSnCIGzpSf1eGjlVkuvS51Wf5rktpWZqGZs-6nP65d26wINXq3JvVGHjkN-A" -X POST http://localhost:8080/api/sayHello
```


### 三、相关参考

* 使用JWT保护你的Spring Boot应用 - Spring Security实战  
https://segmentfault.com/a/1190000009231329

* Springboot+Spring-Security+JWT+Redis实现restful Api的权限管理以及token管理  
https://blog.csdn.net/ech13an/article/details/80779973

* 重拾后端之Spring Boot（四）：使用JWT和Spring Security保护REST API
https://www.jianshu.com/p/6307c89fe3fa

* JWT Authentication Tutorial: An example using Spring Boot  
http://www.svlada.com/jwt-token-authentication-with-spring-boot/