-- ----------------------------
-- 1、广告管理表
-- ----------------------------
drop table if exists adv_management;
create table adv_management (
  adv_id      int              not null         comment '广告id',
  pic_url     varchar(255)     default null     comment '照片地址',
  link_url    varchar(255)     default null     comment '链接地址',
  new_window  tinyint          default null     comment '是否',
  primary key (adv_id)
) engine=innodb comment = '广告管理表';

-- ----------------------------
-- 初始化-广告管理表数据
-- ----------------------------
insert into adv_management values(0,  '',  '',  0);
insert into adv_management values(1,  '',  '',  0);
insert into adv_management values(2,  '',  '',  0);
insert into adv_management values(3,  '',  '',  0);
insert into adv_management values(4,  '',  '',  0);


-- ----------------------------
-- 2、首页轮播图管理表
-- ----------------------------
drop table if exists carousel_map;
create table carousel_map (
  poster_id         int             not null          comment '海报id',
  on_off            tinyint         default null      comment '开关',
  display_mode      tinyint         default null      comment '展示模式',
  pic_url           varchar(255)    default null      comment '照片地址',
  back_color        varchar(255)    default null      comment '背景颜色',
  link_url          varchar(255)    default null      comment '链接地址',
  pic_desc          varchar(255)    default null      comment '照片描述',
  primary key (poster_id)
) engine=innodb comment = '首页轮播图管理表';

-- ----------------------------
-- 初始化-首页轮播图管理表
-- ----------------------------
insert into carousel_map values(0, 0, 0, '', '', '', '');
insert into carousel_map values(1, 0, 0, '', '', '', '');
insert into carousel_map values(2, 0, 0, '', '', '', '');
insert into carousel_map values(3, 0, 0, '', '', '', '');
insert into carousel_map values(4, 0, 0, '', '', '', '');
insert into carousel_map values(5, 0, 0, '', '', '', '');
insert into carousel_map values(6, 0, 0, '', '', '', '');
insert into carousel_map values(7, 0, 0, '', '', '', '');
insert into carousel_map values(8, 0, 0, '', '', '', '');


-- ----------------------------
-- 3、首页轮播图管理表
-- ----------------------------
