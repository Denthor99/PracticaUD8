package org.ieslosremedios.daw1.prog.ut8y9;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Operaciones {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DRIVER = "org.mariadb.jdbc.Driver";


    static String alumnoMasParticipativo(String nombreBD, String nombreTabla) {

        String alumnoSeleccionado = null;

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Alumno con más intervenciones
            String auxQuery = "SELECT MAX(intervenciones) AS maximo FROM " + nombreBD + "." + nombreTabla + ";";
            ResultSet auxResul = stm.executeQuery(auxQuery);
            int maxIntervenciones = 0;
            if (auxResul.next()) {
                maxIntervenciones = auxResul.getInt("maximo");
            }
            auxQuery = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones = " + maxIntervenciones + ";";
            auxResul = stm.executeQuery(auxQuery);
            List<String> alumnos = new ArrayList<>();
            while (auxResul.next()) {
                alumnos.add(auxResul.getString("alumno"));
            }

            // Seleccionar un alumno aleatorio de la lista
            Random random = new Random();
            int index = random.nextInt(alumnos.size());
            alumnoSeleccionado = alumnos.get(index);

            System.out.println("Alumno con más intervenciones: " + alumnoSeleccionado);

            stm.close();
            con.close();

        } catch(SQLException | ClassNotFoundException e){
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return alumnoSeleccionado;
    }

    static String alumnoMenosParticipativo(String nombreBD, String nombreTabla) {

        String alumnoSeleccionado = null;

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String auxQuery = "SELECT MIN(intervenciones) AS minimo FROM " + nombreBD + "." + nombreTabla + ";";
            ResultSet auxResul = stm.executeQuery(auxQuery);
            int minIntervenciones = 0;
            if (auxResul.next()) {
                minIntervenciones = auxResul.getInt("minimo");
            }
            auxQuery = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones = " + minIntervenciones + ";";
            auxResul = stm.executeQuery(auxQuery);
            List<String> alumnosMin = new ArrayList<>();
            while (auxResul.next()) {
                alumnosMin.add(auxResul.getString("alumno"));
            }

            // Seleccionar un alumno aleatorio de la lista
            Random random2 = new Random();
            int index2 = random2.nextInt(alumnosMin.size());
            alumnoSeleccionado = alumnosMin.get(index2);

            System.out.println("Alumno con menos intervenciones: " + alumnoSeleccionado);

            stm.close();
            con.close();

        } catch(SQLException | ClassNotFoundException e){
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return alumnoSeleccionado;
    }

    static List<String> alumnosDebajoMedia(String nombreBD, String nombreTabla) {

        List<String> alumnosSeleccionados = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Alumnos que están por debajo de la media de intervenciones por alumno
            String query = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones<(SELECT AVG(intervenciones) FROM " + nombreBD + "." + nombreTabla + ");";
            ResultSet resultado = stm.executeQuery(query);

            System.out.println("Alumnos con intervenciones por debajo de la media: ");
            while (resultado.next()) {
                String alumno = resultado.getString("alumno");
                alumnosSeleccionados.add(alumno);
                System.out.println("- " + alumno);            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return alumnosSeleccionados;
    }

    static List<String> alumnosIntervencionesValor(String nombreBD, String nombreTabla, String numIntervenciones) {

        List<String> alumnosSeleccionados = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Alumnos que tienen un número de participaciones superior, inferior o igual a un valor indicado
            String query1 = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones=" + numIntervenciones + ";";
            ResultSet resultado1 = stm.executeQuery(query1);

            System.out.println("Alumnos con intervenciones iguales a " + numIntervenciones + ":");
            while (resultado1.next()) {
                String alumno = resultado1.getString("alumno");
                alumnosSeleccionados.add(alumno);
                System.out.println("- " + alumno);
            }

            alumnosSeleccionados.add("] [");

            String query2 = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones>" + numIntervenciones + ";";
            ResultSet resultado2 = stm.executeQuery(query2);

            System.out.println("Alumnos con intervenciones por encima de " + numIntervenciones + ":");
            while (resultado2.next()) {
                String alumno = resultado2.getString("alumno");
                alumnosSeleccionados.add(alumno);
                System.out.println("- " + alumno);
            }

            alumnosSeleccionados.add("] [");

            String query3 = "SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE intervenciones<" + numIntervenciones + ";";
            ResultSet resultado3 = stm.executeQuery(query3);

            System.out.println("Alumnos con intervenciones por debajo de " + numIntervenciones + ":");
            while (resultado3.next()) {
                String alumno = resultado3.getString("alumno");
                alumnosSeleccionados.add(alumno);
                System.out.println("- " + alumno);
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
        return alumnosSeleccionados;
    }

    static List<String> alumnoUltimo(String nombreBD, String nombreTabla) {
        List<String> alumnosSeleccionados = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery("SELECT ultima_intervencion FROM " + nombreBD + "." + nombreTabla + " ORDER BY ultima_intervencion DESC LIMIT 1");

            if (rs.next()) {
                Date ultimaIntervencion = rs.getDate("ultima_intervencion");
                ArrayList<String> alumnos = new ArrayList<>();

                rs = stm.executeQuery("SELECT alumno FROM " + nombreBD + "." + nombreTabla + " WHERE ultima_intervencion = '" + ultimaIntervencion + "'");

                while (rs.next()) {
                    String alumno = rs.getString("alumno");
                    alumnos.add(alumno);
                }

                if (!alumnos.isEmpty()) {
                    if (alumnos.size() > 1) {
                        Random random = new Random();
                        int indiceAleatorio = random.nextInt(alumnos.size());
                        String alumnoSeleccionado = alumnos.get(indiceAleatorio);
                        alumnosSeleccionados.add("Alumno seleccionado: " + alumnoSeleccionado);
                    } else {
                        String alumnoSeleccionado = alumnos.get(0);
                        alumnosSeleccionados.add("Alumno seleccionado: " + alumnoSeleccionado);
                    }
                } else {
                    alumnosSeleccionados.add("No hay alumnos con la misma fecha de intervención");
                }
            } else {
                alumnosSeleccionados.add("No hay alumnos con intervenciones");
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }

        return alumnosSeleccionados;
    }




    static List<String> alumnoInfo(String nombreBD, String nombreTabla, String alumno) {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        List<String> infoAlumno = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Mostrar la información de un alumno dado
            String query = "SELECT * FROM " + nombreBD + "." + nombreTabla + " WHERE alumno = '" + alumno + "';";
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                String nombre = rs.getString("alumno");
                int intervenciones = rs.getInt("intervenciones");
                String fecha=rs.getString("ultima_intervencion");
                // ... Obtener los demás campos que deseas mostrar

                // Agregar la información del alumno a la lista
                infoAlumno.add("Nombre: " + nombre);
                infoAlumno.add("Intervenciones actuales: " + intervenciones);
                infoAlumno.add("Ultima Intervención: " + fecha);

            }

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }

        return infoAlumno;
    }




    public static String alumnoDarAlta(String nombreBD, String nombreTabla, String alumno) {
        String resultado = "";

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String query = "INSERT INTO " + nombreBD + "." + nombreTabla + " (alumno, ultima_intervencion) VALUES (?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, alumno);
                pstmt.setString(2,"0000-00-00");
                int filasInsertadas = pstmt.executeUpdate();
                if (filasInsertadas > 0) {
                    resultado = "El alumno " + alumno + " ha sido dado de alta correctamente";
                } else {
                    resultado = "No se pudo dar de alta al alumno " + alumno;
                }
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }

        return resultado;
    }


    public static String alumnoDarBaja(String nombreBD, String nombreTabla, String alumno) {
        String resultado = "";

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String query = "DELETE FROM " + nombreBD + "." + nombreTabla + " WHERE alumno = ?";

            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, alumno);
                int filasEliminadas = pstmt.executeUpdate();
                if (filasEliminadas > 0) {
                    resultado = "El alumno " + alumno + " ha sido dado de baja correctamente";
                } else {
                    resultado = "No se encontró ningún alumno con el nombre " + alumno;
                }
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }

        return resultado;
    }


    public static List<String> alumnoModificar(String nombreBD, String nombreTabla, String alumno, String nuevoNombre, Integer nuevasIntervenciones) {
        List<String> resultados = new ArrayList<>();

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            con.setAutoCommit(false);

            String query;
            if (nuevasIntervenciones > 0) {
                query = "UPDATE " + nombreBD + "." + nombreTabla + " SET alumno = ?, intervenciones = ?, ultima_intervencion = NOW() WHERE alumno = ?";
            } else {
                query = "UPDATE " + nombreBD + "." + nombreTabla + " SET alumno = ?, intervenciones = ?, ultima_intervencion = NULL WHERE alumno = ?";
            }

            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, nuevoNombre);
                pstmt.setInt(2, nuevasIntervenciones);
                pstmt.setString(3, alumno);
                int filasActualizadas = pstmt.executeUpdate();
                if (filasActualizadas > 0) {
                    con.commit();
                    resultados.add("El alumno " + alumno + " ha sido modificado correctamente");
                    resultados.add("Nuevo nombre: " + nuevoNombre);
                    resultados.add("Nuevas intervenciones: " + nuevasIntervenciones);
                } else {
                    con.rollback();
                    resultados.add("No se encontró ningún alumno con el nombre " + alumno);
                }
            }

            con.setAutoCommit(true);
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }

        return resultados;
    }
    // Metodos tema anterior

    // Elegir alumno de forma aleatoria
    public static String seleccionarAlumnoAleatorio(String nombreBD, String nombreTabla) {
        String resultado = "";

        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String query = "SELECT alumno FROM " + nombreBD + "." + nombreTabla;
            ResultSet rs = stm.executeQuery(query);
            ArrayList<String> alumnos = new ArrayList<>();

            while (rs.next()) {
                String alumno = rs.getString("alumno");
                alumnos.add(alumno);
            }

            if (alumnos.isEmpty()) {
                resultado = "No hay alumnos en la base de datos.";
            } else {
                Random random = new Random();
                int indiceAleatorio = random.nextInt(alumnos.size());
                String alumnoSeleccionado = alumnos.get(indiceAleatorio);
                resultado = "Alumno seleccionado: " + alumnoSeleccionado;
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }
    // Poner intervenciones a cero
    public static String reiniciarIntervenciones(String nombreBD, String nombreTabla) {
        String reinicioIntervenciones="";
        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            String updateQuery = "UPDATE " + nombreBD + "." + nombreTabla + " SET intervenciones = 0, ultima_intervencion = '0000-00-00'";
            int filasActualizadas = stm.executeUpdate(updateQuery);

            String reinicio="Se han reiniciado las intervenciones en " + filasActualizadas + " filas.";

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return reinicioIntervenciones;
    }
}