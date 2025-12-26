DROP TABLE Courses;
DROP TABLE Detections;
DROP TABLE Organisateurs;
DROP TABLE Photos;
DROP TABLE Organise;

CREATE TABLE Organisateurs (
	id_organisateur INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	email VARCHAR(255),
	password VARCHAR(255)
);	

CREATE TABLE Detections (
	id_detection INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	id_photo INT NOT NULL,
	no_dossard INT NOT NULL
);

CREATE TABLE Organise (
	id_organisateur INT NOT NULL,
	id_course INT NOT NULL
);

CREATE TABLE Photos (
	id_photo INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	date DATE,
	id_course INT NOT NULL,
	latitude FLOAT,
	longitude FLOAT,
	file_path VARCHAR(255)
);

CREATE TABLE Courses (
	id_course INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	date DATE,
	lieu VARCHAR(255),
	nom VARCHAR(255)
);
