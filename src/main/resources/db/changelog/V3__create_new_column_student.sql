--liquibase formatted sql

--changeset maria:4

ALTER TABLE student ADD date_birthday DATE;

