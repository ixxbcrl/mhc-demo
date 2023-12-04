-- CREATE TABLE account_type (
--     id INT PRIMARY KEY,
--     account_type varchar(10)
-- );
--
-- CREATE TABLE account (
--     id INT PRIMARY KEY,
--     username varchar(15),
--     password varchar(30),
--     account_type foreign key references account_type(id)
-- );
INSERT INTO company_type (id, type) VALUES (1, 'VENDOR');
INSERT INTO company_type (id, type) VALUES (2, 'HR');
INSERT INTO event_type (id, type) VALUES (1, 'Health Talk');
INSERT INTO event_type (id, type) VALUES (2, 'Time Management');
INSERT INTO event_type (id, type) VALUES (3, 'Onsite Screening');
INSERT INTO role (id, code, name) VALUES (1, 'hr-admin', 'HR Admin Role');
INSERT INTO role (id, code, name) VALUES (2, 'vendor-admin', 'Vendor Admin Role');
INSERT INTO company (id, company_name, company_type_id) VALUES (1, 'HR Company', 2);
INSERT INTO company (id, company_name, company_type_id) VALUES (2, 'Vendor Company', 1);
INSERT INTO company (id, company_name, company_type_id) VALUES (3, 'Vendor Company 2', 1);
INSERT INTO account (username, password, company_id) VALUES ('hr1', 'pwd', 1);
INSERT INTO account (username, password, company_id) VALUES ('v1', 'pwd', 2);
INSERT INTO account (username, password, company_id) VALUES ('v2', 'pwd', 2);
INSERT INTO event (company_id, proposal_date_1, proposal_date_2, proposal_date_3, approved_date, status, proposal_location, event_type_id, remarks, date_created, created_by) VALUES (1, '2022-12-31', '2022-11-01', '2022-10-02', '2024-01-01', 'PENDING', 'Texas', 1, '', '2022-05-01', 1);

