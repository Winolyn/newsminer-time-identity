# 谷雨系统爬虫

滴滴出行谷雨平台(https://wave.xiaojukeji.com) 司机成交额信息爬虫

# 功能说明

* 按日期、城市爬取对应的司机订单信息


# 自定义函数说明

```javascript
    def setup_logging()
```
* 根据json配置文件（logging.json）路径加载基本日志配置
    * 日志打印格式："时间 - Logger的名字 - 日志等级 - 日志信息"
    * 日志信息存储文件：info.log,error.log
    * 日志大小:10M,日志编码：utf-8
* 定义日志处理等级：info级

```javascript
    def crawl(phone, date_start=yesterday, date_end=yesterday)
```
* 接收参数：登陆使用的手机号,查询起始日期(选填),查询结束日期(选填,默认为当前日期的前一天)
* 逻辑过程：
    * 读取今日的cookie文件(没有即创建)
    * 创建今日的urlread文件(存储已读url,避免重复请求)
    * 读取司机列表
        * 利用司机id拼成的url串请求司机的订单列表
            * 利用订单编号请求订单的详细信息


# 数据库表结构
```javascript
    CREATE TABLE `pachongdrivers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `客户姓名` varchar(20) DEFAULT NULL,
  `车辆id` varchar(25) DEFAULT NULL,
  `车牌号` varchar(25) DEFAULT NULL,
  `所属公司` varchar(25) DEFAULT NULL,
  `所在城市` varchar(15) DEFAULT NULL,
  `绑定时间` datetime DEFAULT NULL,
  `司机id` varchar(25) DEFAULT NULL,
  `车型` varchar(25) DEFAULT NULL,
  `创建时间` datetime DEFAULT NULL,
  `data` varchar(500) DEFAULT NULL,
  `进表时间` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
CREATE TABLE `pachongorders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `订单编号` varchar(25) DEFAULT NULL,
  `订单序列号` varchar(10) DEFAULT NULL,
  `订单状态` varchar(15) DEFAULT NULL,
  `结束时间` datetime DEFAULT NULL,
  `纬度` varchar(15) DEFAULT NULL,
  `经度` varchar(15) DEFAULT NULL,
  `订单金额` varchar(15) DEFAULT NULL,
  `星级` varchar(10) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `data` varchar(500) DEFAULT NULL,
  `进表时间` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1283 DEFAULT CHARSET=utf8;

CREATE TABLE `pachongorderdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `订单编号` varchar(25) DEFAULT NULL,
  `司机id` varchar(20) DEFAULT NULL,
  `接单模式` varchar(15) DEFAULT NULL,
  `订单类型` varchar(15) DEFAULT NULL,
  `订单状态` varchar(15) DEFAULT NULL,
  `支付状态` varchar(15) DEFAULT NULL,
  `账单费用` varchar(15) DEFAULT NULL,
  `接驾地点` varchar(45) DEFAULT NULL,
  `目的地点` varchar(50) DEFAULT NULL,
  `评价星级` varchar(10) DEFAULT NULL,
  `评价内容` varchar(200) DEFAULT NULL,
  `评价时间` varchar(20) DEFAULT NULL,
  `订单完成_订单时长` varchar(15) DEFAULT NULL,
  `订单完成_里程` varchar(15) DEFAULT NULL,
  `开始计费_车牌` varchar(15) DEFAULT NULL,
  `开始计费_联系电话` varchar(20) DEFAULT NULL,
  `司机到达_提前到达` varchar(20) DEFAULT NULL,
  `司机接单_联系电话` varchar(20) DEFAULT NULL,
  `司机接单_接单司机` varchar(10) DEFAULT NULL,
  `司机接单_车型` varchar(25) DEFAULT NULL,
  `司机接单_车牌` varchar(15) DEFAULT NULL,
  `订单发起_预估里程` varchar(15) DEFAULT NULL,
  `订单发起_基础预估价格` varchar(15) DEFAULT NULL,
  `订单完成` datetime DEFAULT NULL,
  `开始计费` datetime DEFAULT NULL,
  `司机到达` datetime DEFAULT NULL,
  `司机接单` datetime DEFAULT NULL,
  `订单发起` datetime DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `data` varchar(2000) DEFAULT NULL,
  `进表时间` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `订单编号_UNIQUE` (`订单编号`)
) ENGINE=InnoDB AUTO_INCREMENT=703 DEFAULT CHARSET=utf8;

```

# 有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件(winolynl@gmail.com)