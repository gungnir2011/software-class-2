drop table book;
create table book(
book_id int primary key auto_increment,
book_name varchar(50) not null,
book_price float,
book_author varchar(20),
book_press varchar(50)
);

insert into book(book_name,book_price,book_author,book_press)values('AndroidӦ�ÿ����̳�',48.5,'��Ԫ��','������У������');

insert into book(book_name,book_price,book_author,book_press)values('���Android����',89,'���','���ӹ�ҵ������');

insert into book(book_name,book_price,book_author,book_press)values('��ĥ���ģʽ',65,'�³�','�廪��ѧ������');

insert into book(book_name,book_price,book_author,book_press)values('���Java����',98,'���','���ӹ�ҵ������');