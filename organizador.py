import os
import shutil

# Ruta de la carpeta que queremos limpiar (Descargas)
# Nota: 'frainel' debe ser tu nombre de usuario en la Mac
ruta_descargas = os.path.expanduser("~/Downloads")

# Diccionario para organizar por tipos de archivo
extensiones = {
    ".pdf": "Documentos_PDF",
    ".docx": "Documentos_Word",
    ".jpg": "Imagenes",
    ".png": "Imagenes",
    ".zip": "Archivos_Comprimidos",
    ".py": "Scripts_Python",
    ".java": "Codigo_Java"
}

def organizar():
    for archivo in os.listdir(ruta_descargas):
        nombre, ext = os.path.splitext(archivo)
        
        if ext.lower() in extensiones:
            carpeta_destino = os.path.join(ruta_descargas, extensiones[ext.lower()])
            
            # Crear la carpeta si no existe
            if not os.path.exists(carpeta_destino):
                os.makedirs(carpeta_destino)
            
            # Mover el archivo
            shutil.move(os.path.join(ruta_descargas, archivo), 
                        os.path.join(carpeta_destino, archivo))
            print(f"Movido: {archivo} -> {extensiones[ext.lower()]}")

if __name__ == "__main__":
    print("Iniciando limpieza de FrainelDev...")
    organizar()
    print("¡Carpeta organizada!")
