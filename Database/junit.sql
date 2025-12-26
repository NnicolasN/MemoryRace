/* Ajout des 30 photos de test  séparés entre 3 courses imaginaires */

INSERT INTO Photos (date,id_course,latitude,longitude,file_path)
VALUES ("2000-01-01", 1, 2.1, 1.2, "photo.jpg");
/* Ajout de courses dont viennent les photos (courses iventées) */

INSERT INTO Courses (id_course, date, lieu, nom)
    VALUES (1, '200-01-01', 'Paris', 'Course 1'), (2, '2020-12-12', 'Nice', 'Course 2');

/* Ajout des détections, i.e les liens photos-dossards */

INSERT INTO Detections (id_photo,no_dossard)
    VALUES
    (1, 1);

INSERT INTO Organisateurs (id_organisateur, email, password)
VALUES (0, "test@junit.org", "junit");

INSERT INTO Organise (id_organisateur, id_course)
VALUES (1, 1);
