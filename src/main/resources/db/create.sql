SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS tracks (
 id int PRIMARY KEY auto_increment,
 title VARCHAR,
 genre VARCHAR,
 length FLOAT(2)
 );