## SpringBoot +Vue.js 前后端分离的架构方式 学习笔记

### 一、概述

### 1.后端服务项目 springboot-restful-backend

这里是直接复用之前创建的示例项目(springboot-mybatis-plus)

增加了针对跨域调用的配置类

### 2.前端项目 vue-frontend

```shell
sudo cnpm install webpack -g
sudo cnpm install vue-cli -g

vue init webpack vue-frontend

cd vue-frontend
cnpm install
cnpm install axios --save

#通过npm命令启动node server
npm run dev
```



## 二、webpack介绍

### 1.webpack是什么?
* 能够实现多种不同的前端模块系统
* webpack 打包前端资源（模块）时能够实现代码分割，按需加载
* 处理所有资源，如javascript、css、图片、模板（jade、各种template）等等

### 2.webpack与Node.js区分与联系
联系：webpack打包的时候需要用到node.js中的js库
区别：node.js是一种前端服务器，webpack则是模块打包机

### 3.webpack与Vue区别于联系
联系：webpack配合vue-loader可以将.vue格式的文件打包成js文件
区别：vue.js是一种用于前端开发的渐进式框架，webpack则是模块打包机