#!/usr/bin/env bash
# Prépare le dossier de travail de l'application : environnement Python
# virtuel, dépendances, et copie du script de détection des dossards.
#
# Usage : ./install.sh [dossier]   (défaut : /tmp/photos)

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_ROOT_DIR="${1:-/tmp/photos}"

if ! command -v python3 > /dev/null; then
    echo "python3 est introuvable. Installez-le avant de relancer ce script." >&2
    exit 1
fi

echo "=> Création du dossier $APP_ROOT_DIR"
mkdir -p "$APP_ROOT_DIR"

echo "=> Installation du venv python"
python3 -m venv "$APP_ROOT_DIR/venv"
"$APP_ROOT_DIR/venv/bin/pip" install --quiet --upgrade pip
"$APP_ROOT_DIR/venv/bin/pip" install --quiet -r "$SCRIPT_DIR/../image_processing/requirements.txt"

echo "=> Copie du script de détection"
cp "$SCRIPT_DIR/../image_processing/dossards_extraction.py" "$APP_ROOT_DIR/dossards_extraction.py"

echo
echo "Installation terminée."
echo "Chemin à passer en argument de l'application : \"$APP_ROOT_DIR\""
echo
echo "Pensez à exporter votre clé Gemini avant de lancer l'application :"
echo "    export GEMINI_API_KEY=<votre clé>"
