CREATE TABLE IF NOT EXISTS STATION
(
    id       BIGINT          AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)    NOT NULL UNIQUE,
    PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS LINE
(
    id       BIGINT          AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255)    NOT NULL UNIQUE,
    color    VARCHAR(20)     NOT NULL,
    PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS SECTION
(
    id         BIGINT          AUTO_INCREMENT NOT NULL,
    line_id    BIGINT          NOT NULL,
    up_bound   VARCHAR(255)    NOT NULL,
    down_bound VARCHAR(255)    NOT NULL,
    distance   INT             NOT NULL,
    PRIMARY KEY(ID)
);
