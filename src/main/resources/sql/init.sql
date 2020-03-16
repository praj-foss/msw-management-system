-- Locations
CREATE TABLE IF NOT EXISTS LocationType(
    TypeID      INTEGER PRIMARY KEY AUTO_INCREMENT,
    TypeName    VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS Location(
    LocationID  INTEGER PRIMARY KEY AUTO_INCREMENT,
    Name        VARCHAR NOT NULL,
    TypeID      INTEGER,
    FOREIGN KEY (TypeID) REFERENCES LocationType(TypeID)
);

-- Vehicles
CREATE TABLE IF NOT EXISTS VehicleModel(
    ModelID     INTEGER PRIMARY KEY AUTO_INCREMENT,
    ModelName   VARCHAR NOT NULL,
    Capacity    INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS VehicleStatus(
    StatusID     INTEGER PRIMARY KEY AUTO_INCREMENT,
    StatusValue  VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS Vehicle(
    VehicleID   INTEGER PRIMARY KEY AUTO_INCREMENT,
    ModelID     INTEGER NOT NULL,
    LocationID  INTEGER,
    StatusID    INTEGER,
    FOREIGN KEY (ModelID) REFERENCES VehicleModel(ModelID),
    FOREIGN KEY (LocationID) REFERENCES Location(LocationID),
    FOREIGN KEY (StatusID) REFERENCES VehicleStatus(StatusID)
);

-- Waste collection
CREATE TABLE IF NOT EXISTS Collection(
    CollectionID    INTEGER PRIMARY KEY AUTO_INCREMENT,
    CollectionTime  TIMESTAMP NOT NULL,
    LocationID      INTEGER NOT NULL,
    VehicleID       INTEGER NOT NULL,
    Amount          DECIMAL(5,2) DEFAULT 0,
    FOREIGN KEY (VehicleID) REFERENCES Vehicle(VehicleID),
    FOREIGN KEY (LocationID) REFERENCES Location(LocationID)
);


INSERT INTO LocationType VALUES
	(1, 'Residential'),
	(2, 'Industrial'),
	(3, 'Medical'),
	(4, 'Agricultural'),
	(5, 'Public'),
	(6, 'Transfer station'),
	(7, 'Dumpyard');

INSERT INTO Location VALUES
	(1,  'Chaitanya Nagar',        1),
	(2,  'Auto Nagar',             1),
	(3,  'Akkireddypalem',         1),
	(4,  'Simhachalam',            1),
	(5,  'Vizag Steel Plant',      2),
	(6,  'Zinc Smelter',           2),
	(7,  'NTPC Simhadri',          2),
	(8,  'Vizag Dumpyard',         7),
	(9,  'Gajuwaka TS',            6),
	(10, 'Kurmanapalem TS',        6),
	(11, 'RK Hospital',            3),
	(12, 'Sujatha Hospital',       3),
	(13, 'Appolo Hospital',        3),
	(14, 'Scindia Colony',         1),
	(15, 'Aganampudi Farmlands',   4),
	(16, 'Anakapalli Farms',       4),
	(17, 'Mohini Cinema',          5),
	(18, 'Sree Kanya Theatre',     5),
	(19, 'Sri Chaitanya College',  5),
	(20, 'Narayana School',        5),
	(21, 'Main Road',              5),
	(22, 'Narasinga Rao Street',   1),
	(23, 'Sector 1',               1),
	(24, 'Sector 2',               1),
	(25, 'Sector 3',               1),
	(26, 'Sector 4',               1),
	(27, 'Telephone Exchange Str', 1),
	(28, 'Gandhinagaram',          1);

INSERT INTO VehicleModel VALUES
	(1, 'SKS 500',       8),
	(2, 'Leyland 1618', 18);

INSERT INTO VehicleStatus VALUES
	(1, 'ON DUTY'),
	(2, 'STOPPED');

INSERT INTO Vehicle VALUES
	(1, 1, 9,  2),
	(2, 2, 9,  2),
	(3, 1, 9,  2),
	(4, 1, 9,  2),
	(5, 1, 10, 2),
	(6, 2, 10, 2);
