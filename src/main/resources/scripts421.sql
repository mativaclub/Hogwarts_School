CREATE TABLE student (
    id INTEGER PRIMARY KEY,
    name VARCHAR(200) UNIQUE NOT NULL,
    age INTEGER DEFAULT 20 CHECK ( age >= 16 )
);

CREATE TABLE faculty(
    id INTEGER PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    color VARCHAR(200) NOT NULL
);

CREATE UNIQUE INDEX name_color
    ON faculty(name,color);