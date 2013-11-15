drop table grade;
create table grade(
grade_id int primary key auto_increment,
num varchar(20),
course_name varchar(50),
credit int,
score int
);

insert into grade(num,course_name,credit,score)values('220122004','Java程序设计',3,88);
insert into grade(num,course_name,credit,score)values('220122004','软件工程',4,80);
insert into grade(num,course_name,credit,score)values('220122004','计算机基础',2,86);
insert into grade(num,course_name,credit,score)values('220122006','Java程序设计',3,90);
insert into grade(num,course_name,credit,score)values('220122008','Java程序设计',3,84);

