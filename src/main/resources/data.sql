INSERT INTO ROLES (ID, NAME) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

INSERT INTO USERS (ID, PASSWORD, USERNAME, EMAIL) VALUES (1, '$2a$04$xRj.iSxRN7.hVao2ltvrku7o2NUEsk0DxZ1Qatv5UGDP7ncZS3MBS', 'test', 't@t.com');

INSERT INTO USERS_ROLES (USER_ID, ROLE_ID) VALUES (1,2), (1,1);