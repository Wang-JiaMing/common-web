# 通用WEB框架 version 20.3.25.1
> author:wangjiaming

### 前言
为了快速对业务内容开发，所以形成该该底层框架，从而减少重复编码工作。所有后台例子均在com.wonders.commonweb.demo

### 开发规范
程序会不断更新迭代新特性(特性内容可以查阅更新日志)，更新牵涉路径
>PS：com.wonders.commonweb.core.**目录不能做任何修改，否者会影响后期更新特性
1. java:com.wonders.commonweb.core.**
2. html:templates/pages

故开发时候请独立建立自己的package和html文件夹，例如
- java：com.wonders.commonweb.xxx.
- html:templates/xxx

### 特性标签
> 可以通过全文搜索对以下特性代码进行快速定位
- TODO:系统别名
- TODO:系统变量
- TODO:文件上传
- TODO:静态文件导出
> 注意文件存放位置 resources/static/downFiles
- TODO:动态Excel下载
- TODO:自动表单插件
- TODO:页面排版
- WEBSERVICE:webService特性

### 更新日志
#### 2020/07/08
1. 接入SSO以及AUTH权限框架。
主要配置在application.properties
> 1.    IS_SSO_MENUS配置是否通过单点获取权限框架下的菜单[false/true]。
> 2.    SSO_IP_AND_PORT填入sso以及auth的http地址以及端口
> 3.    INNER_RESOURCE_URL根据产品的请求路径，获取资源菜单[PS:最后部分是对应auth系统的节点]
```
#是否启用单点获取菜单
IS_SSO_MENUS=false
#sso和auth部署的地址
SSO_IP_AND_PORT=http://192.168.1.200:8080
#请求内部资源的路径
INNER_RESOURCE_URL=/auth/service/webService/getResourcesByUserID/USER_ID/PT_YWJG
#本地部署的地址，在正式库不要写localhost和127.0.0.1
LOCAL_SERVER_ADDR=http://localhost:${server.port}
```

#### 2020/03/12    
- 完善demo用例，引入缓存框架

#### 2020/02/27   
- 引入自动表单插件

#### 2020/02/26   <br> 
1.加入动态sql分页方法 <br> 
2.优化静态文件下载兼容性

```java
    /**
     * 1.加入动态sql分页方法
     **/
    @RequestMapping("/differenceFileData")
    @ResponseBody
    public ResultList differenceFileData(EpidemicPerson epidemicPerson){
        StringBuffer whereSql=new StringBuffer();
        if(!StringUtils.isEmpty(epidemicPerson.getName())){
            whereSql.append("and (name like '%"+epidemicPerson.getName()+"%' or "+epidemicPerson.getName()+" is null) " );
        }
        if(!StringUtils.isEmpty(epidemicPerson.getCardNo())){
            whereSql.append("and (card_no like '%"+epidemicPerson.getCardNo()+"%' or "+epidemicPerson.getCardNo()+" is null) " );
        }

        String sql="select p.id,p.name,p.phone,p.sex,p.birthday,p.card_type cardType,p.card_no cardNo,p.city," +
                "                p.city_code cityCode,p.area,p.area_code areaCode,p.jd,p.jd_code jdCode," +
                "                p.hj,p.address,p.work_unit workUnit,p.contact,p.contact_phone contactPhone," +
                "                p.batch_no batchNo,to_char(p.create_date,'yyyy-mm-dd') createDate ,i.mobile_phone_number mobilePhoneNumber from rhin_epidemic.tb_epid_person p,rhin_pub.basic_personal_information i " +
                "where p.card_no=i.id_number " +
                "and p.phone<>i.mobile_phone_number " +whereSql.toString();

        Map<String, String> params = new HashMap<>();
        params.put("sql", sql);
        //排序字段
        params.put("sortField","batchNo");
        //升序、降序
        params.put("sort","desc");
        //分页起始下标
        params.put("offset", epidemicPerson.getOffset());
        //分页间距
        params.put("limit", epidemicPerson.getLimit());
        return commonService.queryForResultList(params);
    }
```

#### 2020/02/25    
- 加入数据库导出Excel功能，加入静态excel下载功能
>静态文件请存放在static/downFiles下
**动态excel下载**
```js
$("#down").click(function(){
         window.location.href="../demoPage/download";
});
```


#### 2020/02/03    
- 加入文件上传模块<br>
>文件上传界面均使用模态窗弹出,通用上传模态弹窗已在include/uploadFile.html
>,只需把弹窗事件绑定即可

```html
<button type="button" id="impData" class="btn btn-white btn-pink btn-sm" data-toggle="modal" data-target=".bs-example-modal-lg">上传文件</button>
```

