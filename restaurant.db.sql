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
CREATE TABLE IF NOT EXISTS "wishlist" (
	"id"	INTEGER,
	"userId"	INTEGER,
	"menuitem"	TEXT,
	"price"	NUMERIC,
	FOREIGN KEY("userId") REFERENCES "users"("id"),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "reservations" (
	"id"	INTEGER,
	"date"	TEXT,
	"time"	NUMERIC,
	"guests"	INTEGER,
	"userId"	INTEGER,
	"guest_name"	TEXT,
	"guest_surname"	TEXT,
	FOREIGN KEY("userId") REFERENCES "users"("id"),
	PRIMARY KEY("id")
);
INSERT INTO "admins" VALUES (1,'Almina','Brulic','alminabr@hotmail.com','admin','admin');
INSERT INTO "users" VALUES (1,'Neko','Nekic','nnekic@gmail.com','nekic','nekic','Male');
INSERT INTO "users" VALUES (2,'Ana','Anic','anic@gmail.com','ana','ana','Female');
INSERT INTO "menuitem" VALUES (1,'Lentil bolognese',20.0,'yes','no');
INSERT INTO "menuitem" VALUES (2,'Southwestern Pasta Salad',15.95,'yes','yes');
INSERT INTO "menuitem" VALUES (3,'Garlic Pasta',10.0,'no','no');
INSERT INTO "menuitem" VALUES (4,'Vegan Pasta',25.0,'yes','yes');
INSERT INTO "menuitem" VALUES (5,'Spaghetti',7.5,'no','yes');
INSERT INTO "menuitem" VALUES (6,'cevapi',5.0,'no','no');
INSERT INTO "wishlist" VALUES (1,2,'Spaghetti',7.5);
INSERT INTO "wishlist" VALUES (2,1,'Vegan Pasta',25);
INSERT INTO "wishlist" VALUES (4,1,'cevapi',5);
INSERT INTO "reservations" VALUES (1,'2022-09-09','11:00 AM',4,2,'Ana','Anic');
COMMIT;
