DROP TABLE IF EXISTS Blocks;

CREATE TABLE Blocks (
    ID INTEGER NOT NULL
                    CONSTRAINT BlocksID_pk
                    PRIMARY KEY autoincrement,
    UUID TEXT NOT NULL,
    Blocks INTEGER NOT NULL
                    DEFAULT 0
);