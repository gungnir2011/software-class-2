
create table user(
 user_id int auto_increment primary key,
 user_name varchar(20) not null,
 user_psd varchar(20) not null
);

insert into user(user_name,user_psd)values('gao','gao');
insert into user(user_name,user_psd)values('cheng','cheng');
insert into user(user_name,user_psd)values('zhen','zhen');
insert into user(user_name,user_psd)values('aaa','123');
insert into user(user_name,user_psd)values('bbb','123');