BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "grad" (
	"grad_id"	INTEGER,
	"broj_stanovnika"	INTEGER,
	"drzava"	INTEGER,
	"naziv"	TEXT,
	PRIMARY KEY("grad_id"),
	FOREIGN KEY("drzava") REFERENCES "drzava"
);
INSERT INTO "grad" VALUES (1,2206488,1,'Pariz');
INSERT INTO "grad" VALUES (2,1899055,2,'Beč');
INSERT INTO "grad" VALUES (3,545500,3,'Manchester');
INSERT INTO "grad" VALUES (4,280200,2,'Graz');
INSERT INTO "grad" VALUES (5,8825000,3,'London');
CREATE TABLE IF NOT EXISTS "drzava" (
	"drzava_id"	INTEGER,
	"glavni_grad"	INTEGER,
	"naziv"	TEXT,
	PRIMARY KEY("drzava_id")
);
INSERT INTO drzava VALUES (1,1,'Francuska');
INSERT INTO drzava VALUES (2,2,'Austrija');
INSERT INTO drzava VALUES (3,5,'Velika Britanija');
COMMIT;
