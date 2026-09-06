# Bib number detection

`dossards_extraction.py` is the script the Java application runs after every
photo upload. It takes the photo folder and a list of photo identifiers, looks
up the file name of each one in the database, sends the image to Google Gemini,
and writes the bib numbers it reads back into the `Detections` table.

```bash
GEMINI_API_KEY=your_key_here python3 dossards_extraction.py /tmp/photos/ 1 2 3
```

The application never runs this copy of the script. `Application/install.sh`
copies it into the photo folder, next to the virtual environment that holds its
dependencies, and the application runs it from there.

## Configuration

Everything comes from the environment, so no key or password ends up in the
source:

| Variable         | Default            |
| ---------------- | ------------------ |
| `GEMINI_API_KEY` | required           |
| `GEMINI_MODEL`   | `gemini-1.5-flash` |
| `DB_HOST`        | `localhost`        |
| `DB_PORT`        | `3306`             |
| `DB_USER`        | `root`             |
| `DB_PASSWORD`    | `password`         |
| `DB_NAME`        | `db`               |

The Java application starts the script as a subprocess and passes its own
environment along, which is why these variables have to be exported before
launching the application.

## Dependencies

```bash
pip install -r requirements.txt
```

## Prototypes

`prototypes/` keeps the two earlier attempts. `extract_dossards.py` isolates the
bib with OpenCV and reads it with Tesseract, which works on clean close up shots
and fails on most real race photos. `extract_API_LLM.py` is the first test of
the Gemini API, before it was wired to the database. Neither is used at runtime.
