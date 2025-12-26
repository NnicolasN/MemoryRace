Application Java
================

Préparation de l’environnement
------------------------------

Pour préparer l’environnement d’exécution de l’application, il faut :
1. Lancer MariaDB.
   Les instructions sont disponibles dans le dossier Database
2. Éxecuter le script install.sh avec le dossier où l’on souhaite stocker les photos
   Par exemple : ```$ ./install.sh /tmp/photos```

Compilation
-----------

Pour compiler l’application, il possible d’utiliser un IDE correctement configuré (avec le bon classpath contenant le jar du driver mariadb).
Il est aussi possible d’utiliser maven :
1. Installer maven
2. ```$ mvn clean```
3. ```$ mvn package -Dmaven.test.skip```
4. L’application se trouve dans le dossier target sous le nom standalone-*.jar
   ```$ java -jar target/standalone-*.jar /tmp/photos/```
   Il ne faut pas oublier le slash final.

