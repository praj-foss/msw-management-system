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
