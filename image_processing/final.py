import sys
import mysql.connector
import google.generativeai as genai
import PIL.Image
import ast
import google.api_core.exceptions

# Ce script prend en argument plusieurs entiers qui correspondent à des photo_id
# il trouve les numéros sur ces photos
# il les injecte dans la base de données


if len(sys.argv) < 1:
    print("Vous devez au moins préciser le root_path")
    sys.exit(1)

root_path = sys.argv[1]

# Pour chaque argument (photo_id)
for i in sys.argv[2:]:

    # Récupère l'argument
    try:
        photo_id = int(i)
    except ValueError:
        print("L'argument doit être un entier.")
        sys.exit(1)

    # SCRIPT 1 : Récupération du file_path dans la BDD

    conn = mysql.connector.connect(
        host="localhost",
        user="root",
        password="password",
        database="db"
    )
    cursor = conn.cursor()
    query = "SELECT file_path FROM Photos WHERE id_photo = %s;"
    cursor.execute(query, (photo_id,))
    result = cursor.fetchone()
    cursor.close()
    conn.close()

    if not result:
        print(f"Aucun résultat trouvé pour l'id {photo_id}")
        sys.exit(1)

    image_path = root_path + result[0]
    print(f"Image trouvée : {image_path}")

    # SCRIPT 2 : Appel Gemini

    genai.configure(api_key="")
    img = PIL.Image.open(image_path)
    model = genai.GenerativeModel("gemini-1.5-flash")
    prompt = (
        "cette image montre des coureurs lors d'un marathon. "
        "Chaque coureur porte un brassard qui contient un numéro. "
        "Génére moi en sortie UNIQUEMENT une liste contenant tous les numéros des joueurs sur l'image. "
        "par exemple : [1234,2344]"
        "Je ne veux que les caractères suivants : 1 2 3 4 5 6 7 8 9 0."
        "Je ne veux pas de lettres, que des nombres entiers."
    )
    try:
        response = model.generate_content([prompt, img])
        print("Réponse Gemini :", response.text)

    except google.api_core.exceptions.ResourceExhausted as e:
        print("Limite de quota atteinte :", e.message)
        sys.exit(1)

    except google.api_core.exceptions.PermissionDenied as e:
        print("Accès refusé (clé API invalide ou restrictions) :", e.message)
        sys.exit(1)

    except Exception as e:
        print("Erreur inattendue :", str(e))
        sys.exit(1)

    # SCRIPT 3 : Analyse + insertion en BDD
    
    try:
        dossards_list = ast.literal_eval(response.text.strip())
        if not (isinstance(dossards_list, list) and all(isinstance(d, int) for d in dossards_list)):
            raise ValueError("Format inattendu de la liste")
    except Exception as e:
        print("Erreur lors de l’analyse de la réponse Gemini :", e)
        sys.exit(1)

    def insert_dossards(id_photo, dossards_list):
        conn = mysql.connector.connect(
            host="localhost",
            user="root",
            password="password",
            database="db"
        )
        cursor = conn.cursor()
        insert_query = """
        INSERT INTO Detections (id_photo, no_dossard)
        VALUES (%s, %s);
        """
        try:
            for dossard in dossards_list:
                cursor.execute(insert_query, (id_photo, dossard))
            conn.commit()
            print(f"{len(dossards_list)} dossard(s) inséré(s) pour la photo {id_photo}.")
        except mysql.connector.Error as err:
            print("Erreur SQL :", err)
        finally:
            cursor.close()
            conn.close()

    # Appel de la fonction
    insert_dossards(photo_id, dossards_list)
