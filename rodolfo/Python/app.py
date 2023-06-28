import mysql.connector
import random
import time

conn = mysql.connector.connect(host='18.119.29.121', user='usuario2', passwd='Pass1234*', db='compras')
cursor = conn.cursor()

# Creación de la tabla (si no existe)
cursor.execute('''CREATE TABLE IF NOT EXISTS compras
                  (id INTEGER PRIMARY KEY AUTO_INCREMENT, descripcion TEXT, cant INTEGER)''')

# Función para insertar compras de manera cíclica y aleatoria
def insertar_compras(compras, inserciones):
    for _ in range(inserciones):
        compra = random.choice(compras)
        descripcion = compra[0]
        cant = random.randint(1, 12)  # Generar número aleatorio entre 1 y 12
        cursor.execute('INSERT INTO compras (descripcion, cant) VALUES (%s, %s)', (descripcion, cant))
    conn.commit()

# Función para mostrar todos los compras
def mostrar_compras():
    cursor.execute('SELECT * FROM compras')
    compras = cursor.fetchall()
    for compra in compras:
        print(f'ID: {compra[0]}, Descripcion: {compra[1]}, Cantidad: {compra[2]}')

compras_aleatorias = [('Laptop', 10), ('Cargador', 5), ('Mouse', 8), ('Teclado', 3), ('Monitor', 2), ('Cable', 1), ('Router', 6), ('Tarjeta', 9), ('Audifono', 7), ('Bocina', 5), ('Disco', 5), ('Memoria', 5), ('Compu', 5), ('SSD', 3),]
inserciones = 3

while True:
    print("======= MENÚ =======")
    print("1. Insertar compras")
    print("2. Mostrar compras")
    print("3. Salir")

    opcion = int(input("Selecciona una opción: "))

    if opcion == 1:
        while True:
            insertar_compras(compras_aleatorias, inserciones)
            print("Compras insertadas correctamente.")
            time.sleep(5)  # Pausar la ejecución durante 5 segundos
    elif opcion == 2:
        mostrar_compras()
    elif opcion == 3:
        break
    else:
        print("Opción inválida. Por favor, selecciona una opción válida.")

# Cierre de la conexión a la base de datos
conn.close()

