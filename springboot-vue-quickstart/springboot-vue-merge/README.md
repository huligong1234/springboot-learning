##SpringBoot +Vue.js 前后端`不分离`的架构方式 学习笔记

本项目是在本人前一个`前后端分离`示例项目 [springboot-vue-part](https://github.com/huligong1234/springboot-learning/tree/master/springboot-vue-quickstart/springboot-vue-part) 的基础上增加和修改pom.xml而来。



基础架构的核心是使用maven插件 [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin)
,编译时自动将 vue项目下面的编译好的静态文件目录`dist`拷贝到springboot项目下的`src/main/resources/public/`目录下。



### 使用方式

```shell
在pom.xml配置好后，
开发时，只要vue项目下`dist`已有编译好的静态文件，只要运行springboot项目，就会自动拷贝相关静态文件到public目录下。

打包时，可以在 parent 目录下执行`mvn clean compile package -Dmaven.test.skip=true` 直接打包保含最新vue静态文件的jar包或war包
程序会自动先编译打包vue项目 然后打包 springboot项目

springboot-restful-backend启动后，直接访问http://localhost:8080 即可

```


### 重要目录结构说明

```shell
springboot-vue-merge
├── README.md
├── `pom.xml` 重要编译依赖文件
├── springboot-restful-backend  后端项目
│   ├── README.md
│   ├── document
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── `pom.xml`  重要编译依赖文件
│   ├── src
└── vue-frontend  前端vue项目
    ├── README.md
    ├── build
    ├── config
    ├── dist  vue项目编译后静态文件存放目录
    ├── index.html
    ├── node
    ├── node_modules
    ├── package.json
    ├── `pom.xml`  重要编译依赖文件
    ├── src
    ├── static
    └── test
```

