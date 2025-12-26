#!/usr/bin/env bash

APP_ROOT_DIR="/tmp/photos"

if [ $# -ge 1 ]; then
    APP_ROOT_DIR=$1
fi

install_app () {
    echo "=> Installation du venv python"

    python3 -m venv $APP_ROOT_DIR/venv
    source $APP_ROOT_DIR/venv/bin/activate
    pip3 install google-generativeai
    pip3 install pillow
    pip3 install mysql-connector

    echo "=> Déplacement du script python"

    cp ../image_processing/final.py $APP_ROOT_DIR/dossards_extraction.py

    echo "=> Chemin à passer en argument de l’application : \"$APP_ROOT_DIR\""
    echo "=> Le chemin doit se terminer par un slash /"
    echo "(par défaut : /tmp/photos/)"
}

install_app
