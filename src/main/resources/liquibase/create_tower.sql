--changeset bogatan:2

create table bogatan.towers (
  id  BIGINT primary key,
  user_id varchar(255) not null,
  add_date DATETIME not null DEFAULT sysdate,
  value BIGINT not null DEFAULT 1
);


ALTER TABLE bogatan.towers
ADD CONSTRAINT fk_towers_users
FOREIGN KEY (user_id) REFERENCES bogatan.users(id);


CREATE SEQUENCE BOGATAN.SEQUENCE