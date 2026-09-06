# Database

MariaDB, the free fork of MySQL created after Oracle bought Sun. Adminer comes
along in the compose file and gives a web interface to browse the data.

## Running it locally

1. Install Docker and Docker Compose.
2. Leave the passwords in `docker-compose.yml` alone, they match the defaults
   the application expects, and start the stack:

   ```bash
   docker compose up -d
   ```

The `db` database and the five tables are created on the first start, when the
data volume is still empty. Adminer listens on <http://localhost:8080>, server
`db`, user `root`, password `password`.

To start over from an empty database, remove the volume:

```bash
docker compose down -v
```

## Scripts

`init_db.sql` creates the tables. It is loaded automatically on the first
start, you only need to run it by hand if you are working against a MariaDB
instance you installed yourself.

`reset_db.sql` drops the tables and recreates them empty.

`micro_dataset.sql` fills the database with the 30 test photos of
`Micro_dataset/`, split between three fictional races. The bib numbers come
from the manual annotations of the dataset, so they can be used as a reference
to check what the detection script finds. Copy the images into the photo folder
of the application first:

```bash
cp Micro_dataset/images/*.png /tmp/photos/
docker compose exec -T db mariadb -uroot -ppassword db < micro_dataset.sql
```

`junit.sql` inserts the minimal fixture the JUnit tests expect: one photo, two
races, one detection and one organizer.

## Schema

Five tables:

- `Courses`, a race with a date, a place and a name.
- `Photos`, a photo attached to a race, with its capture date, its coordinates
  and the file name it has in the photo folder.
- `Detections`, one row per bib number found on a photo. A photo usually has
  several.
- `Organisateurs`, the accounts that can create races and upload photos.
- `Organise`, which organizer manages which race.

The photos themselves are not stored in the database. Only their file names
are, and the files live in the folder given to the application at startup.
