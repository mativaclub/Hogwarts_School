--liquibase formatted sql

--changeset nvoxland:2

CREATE INDEX name_student ON student (
                                      name
    );
