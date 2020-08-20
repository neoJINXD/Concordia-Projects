-- CREATE DATABASE IF NOT EXISTS assignment1_testing;

USE kekw;

CREATE TABLE IF NOT EXISTS patient (
    pid INT PRIMARY KEY AUTO_INCREMENT,
    pname VARCHAR(255),
    age INT,
    city VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS doctor ( 
    did INT PRIMARY KEY AUTO_INCREMENT,
    dname VARCHAR(255),
    city VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS specialization (
    did INT NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    start_date_of_specialization DATE,

    FOREIGN KEY (did) REFERENCES doctor(did) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS clinic (
    cid INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cname VARCHAR(255),
    city VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS works_in (
    did INT NOT NULL, 
    cid INT NOT NULL, 
    hours_per_week INT NOT NULL,
    FOREIGN KEY (did) REFERENCES doctor(did) ON DELETE CASCADE,
    FOREIGN KEY (cid) REFERENCES clinic(cid) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS consults (
    pid INT NOT NULL,
    did INT NOT NULL,
    cid INT NOT NULL, 
    date DATE,
    illness VARCHAR(255) NOT NULL,
    FOREIGN KEY (pid) REFERENCES patient(pid) ON DELETE CASCADE,
    FOREIGN KEY (did) REFERENCES doctor(did) ON DELETE CASCADE,
    FOREIGN KEY (cid) REFERENCES clinic(cid) ON DELETE CASCADE
);

