-- noinspection SqlNoDataSourceInspectionForFile

INSERT IGNORE INTO role (id, name)
VALUES (1, "ROLE_USER");
INSERT IGNORE INTO role (id, name)
VALUES (2, "ROLE_ADMIN");
INSERT IGNORE INTO user (id, email, first_name, last_name, password, role_id)
VALUES (1, "admin@abv.bg", "Admin", "Adminov",
        "$2a$10$/LjlWo218PKRod1d78mAX.D5yc.Kc6QmqrkHEF3pS2/WA1mJR0Goy", 2);


