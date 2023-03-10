CREATE TABLE Person
(
    id    INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    age   INT,
    email VARCHAR(100) UNIQUE
);

INSERT INTO Person (name, age, email)
VALUES ('Tom', 25, 'tom@mail.ru');
INSERT INTO Person (name, age, email)
VALUES ('Bob', 51, 'bob@mail.ru');
INSERT INTO Person (name, age, email)
VALUES ('Nik', 35, 'nik@mail.ru');

ALTER TABLE person
    ADD COLUMN created_at  TIMESTAMP,
    ADD COLUMN updated_at  TIMESTAMP,
    ADD COLUMN created_who VARCHAR;

