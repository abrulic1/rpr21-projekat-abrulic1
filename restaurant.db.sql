BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "admins" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "users" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"username"	TEXT,
	"password"	TEXT,
	"gender"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "menuitem" (
	"id"	INTEGER,
	"name"	TEXT,
	"price"	REAL,
	"vegan"	TEXT,
	"vegetarian"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "reservations" (
	"id"	INTEGER,
	"date"	TEXT,
	"time"	TEXT,
	"guests"	INTEGER,
	PRIMARY KEY("id")
);
INSERT INTO "admins" VALUES (1,'Almina','Brulic','alminabr@hotmail.com','admin','admin');
INSERT INTO "users" VALUES (1,'Neko','Nekiccccccc','neko@gmail.com','nnekic','nnekic','Male');
INSERT INTO "users" VALUES (2,'ok','sd','dsds','dsdsd','sddsdsdsdssd','Female');
INSERT INTO "menuitem" VALUES (1,'spagetthi',10.0,'no','yes');
INSERT INTO "reservations" VALUES (1,'09/09/2022','09:00',3);
COMMIT;
