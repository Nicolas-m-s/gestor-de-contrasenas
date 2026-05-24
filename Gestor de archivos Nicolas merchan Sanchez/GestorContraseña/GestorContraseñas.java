import java.io.*;
import java.util.Scanner;

public class GestorContraseñas {
    private static final String ARCHIVO = "contraseñas.txt";
    private static final int DESPLAZAMIENTO = 3; // Cifrado César (+3)

    public static String cifrar(String texto) {
        StringBuilder cifrado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            cifrado.append((char) (c + DESPLAZAMIENTO));
        }
        return cifrado.toString();
    }

    public static String descifrar(String texto) {
        StringBuilder descifrado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            descifrado.append((char) (c - DESPLAZAMIENTO));
        }
        return descifrado.toString();
    }

    public static void guardarContraseña(String servicio, String contraseña) {
        try (FileWriter fw = new FileWriter(ARCHIVO, true)) {
            fw.write(servicio + ":" + cifrar(contraseña) + "\n");
            System.out.println("Contraseña guardada para " + servicio);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public static void mostrarContraseñas() {
        try (Scanner fileScanner = new Scanner(new File(ARCHIVO))) {
            System.out.println("\n--- Contraseñas Guardadas ---");
            while (fileScanner.hasNextLine()) {
                String[] linea = fileScanner.nextLine().split(":");
                System.out.println("Servicio: " + linea[0] + " | Contraseña: " + descifrar(linea[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No hay contraseñas guardadas aún.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Gestor de Contraseñas ---");
            System.out.println("1. Guardar contraseña");
            System.out.println("2. Mostrar contraseñas");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Servicio (ej: Gmail): ");
                    String servicio = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contraseña = scanner.nextLine();
                    guardarContraseña(servicio, contraseña);
                    break;
                case 2:
                    mostrarContraseñas();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);

        scanner.close();
    }
}