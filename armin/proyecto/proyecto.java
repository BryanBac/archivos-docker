import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class proyecto {
    private static final String URL = "jdbc:mysql://18.119.29.121:3306/clientes";
    private static final String USUARIO = "usuario1";
    private static final String CONTRASEÑA = "Pass1234*";

    public static void main(String[] args) {
        Connection conexion = null;
        Statement sentencia = null;

        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            sentencia = conexion.createStatement();

            Scanner scanner = new Scanner(System.in);
            int opcion;

            do {
                mostrarMenu();
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        insertarDatos(sentencia);
                        break;
                    case 2:
                        leerDatos(sentencia);
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Inténtalo nuevamente.");
                        break;
                }
            } while (opcion != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("=== MENU ===");
        System.out.println("1. Insertar datos");
        System.out.println("2. Leer datos");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private static void insertarDatos(Statement sentencia) throws SQLException {
        Random random = new Random();
        String[] nombres = { "Juan", "María", "Carlos", "Ana", "Pedro", "Laura", "Luis", "Marta", "Alejandro", "Sofía",
                "Diego", "Valentina", "Andrés", "Camila", "Javier" };
        String[] apellidos = { "Pérez", "Gómez", "López", "García", "Rodríguez", "Fernández", "Torres", "Sánchez",
                "Ramírez", "González", "Vargas", "Martínez", "Hernández", "Silva", "Ruiz" };

        try {
            while (true) {
                int id = random.nextInt(1000) + 1;
                String nombre = nombres[random.nextInt(nombres.length)];
                String apellido = apellidos[random.nextInt(apellidos.length)];
                
                String consulta = "INSERT INTO clientes (id_clientes, nombre, apellido) VALUES (" + id + ", '" + nombre
                        + "', '" + apellido + "')";
                sentencia.executeUpdate(consulta);

                System.out.println("Datos insertados correctamente.");

                TimeUnit.SECONDS.sleep(4);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void leerDatos(Statement sentencia) throws SQLException {
        try {
            while (true) {
                String consulta = "SELECT * FROM clientes";
                ResultSet resultado = sentencia.executeQuery(consulta);

                System.out.println("=== DATOS ===");
                while (resultado.next()) {
                    int id = resultado.getInt("id_clientes");
                    String nombre = resultado.getString("nombre");
                    String apellido = resultado.getString("apellido");

                    System.out.println("ID: " + id + ", Nombre: " + nombre + ", Apellido: " + apellido);
                }

                TimeUnit.SECONDS.sleep(4);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
