CREATE TABLE car
(
    id    INTEGER PRIMARY KEY,
    type  VARCHAR(200) NOT NULL,
    model VARCHAR(200) NOT NULL,
    price INTEGER DEFAULT 0
);

CREATE TABLE person
(
    id          INTEGER PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    age         INTEGER CHECK (age >= 18),
    has_license BOOLEAN DEFAULT FALSE,
    car_id INTEGER REFERENCES car(id)
);

