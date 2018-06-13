## SpringBoot+OAuth2+client_credentials授权模式

### client_credentials 授权模式
* 申请access_token
```shell
$ curl http://localhost:8080/oauth/token -d "grant_type=client_credentials&client_id=demoApp&client_secret=demoAppSecret"
或
$ curl demoApp:demoAppSecret@localhost:8080/oauth/token -d grant_type=client_credentials
```

* 返回 access_token
```json
{"access_token":"583080c0-c281-4ccc-af40-754bf269ca9c","token_type":"bearer","expires_in":1199,"scope":"all"}
```

### 请求资源
```shell
$ curl -i -X POST http://localhost:8080/api/sayHello -H "Authorization: Bearer 583080c0-c281-4ccc-af40-754bf269ca9c"
```