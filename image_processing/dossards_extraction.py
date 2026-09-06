"""Extraction des numéros de dossard d'une ou plusieurs photos.

Le script prend en argument le dossier racine des photos, puis une liste
d'identifiants de photos (photo_id). Pour chaque identifiant, il récupère le
chemin du fichier dans la base, envoie l'image à Gemini, et insère les
dossards détectés dans la table Detections.

Usage :
    python3 dossards_extraction.py /tmp/photos/ 1 2 3

Configuration par variables d'environnement :
    GEMINI_API_KEY  clé de l'API Google Generative AI (obligatoire)
    GEMINI_MODEL    modèle utilisé (défaut : gemini-1.5-flash)
    DB_HOST         hôte MariaDB (défaut : localhost)
    DB_PORT         port MariaDB (défaut : 3306)
    DB_USER         utilisateur MariaDB (défaut : root)
    DB_PASSWORD     mot de passe MariaDB (défaut : password)
    DB_NAME         nom de la base (défaut : db)

Le script est appelé par l'application Java, qui transmet son propre
environnement au sous-processus : les variables doivent donc être définies
avant de lancer l'application.
"""

import ast
import os
import sys

import google.api_core.exceptions
import google.generativeai as genai
import mysql.connector
import PIL.Image

PROMPT = (
    "cette image montre des coureurs lors d'un marathon. "
    "Chaque coureur porte un brassard qui contient un numéro. "
    "Génére moi en sortie UNIQUEMENT une liste contenant tous les numéros des joueurs sur l'image. "
    "par exemple : [1234,2344]. Je ne veux que des entiers dans la liste, pas de lettres ou d’autre symboles."
    "Si tu ne peux pas répondre, renvoie juste une liste vide. Si il y a autre chose qu’un entier dans la liste, supprime cet élément."
    "Je ne veux pas de texte en sortie, juste une liste."
    "Réponds en texte brut, sans mise en forme. N’utilise pas de markdown."
)


def db_connection():
    """Ouvre une connexion à la base, configurée par l'environnement."""
    return mysql.connector.connect(
        host=os.environ.get("DB_HOST", "localhost"),
        port=int(os.environ.get("DB_PORT", "3306")),
        user=os.environ.get("DB_USER", "root"),
        password=os.environ.get("DB_PASSWORD", "password"),
        database=os.environ.get("DB_NAME", "db"),
    )


def file_path_of(photo_id):
    """Retourne le chemin relatif de la photo, ou None si elle n'existe pas."""
    conn = db_connection()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT file_path FROM Photos WHERE id_photo = %s;", (photo_id,))
        result = cursor.fetchone()
    finally:
        cursor.close()
        conn.close()
    return result[0] if result else None


def detect_dossards(model, image_path):
    """Envoie l'image à Gemini et retourne la liste des dossards détectés."""
    img = PIL.Image.open(image_path)
    response = model.generate_content([PROMPT, img])
    print("Réponse Gemini :", response.text)

    dossards = ast.literal_eval(response.text.strip())
    if not (isinstance(dossards, list) and all(isinstance(d, int) for d in dossards)):
        raise ValueError("Format inattendu de la liste")
    return dossards


def insert_dossards(photo_id, dossards):
    """Enregistre les dossards détectés pour une photo."""
    conn = db_connection()
    cursor = conn.cursor()
    try:
        for dossard in dossards:
            cursor.execute(
                "INSERT INTO Detections (id_photo, no_dossard) VALUES (%s, %s);",
                (photo_id, dossard),
            )
        conn.commit()
        print(f"{len(dossards)} dossard(s) inséré(s) pour la photo {photo_id}.")
    except mysql.connector.Error as err:
        print("Erreur SQL :", err)
    finally:
        cursor.close()
        conn.close()


def main():
    if len(sys.argv) < 2:
        print("Usage : dossards_extraction.py <root_path> [photo_id ...]")
        return 1

    api_key = os.environ.get("GEMINI_API_KEY")
    if not api_key:
        print("La variable d’environnement GEMINI_API_KEY n’est pas définie.")
        return 1

    root_path = sys.argv[1]
    genai.configure(api_key=api_key)
    model = genai.GenerativeModel(os.environ.get("GEMINI_MODEL", "gemini-1.5-flash"))

    for arg in sys.argv[2:]:
        try:
            photo_id = int(arg)
        except ValueError:
            print("L'argument doit être un entier.")
            return 1

        file_path = file_path_of(photo_id)
        if file_path is None:
            print(f"Aucun résultat trouvé pour l'id {photo_id}")
            return 1

        image_path = os.path.join(root_path, file_path)
        print(f"Image trouvée : {image_path}")

        try:
            dossards = detect_dossards(model, image_path)
        except google.api_core.exceptions.ResourceExhausted as e:
            print("Limite de quota atteinte :", e.message)
            return 1
        except google.api_core.exceptions.PermissionDenied as e:
            print("Accès refusé (clé API invalide ou restrictions) :", e.message)
            return 1
        except (ValueError, SyntaxError) as e:
            # Réponse illisible : on passe à la photo suivante plutôt que de
            # tout interrompre.
            print("Erreur lors de l’analyse de la réponse Gemini :", e)
            continue
        except Exception as e:
            print("Erreur inattendue :", str(e))
            return 1

        insert_dossards(photo_id, dossards)

    return 0


if __name__ == "__main__":
    sys.exit(main())
