create table user
(
    id         bigint auto_increment
        primary key,
    email      varchar(255) not null,
    password   varchar(255) not null,
    name       varchar(50)  not null,
    tel        varchar(100) null,
    created_at datetime     null,
    updated_at datetime     null,
    user_id    binary(16)   null
);



INSERT INTO assignment.user (id, email, password, name, tel) VALUES (2, 'whdk2340@naver.com', '$2a$10$fcH/owLwzlfd2eRIXVnjC.1Ef9NvADlU5Gcv3Fme5g94MOacOzHP6', 'gildong', '010-1123-1234');
INSERT INTO assignment.user (id, email, password, name, tel) VALUES (3, 'whdk237@naver.com', '$2a$10$fcH/owLwzlfd2eRIXVnjC.1Ef9NvADlU5Gcv3Fme5g94MOacOzHP6', 'gildong', '010-2345-3434');
INSERT INTO assignment.user (id, email, password, name, tel) VALUES (4, 'whdk0776@naver.com', '$2a$10$fcH/owLwzlfd2eRIXVnjC.1Ef9NvADlU5Gcv3Fme5g94MOacOzHP6', 'gildong', '010-2345-3434');
