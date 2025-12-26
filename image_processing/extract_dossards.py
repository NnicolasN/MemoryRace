import cv2
import pytesseract
import numpy as np
import re
import os


image_path = r"C:\images\nico.jpg"

def extract_number(image_path):
    if not os.path.exists(image_path):
        print(f"Erreur : L'image '{image_path}' n'existe pas.")
        return None

    image = cv2.imread(image_path)

    if image is None:
        print("Erreur : Impossible de charger l'image.")
        return None

    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    blurred = cv2.GaussianBlur(gray, (5, 5), 0)
    edges = cv2.Canny(blurred, 50, 150)
    contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    contours = sorted(contours, key=cv2.contourArea, reverse=True)
    number_region = None

    for contour in contours:
        x, y, w, h = cv2.boundingRect(contour)
        aspect_ratio = w / h  
        if 2.0 > aspect_ratio > 0.5 and 50 < w < 500:  
            number_region = (x, y, w, h)
            break

    if number_region:
        x, y, w, h = number_region
        number_zone = gray[y:y+h, x:x+w]  
        _, processed = cv2.threshold(number_zone, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
        text = pytesseract.image_to_string(processed, config='--psm 7 --oem 3')

        numbers = re.findall(r'\d+', text)

     
        return numbers[0] if numbers else None
    else:
        print("Aucun numéro détecté.")
        return None

result = extract_number(image_path)

if result:
    print(f"Numero extrait : {result}")
else:
    print("Aucun numéro détecté.")
