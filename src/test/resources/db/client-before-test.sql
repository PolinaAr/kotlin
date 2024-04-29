DELETE FROM client;
DELETE FROM job;
DELETE FROM `position`;

INSERT INTO job (id, name)
VALUES(1, 'Andersen');

INSERT INTO `position` (id, name)
VALUES(1, 'Engineer'),
    (2, 'Lead');

INSERT INTO client (id, first_name, last_name, email, job_id, position_id, gender, created, updated, version, deleted)
VALUES (1, 'Mark', 'Johnson', 'mark@gmail.com', null, null, 'MALE', now(), now(), 0, false),
    (2, 'Paula', 'Watson', 'watson@gmail.com', 1, 1, 'FEMALE', now(), now(), 0, false),
    (3, 'Marcus', 'Torres', 'torres@gmail.com', 1, 2, 'MALE', now(), now(), 0, false);