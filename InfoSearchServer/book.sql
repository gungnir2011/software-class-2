drop table book;
create table book(
book_id int primary key auto_increment,
book_name varchar(50) not null,
book_price float,
book_author varchar(20),
book_press varchar(50)
);

insert into book(book_name,book_price,book_author,book_press)values('Android应用开发教程',48.5,'钟元生','江西高校出版社');

insert into book(book_name,book_price,book_author,book_press)values('疯狂Android讲义',89,'李刚','电子工业出版社');

insert into book(book_name,book_price,book_author,book_press)values('研磨设计模式',65,'陈臣','清华大学出版社');

insert into book(book_name,book_price,book_author,book_press)values('疯狂Java讲义',98,'李刚','电子工业出版社');