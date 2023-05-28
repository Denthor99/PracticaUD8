package practica89;

import java.sql.*;
import java.util.Scanner;

public class BaseDatos {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    /* public static void main(String[] args) {
        System.out.println("Gestión Base de Datos");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese c (crear) / b (borrar): ");
        String entrada = scanner.nextLine();

        if (entrada.equals("c")) {
            crearBD();
        } else if (entrada.equals("b")) {
            borrarBD();
        } else {
            System.out.println("Entrada no válida");
        }
    } */

     static void crearBD() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la base de datos: ");
        String nombreBD = scanner.nextLine();
        System.out.print("Ingrese el nombre de la tabla: ");
        String nombreTabla = scanner.nextLine();
        scanner.close();

        if (nombreTabla.isEmpty()) {
            crearBaseDeDatos(nombreBD);
        } else
            crearBaseDeDatos(nombreBD);
            crearTabla(nombreBD, nombreTabla);
    }

    static void borrarBD() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la base de datos: ");
        String nombreBD = scanner.nextLine();
        System.out.print("Ingrese el nombre de la tabla: ");
        String nombreTabla = scanner.nextLine();
        scanner.close();

        if (nombreTabla.isEmpty()) {
            borrarBaseDeDatos(nombreBD);
        } else
            borrarTabla(nombreBD, nombreTabla);
    }

    static void crearBaseDeDatos(String nombreBD) {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "CREATE DATABASE IF NOT EXISTS " + nombreBD;
            PreparedStatement stm = con.prepareStatement(query);
            stm.executeUpdate();
            stm.close();
            con.close();

            System.out.println("Base de datos creada con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void crearTabla(String nombreBD, String nombreTabla) {
        try {
            Connection con = DriverManager.getConnection(DB_URL + nombreBD, DB_USER, DB_PASSWORD);
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("CREATE TABLE IF NOT EXISTS ").append(nombreTabla).append(" (")
                    .append("id INT PRIMARY KEY AUTO_INCREMENT, ")
                    .append("alumno VARCHAR(35) NOT NULL, ")
                    .append("intervenciones TINYINT DEFAULT 0, ")
                    .append("ultima_intervencion DATE)");

            String query = queryBuilder.toString();
            PreparedStatement stm = con.prepareStatement(query);
            stm.executeUpdate();
            stm.close();
            con.close();

            System.out.println("Tabla creada con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void borrarBaseDeDatos(String nombreBD) {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();
            String query = "DROP DATABASE IF EXISTS " + nombreBD;
            stm.executeUpdate(query);
            stm.close();
            con.close();

            System.out.println("La base de datos se ha eliminado con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void borrarTabla(String nombreBD, String nombreTabla) {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();
            String query = "DROP TABLE IF EXISTS " + nombreBD + "." + nombreTabla;
            stm.executeUpdate(query);
            stm.close();
            con.close();

            System.out.println("La tabla se ha eliminado con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}