drop table grade;
create table grade(
grade_id int primary key auto_increment,
num varchar(20),
course_name varchar(50),
credit int,
score int
);

insert into grade(num,course_name,credit,score)values('220122004','Java�������',3,88);
insert into grade(num,course_name,credit,score)values('220122004','�������',4,80);
insert into grade(num,course_name,credit,score)values('220122004','���������',2,86);
insert into grade(num,course_name,credit,score)values('220122006','Java�������',3,90);
insert into grade(num,course_name,credit,score)values('220122008','Java�������',3,84);

