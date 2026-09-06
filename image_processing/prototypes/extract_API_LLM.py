import PIL.Image
import google.generativeai as genai


genai.configure(api_key="")


img = PIL.Image.open("3.png")


model = genai.GenerativeModel("gemini-1.5-flash")


prompt = "cette image montre des coureurs lors d'un mararthon. Chaque coureur porte un brassard qui contient un numéro. Génére moi en sortie UNIQUEMENT (pas de texte) une liste contenant tous les numéros des joueurs sur l'image. par exemple : [1234,2344]"
response = model.generate_content([prompt, img])

print(response.text)
