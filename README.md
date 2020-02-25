# 通用WEB模板 version 20.2.25.1

### 前言
为了快速对业务内容开发，故开发该低层框架，减少重复编码工作。所有后台例子均在com.wonders.commonweb.controller.DemoController

### 开发规范
程序会不断更新迭代新特性(特性内容可以查阅更新日志)，更新牵涉路径主要
- java:com.wonders.commonweb.**
- html:templates/pages

故开发时候请独立建立自己的package和html文件夹，例如
- java：com.wonders.xxx
- html:templates/xxx

### 更新日志
- 2020/02/03    加入excel 上传模块
- 2020/02/25    加入数据库导出Excel功能，加入静态excel下载功能
>静态文件请存放在static/downFiles下
**动态excel下载**
```js
$("#down").click(function(){
         window.location.href="../demoPage/download";
});
```
