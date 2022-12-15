---
title: 南乔到家
---

# 南乔到家 O2O上门服务


## 表设计

用户表

``` sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `avatar` varchar(2000) DEFAULT NULL COMMENT '用户头像',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `age` int(11) NOT NULL DEFAULT '0' COMMENT '年龄',
  `sex` char(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8

```

社会化用户表

``` sql
CREATE TABLE `social_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `uuid` varchar(255) NOT NULL COMMENT '第三方系统的唯一ID',
  `source` varchar(100) NOT NULL COMMENT '第三方用户来源',
  `access_token` varchar(255) NOT NULL COMMENT '用户的授权令牌',
  `expire_in` int(11) DEFAULT NULL COMMENT '第三方用户的授权令牌的有效期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='社会化用户表'

```

社会化用户和系统用户关系表

``` sql
CREATE TABLE `social_user_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '系统用户ID',
  `social_user_id` int(11) NOT NULL COMMENT '社会化用户表主键ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='社会化用户和系统用户关系表'
```

> 注意
>
> 1. 建议通过`uuid` + `source`的方式唯一确定一个用户，这样可以解决用户身份归属的问题。因为 单个用户ID 在某一平台中是唯一的，但不能保证在所有平台中都是唯一的。
>
> 2. 相关SQL操作的伪代码
     >
     >    获取第三方平台 `GITHUB` 用户（UUID = `xxxxxxx`）的 `SQL` 语句伪代码：
>
> ``` sql
> SELECT
>     su.* 
> FROM
>     `social_user` su 
> WHERE
>     su.uuid = 'xxxxxxx' 
>     AND su.source = 'GITHUB'
> ```
>
> 	查询系统用户（ID = `1`）是否绑定了 `GITHUB` 平台账号的 `SQL` 语句伪代码：
>
> ``` sql
> SELECT
>     count(1)
> FROM
>     `social_user_auth` sua
> INNER JOIN `social_user` su ON sua.social_user_id = su.id
> WHERE
>     sua.user_id = '1' 
>     AND su.source = 'GITHUB'
> ```
>
> 	解绑 `GITHUB` 平台的绑定账号
>
> ``` sql
> DELETE FROM `social_user_auth` sua WHERE sua.social_user_id = '1' AND sua.user_id = '1'
> ```

理疗师表

``` sql
CREATE TABLE `physio` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `nickname` varchar(50) NOT NULL COMMENT '昵称',
  `avatar` varchar(255) NOT NULL COMMENT '头像',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '服务状态:1可服务 0不可服务',
  `like` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `bill_count` int(11) NOT NULL DEFAULT '0' COMMENT '服务单数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理疗师表'
```

理疗师和项目的关系表

``` sql
CREATE TABLE `physio_pro` (
  `id` int(11) NOT NULL COMMENT '主键ID',
  `physio_id` int(11) NOT NULL COMMENT '理疗师ID',
  `project_id` int(11) NOT NULL COMMENT '项目ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理疗师和项目的关系表'
```

服务项目表

``` sql
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) NOT NULL COMMENT '项目标题',
  `img` varchar(500) NOT NULL COMMENT '项目图片',
  `duration` varchar(100) NOT NULL COMMENT '项目时长',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '价格',
  `consume_count` int(11) NOT NULL DEFAULT '0' COMMENT '消费人数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务项目表'
```

订单表

``` sql
-- auto-generated definition
create table orders
(
    id           int auto_increment
        primary key,
    uid          int            null comment '用户ID',
    total_money  decimal(10, 2) null comment '总金额',
    pay_type     int            null comment '支付类型',
    flag         int            null comment '订单状态',
    create_time  datetime       null comment '创建时间',
    update_time  datetime       null comment '更新时间',
    no           varchar(50)    null comment '订单编号',
    project_id   int            not null comment '项目Id',
    project_name varchar(50)    not null comment '项目名称',
    physio_id    int            not null comment '技师ID',
    physio_name  varchar(50)    not null comment '技师名字'
)
    comment '订单表';
```

## 框架搭建

> 技术选型：
>
> 后端：SpringBoot + MyBatis + mysql + swagger + knife4j + MinIO + SMS + QrCode + JustAuth + alipay + redis
>
> 前端：VUE + element UI + axios + vuex + vue-router

开发流程：

需求分析 + 数据库设计 + 框架搭建[前后端] + 接口文档 + 开发接口 + 测试 + 上线部署[云服务+虚拟机]


