create database if not exists CHAT;

use CHAT;

drop table if exists t_users;
create table `t_users` (
	`id` int(8) primary key default '0',
    `user` varchar(32) not null,
    `pass` varchar(32) not null
);

drop table if exists t_messages;
create table `t_messages` (
	`id` int(8) primary key default '0',
    `sender_id` int(8) not null,
    `receiver_id` int(8) not null,
    `content` text,
    `date` datetime not null
);