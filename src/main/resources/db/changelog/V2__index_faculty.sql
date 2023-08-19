--liquibase formatted sql

--changeset maria:3

CREATE INDEX index_color_name ON faculty (
                                          name, color
    );