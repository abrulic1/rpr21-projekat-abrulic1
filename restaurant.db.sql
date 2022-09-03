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
INSERT INTO "users" VALUES (2,'asas`','ded','ddwd','adfdf','jdsddsdsdsd','Male');
INSERT INTO "users" VALUES (3,'almina','almina','almina','admia','adminaadd','Female');
INSERT INTO "users" VALUES (4,'','','','','aaaaaaaaa','Female');
INSERT INTO "users" VALUES (5,'a','a','a','aa','aaaaaaaaaaa','Female');
INSERT INTO "users" VALUES (6,'fdf','fdf','fdf','dfd','qqqqqqqqqq','Female');
INSERT INTO "users" VALUES (7,'sasas','assasa','sasa','sas','assssssss','Female');
INSERT INTO "users" VALUES (8,'dsdsdd','sddsddsdsds','dsdd','ddsd','ffffffffffff','Female');
INSERT INTO "users" VALUES (9,'a','aa','a','aaa','aaaaaaaaa','Female');
INSERT INTO "users" VALUES (10,'asasas','assas','sass','asas','aaaaaaasasa','Female');
INSERT INTO "users" VALUES (11,'s','s','s','sss','qqqqqqqq','Female');
INSERT INTO "menuitem" VALUES (1,'spagetthi',10.0,'no','yes');
INSERT INTO "menuitem" VALUES (2,'Bodd',15.0,'no','yes');
INSERT INTO "menuitem" VALUES (3,'kokok',5.0,'no','no');
INSERT INTO "menuitem" VALUES (4,'z9',5.0,'no','no');
INSERT INTO "reservations" VALUES (1,'2022-09-23','03:00 PM',2);
INSERT INTO "reservations" VALUES (3,'2022-09-01','11:00 AM',2);
INSERT INTO "reservations" VALUES (6,'2022-09-02','11:00 AM',2);
INSERT INTO "reservations" VALUES (9,'2022-09-30','12:00 AM',2);
INSERT INTO "reservations" VALUES (10,'2022-09-30','11:00 AM',2);
INSERT INTO "reservations" VALUES (11,'2022-09-03','11:00 AM',2);
INSERT INTO "reservations" VALUES (12,'2022-09-04','11:00 AM',2);
INSERT INTO "reservations" VALUES (13,'2022-09-05','11:00 AM',2);
INSERT INTO "reservations" VALUES (14,'2022-09-06','11:00 AM',2);
INSERT INTO "reservations" VALUES (15,'2022-09-08','11:00 AM',2);
INSERT INTO "reservations" VALUES (16,'2022-09-17','11:00 AM',2);
INSERT INTO "reservations" VALUES (17,'2022-09-22','11:00 AM',2);
INSERT INTO "reservations" VALUES (18,'2022-09-24','11:00 AM',2);
INSERT INTO "reservations" VALUES (19,'2022-09-21','11:00 AM',2);
COMMIT;
