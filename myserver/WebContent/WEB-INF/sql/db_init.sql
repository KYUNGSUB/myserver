/*
 * id : facebook(15자 숫자), Kakao(9자 숫자), ID(영문 5자, 숫자 1자 이상, 고유
 */
create table member (
	mid int primary key not null auto_increment,
	uid varchar(30) not null,
	password varchar(30) not null,
	name varchar(50) not null,
	login_type int not null,
	email varchar(30),
	createdAt timestamp not null default '0000-00-00 00:00:00',
	modifiedAt timestamp not null default current_timestamp on update current_timestamp
);

desc member;
select * from member;

create table pds_item (
	pds_item_id int not null auto_increment,
	filename varchar(100) not null,
	realpath varchar(200) not null,
	filesize int,
	downloadcount int default 0,
	description varchar(255),
	articleId bigint,
	kind varchar(12),
	primary key (pds_item_id)
);

desc pds_item;
drop table pds_item;
/*
select * from pds_item where description like '10001060101';
*/
select * from pds_item;
alter table pds_item add articleId int;
alter table pds_item add kind varchar(12);

create table main_category (
	mid int not null primary key,
	kname varchar(50) not null,
	ename varchar(50) not null,
	mcount int default 0
);

create table sub_category (
	mid int not null,
	sid int not null,
	kname varchar(50) not null,
	ename varchar(50) not null,
	mcount int default 0
);

create table setting_item (
	parameterId int not null primary key auto_increment,
	parameterName varchar(100) not null,
	value varchar(200) not null,
	createdAt timestamp not null default '0000-00-00 00:00:00',
	modifiedAt timestamp not null default current_timestamp on update current_timestamp
);

desc setting_item;
drop table setting_item;
show tables;

create table access_token (
	deviceId varchar(40) not null primary key,
	token varchar(160) not null,
	createdAt timestamp not null default '0000-00-00 00:00:00',
	modifiedAt timestamp not null default current_timestamp on update current_timestamp
);

desc access_token;
drop table access_token;

select * from access_token;

/*  fcm_history
 * 	fcm_results
 */
create table fcm_message (
	id int not null primary key auto_increment,
	destination varchar(40) not null,
	method char(1),
	title varchar(80),
	body varchar(1024),
	data varchar(2048),
	xmpp char(1) default 'N',
	createdAt timestamp default current_timestamp
);

desc fcm_message;
drop table fcm_message;

SELECT * FROM FCM_MESSAGE;

create table fcm_history (
	id int not null primary key auto_increment,
	mid int not null,
	multicast_id bigint not null,
	success int default 0,
	failure int default 0,
	canonical_ids int default 0,
	createdAt timestamp default current_timestamp
);

drop table fcm_history;
select * from fcm_history;

create table fcm_results (
	multicast_id bigint not null primary key,
	message_id varchar(128) not null
);

drop table fcm_results;
select * from fcm_results;
select A.*, B.message_id from fcm_history as A, fcm_results as B where A.multicast_id=B.multicast_id;

create table fcm_group_name (
	gid int not null primary key auto_increment,
	notification_key_name varchar(30) not null,
	notification_key varchar(160) not null,
	createdAt timestamp default '0000-00-00 00:00:00',
	modifiedAt timestamp not null default current_timestamp on update current_timestamp
);

drop table fcm_group_name;

select * from fcm_group_name;

create table fcm_group_device (
	gid int not null,
	deviceId varchar(40) not null,
	createdAt timestamp not null default current_timestamp
);

select * from fcm_group_device;