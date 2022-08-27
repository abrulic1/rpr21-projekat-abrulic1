BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "users" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"nickname"	TEXT,
	"email"	TEXT,
	"number"	TEXT,
	"password"	TEXT,
	"dob"	TEXT,
	"gender"	TEXT,
	PRIMARY KEY("id")
);
INSERT INTO "users" VALUES (1,'Almina','Brulic','abrulic1','abrulic1@etf.unsa.ba','0603360000','Projekatabrulic1',NULL,NULL);
COMMIT;
