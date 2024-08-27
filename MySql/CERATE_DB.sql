CREATE DATABASE mydatabase;

USE mydatabase;

CREATE USER root@'localhost' IDENTIFIED BY '20121820';
GRANT ALL ON mydatabase.* TO root@'localhost';