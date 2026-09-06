# Java client

The Swing application runners and organizers use. It reads and writes a MariaDB
database, stores the uploaded photos on the file system, and calls the Python
detection script after each upload.

## Preparing the environment

1. Start MariaDB. Instructions are in the `Database` folder.
2. Run `install.sh` with the folder where photos should be stored:

   ```bash
   ./install.sh /tmp/photos
   ```

   The script creates the folder, sets up a Python virtual environment inside
   it and installs the dependencies listed in
   `image_processing/requirements.txt`, then copies the detection script there.

3. Export your Gemini API key before starting the application, otherwise photo
   upload fails when the detection script runs:

   ```bash
   export GEMINI_API_KEY=your_key_here
   ```

## Building

With Maven:

```bash
mvn clean package -DskipTests
java -jar target/standalone-jar-with-dependencies.jar /tmp/photos/
```

The argument is the folder passed to `install.sh`.

You can also build from an IDE. In that case add `mariadb-java-client-3.5.2.jar`
to the classpath by hand. That jar is committed next to the sources so that the
project opens in Eclipse without Maven, the Maven build downloads its own copy.

## Configuration

The database connection is read from the environment, with the defaults of a
local docker compose setup:

| Variable      | Default     |
| ------------- | ----------- |
| `DB_HOST`     | `127.0.0.1` |
| `DB_PORT`     | `3306`      |
| `DB_USER`     | `root`      |
| `DB_PASSWORD` | `password`  |

## Tests

The tests in `src/test/java` run against a real database. Before running them,
load `Database/junit.sql` into the `db` database and copy
`src/test/resources/photo.jpg` into `/tmp/junit-photos/`.

```bash
mvn test
```

## Code organization

`connectionmodel` holds the abstract types: a `ConnectionHandler` that executes
requests, the `Request` and `Response` hierarchies, and the data classes that
travel between them. Nothing in that package knows how the data is stored.

`connectionlocal` implements those types for a local MariaDB instance. Each
request knows how to serialize itself into SQL, and each response knows how to
read a `ResultSet`.

`interfaceswing` holds the windows. They all extend `FenetreBase`, which draws
the logo on the left and leaves the right panel to the subclass.
