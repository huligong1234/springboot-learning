# vue-frontend

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8081
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run all tests
npm test
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).



---

## QuickStart

### 1.创建组件

* src/components/Index.vue
* src/components/Add.vue
* src/components/Edit.vue



### 2.修改路由配置

src/router/index.js

```javascript
import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import Add from '@/components/Add'
import Edit from '@/components/Edit'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'Index',
    component: Index
  },
  {
    path: '/add',
    name: 'Add',
    component: Add
  },
  {
    path: '/edit',
    name: 'Edit',
    component: Edit
  }
  ]
});
```





### 3.修改服务端口号为8081 (默认是8080，避免与spring-restful-backend 的端口号冲突)

* config/index.js







## 目录结构

```shell
vue-frontend
├── README.md
├── build 构建脚本目录
│   ├── build.js 生产环境构建脚本
│   ├── check-versions.js 检查node&npm等版本
│   ├── logo.png
│   ├── utils.js 构建配置公用工具
│   ├── vue-loader.conf.js vue加载器
│   ├── webpack.base.conf.js webpack基础环境配置
│   ├── webpack.dev.conf.js webpack开发环境配置
│   └── webpack.prod.conf.js webpack生产环境配置
├── config 配置目录
│   ├── dev.env.js 开发环境变量
│   ├── index.js 项目一些配置变量
│   ├── prod.env.js 生产环境变量
│   └── test.env.js 测试环境变量
├── index.html 首页
├── node_modules npm加载的项目依赖模块
├── package.json 项目基本信息和配置
├── src 源码目录
│   ├── App.vue 项目入口（根组件和路由入口）
│   ├── assets 系统静态源文件
│   │   └── logo.png
│   ├── components 公共组件库
│   │   ├── Add.vue 实际业务组件--新增页
│   │   ├── Edit.vue 实际业务组---修改页
│   │   └── Index.vue 实际业务组件--首页(数据列表和删除操作)
│   ├── main.js 入口js文件
│   └── router 路由
│       └── index.js
├── static 静态文件，比如一些图片，json数据等
└── test 测试文件目录
    └── unit 单元测试
├── .babelrc ES6语法编译配置
├── .editorconfig 定义代码格式
├── .eslintignore eslint检查忽略文件
├── .eslintrc.js eslint检查配置文件
├── .gitignore git上传需要忽略的文件格式
├── .postcssrc.js  css3样式补充
```

