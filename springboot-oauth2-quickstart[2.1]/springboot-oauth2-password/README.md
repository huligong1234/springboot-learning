## SpringBoot+OAuth2+password授权模式

### password 授权模式
* 申请access_token
```shell
$ curl -i -X POST -d "username=demoUser1&password=123456&grant_type=password&client_id=demoApp&client_secret=demoAppSecret" http://localhost:8080/oauth/token
```

* 返回 access_token
```json
{"access_token":"15aa2a89-fd6f-48d7-b495-81eaab7d017e","token_type":"bearer","refresh_token":"e9796ba1-186e-4f50-a159-77de663bca75","expires_in":7199,"scope":"all"}
```

### 请求资源
```shell
$ curl -i -H "Accept: application/json" -H "Authorization: Bearer 15aa2a89-fd6f-48d7-b495-81eaab7d017e" -X POST http://localhost:8080/api/sayHello
或
$ curl -i -X POST http://localhost:8080/api/sayHello?access_token=15aa2a89-fd6f-48d7-b495-81eaab7d017e
```
