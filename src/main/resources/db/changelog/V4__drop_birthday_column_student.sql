--liquibase formatted sql

--changeset maria:5

ALTER TABLE student DROP COLUMN date_birthday;