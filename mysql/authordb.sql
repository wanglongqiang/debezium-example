GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT  ON *.* TO 'debezium' IDENTIFIED BY 'dbz';

CREATE DATABASE authordb;
USE authordb;

CREATE TABLE author (
    id INT NOT NULL,
    name VARCHAR(32) NOT NULL,
    age INT,
    PRIMARY KEY (id)
);

INSERT INTO author (id, name, age) VALUES (1, 'one', 10);
INSERT INTO author (id, name, age) VALUES (2, 'two', 20);
INSERT INTO author (id, name, age) VALUES (3, 'three', 30);