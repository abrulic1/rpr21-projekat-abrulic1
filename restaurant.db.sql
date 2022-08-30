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
CREATE TABLE IF NOT EXISTS "reservations" (
	"id"	INTEGER,
	"code"	TEXT,
	"time"	TEXT,
	"number of guests"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "menuitem" (
	"id"	INTEGER,
	"name"	TEXT,
	"price"	REAL,
	"vegan"	INTEGER,
	"vegetarian"	INTEGER,
	PRIMARY KEY("id")
);
INSERT INTO "admins" VALUES (1,'Almina','Brulic','alminabr@hotmail.com','admin','admin');
INSERT INTO "users" VALUES (1,'Neko','Nekic','neko@gmail.com','nnekic','nnekic','Musko');
INSERT INTO "reservations" VALUES (1,'ddd3222d','13.4.2022.08:20',3);
INSERT INTO "menuitem" VALUES (1,'spaghetti',10.0,0,1);
COMMIT;
