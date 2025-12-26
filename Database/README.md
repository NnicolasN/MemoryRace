# Quel SGBD ?

MariaDB, qui est la suite libre de MySQL après le rachat de SUN par Oracle.
En Bonus, Adminer permet d’avoir une interface Web pour gérer la base de données.

# Faire tourner le SGBD en local :

1. Installer docker et docker-compose
2. Ne pas modifier les mots de passes dans le fichier `docker-compose.yml` et lancer la commande :`$ sudo docker compose up`
5. Créer une base de donnée `db`
6. Importer init_db.sql
7. Exéctuer


# Premiers pas dans le SGBD

Vous avez 2 scripts à votre disposition en plus du init_db.sql :

1. reset_db.sql : remet à 0 la base de donnée : supprime les tables puis les réinitialisent vides.
2. micro_dataset.sql : ajoute les données relatives aux 30 photos de tests de la micro db initiale
