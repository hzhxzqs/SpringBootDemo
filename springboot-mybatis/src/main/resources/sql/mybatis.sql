create database demo_mybatis;

use demo_mybatis;

drop table if exists `demo`;
create table `demo`
(
    `id`          bigint auto_increment primary key,
    `create_date` datetime(6),
    `demo_desc`   varchar(255)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
