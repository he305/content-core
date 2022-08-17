CREATE TABLE account (
    id varchar(36) NOT NULL,
    password varchar(255),
    role int4,
    username varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE content_account (
    id varchar(36) NOT NULL,
    name varchar(255) NOT NULL,
    platform int4 NOT NULL,
    status int4 NOT NULL,
    use_counter int4 NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE content_account_entry (
    id varchar(36) NOT NULL,
    alias varchar(255),
    content_account_id varchar(36),
    watching_list_entry_id varchar(36) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE content_account_notification (
    content_account_entry_data_id varchar(36) NOT NULL,
    id varchar(36)
);

CREATE TABLE notification (
    id varchar(36) NOT NULL,
    content_account_id varchar(36),
    message varchar(255),
    message_time timestamp NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE stream (
    id varchar(36) NOT NULL,
    ended_at timestamp,
    is_live boolean NOT NULL,
    max_viewers int4 NOT NULL,
    started_at timestamp NOT NULL,
    stream_channel_id varchar(36) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE stream_channel (
    id varchar(36) NOT NULL,
    content_account_id varchar(36),
    platform int4,
    status int4,
    PRIMARY KEY (id)
);

CREATE TABLE stream_data (
    id varchar(36) NOT NULL,
    game_name varchar(255) NOT NULL,
    stream_data_time timestamp NOT NULL,
    title varchar(255) NOT NULL,
    viewer_count int4 NOT NULL,
    stream_id varchar(36) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE stream_list (
    id varchar(36) NOT NULL,
    member_id varchar(36),
    PRIMARY KEY (id)
);

CREATE TABLE stream_list_stream_channel_id (
    stream_list_jpa_id varchar(36) NOT NULL,
    id varchar(36)
);

CREATE TABLE watching_list (
    id varchar(36) NOT NULL,
    member_id varchar(36) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE watching_list_entry (
    id varchar(36) NOT NULL,
    name varchar(255) NOT NULL,
    watching_list_id varchar(36) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS watching_list
    ADD CONSTRAINT UK_6uct2ufu285t6uo4auj55o9np UNIQUE (member_id);

ALTER TABLE IF EXISTS content_account_entry
    ADD CONSTRAINT FK2fthfg6nrhuop1bikt45qt5sg FOREIGN KEY (watching_list_entry_id) REFERENCES watching_list_entry;

ALTER TABLE IF EXISTS content_account_notification
    ADD CONSTRAINT FKl0ofuslvlsxo84my8ywh8pnai FOREIGN KEY (content_account_entry_data_id) REFERENCES content_account_entry;

ALTER TABLE IF EXISTS stream
    ADD CONSTRAINT FKtrmmq8vt1jxhgvcc2v0k6quko FOREIGN KEY (stream_channel_id) REFERENCES stream_channel;

ALTER TABLE IF EXISTS stream_data
    ADD CONSTRAINT FK59i8rrrccajdokwrn8atgjhrh FOREIGN KEY (stream_id) REFERENCES stream;

ALTER TABLE IF EXISTS stream_list_stream_channel_id
    ADD CONSTRAINT FK9f4hea1ku9eptqnc87h9t1vx1 FOREIGN KEY (stream_list_jpa_id) REFERENCES stream_list;

ALTER TABLE IF EXISTS watching_list_entry
    ADD CONSTRAINT FKplaorseq258v83e9u25xcf6fl FOREIGN KEY (watching_list_id) REFERENCES watching_list;

