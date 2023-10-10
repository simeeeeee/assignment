create table user
(
    user_id  bigint auto_increment
        primary key,
    email    varchar(255) not null,
    password varchar(255) not null,
    name     varchar(50)  not null,
    tel      varchar(100) null
);


INSERT INTO assignment.user (user_id, email, password, name, tel) VALUES (2, 'whdk2340@naver.com', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'gildong', '010-1123-1234');
INSERT INTO assignment.user (user_id, email, password, name, tel) VALUES (3, 'whdk237@naver.com', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'gildong', '010-2345-3434');
INSERT INTO assignment.user (user_id, email, password, name, tel) VALUES (4, 'whdk0776@naver.com', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'gildong', '010-2345-3434');
