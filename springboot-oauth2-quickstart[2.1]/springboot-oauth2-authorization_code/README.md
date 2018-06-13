## SpringBoot+OAuth2+Authorization Code授权模式

### Authorization Code 授权模式

* 申请Code
```
浏览器访问:
http://localhost:8080/oauth/authorize?response_type=code&scope=all&client_id=demoApp&redirect_uri=http://www.baidu.com&state=uuid-76w2-hg3q-98sa-769q2d20tda6
```

返回
https://www.baidu.com/?code=r1pSIp&state=uuid-76w2-hg3q-98sa-769q2d20tda6


* 带上code申请access_token
```shell
curl -X POST http://localhost:8080/oauth/token -H "Accept: application/json" -d "client_id=demoApp&client_secret=demoAppSecret&grant_type=authorization_code&code=r1pSIp&redirect_uri=http://www.baidu.com"
```

* 返回 access_token
```json
{"access_token":"bf8ca4d1-9e77-46b2-a5b4-2f51129d827a","token_type":"bearer","refresh_token":"a7841b4f-f14b-4e4f-8b59-de6ea1dd5c20","expires_in":7199,"scope":"all"}
```

### 请求资源
```shell
$ curl -X POST http://localhost:8080/api/sayHello -H "Authorization: Bearer bf8ca4d1-9e77-46b2-a5b4-2f51129d827a"
```