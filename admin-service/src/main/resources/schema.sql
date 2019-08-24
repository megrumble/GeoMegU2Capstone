create schema if not exists spring_security;
use spring_security;

create table if not exists users(
    username varchar(50) not null primary key,
    password varchar(100) not null,
    enabled boolean not null
);

create table if not exists authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username));
    create unique index ix_auth_username on authorities (username,authority
);
insert into users (username, password, enabled) values ('employee', '$2a$10$KxTc8SYbIB/IaXCWz6NA4ug1pkAYM/e.P.0YQFGE3Ua4FZ6Qf842a', true);
insert into users (username, password, enabled) values ('teamLead', '$2a$10$bW.JeNBrcMFBPpydb4ttUO0jlFRQmH.aRVPz.ErbCbawZspX99lPO', true);
insert into users (username, password, enabled) values ('manager', '$2a$10$QPnaeWBWz1BdDglni2CLzO2YMeifVXtQDPgUOVNETTcj8cEGwqiym', true);
insert into users (username, password, enabled) values ('admin', '$2a$10$Hc878CPLJ4hOtwyzt6V7..LHtzhcR3zqcXOAPseY9QGg05ZxcsTR6', true);

insert into authorities (username, authority) values ('employee', 'ROLE_EMPLOYEE');
insert into authorities (username, authority) values ('teamLead', 'ROLE_EMPLOYEE');
insert into authorities (username, authority) values ('teamLead', 'ROLE_LEAD');
insert into authorities (username, authority) values ('manager', 'ROLE_EMPLOYEE');
insert into authorities (username, authority) values ('manager', 'ROLE_LEAD');
insert into authorities (username, authority) values ('manager', 'ROLE_MANAGER');
insert into authorities (username, authority) values ('admin', 'ROLE_EMPLOYEE');
insert into authorities (username, authority) values ('admin', 'ROLE_LEAD');
insert into authorities (username, authority) values ('admin', 'ROLE_MANAGER');
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');