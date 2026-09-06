"""Prototype : premier essai d'extraction des dossards avec Gemini.

Ce script n'est pas utilisé par l'application, il est gardé comme trace des
essais faits pendant le projet. Le script de production est
image_processing/dossards_extraction.py.

Usage : GEMINI_API_KEY=... python3 extract_API_LLM.py image.png
"""

import os
import sys

import PIL.Image
import google.generativeai as genai


genai.configure(api_key=os.environ["GEMINI_API_KEY"])


img = PIL.Image.open(sys.argv[1] if len(sys.argv) > 1 else "3.png")


model = genai.GenerativeModel("gemini-1.5-flash")


prompt = "cette image montre des coureurs lors d'un mararthon. Chaque coureur porte un brassard qui contient un numéro. Génére moi en sortie UNIQUEMENT (pas de texte) une liste contenant tous les numéros des joueurs sur l'image. par exemple : [1234,2344]"
response = model.generate_content([prompt, img])

print(response.text)
