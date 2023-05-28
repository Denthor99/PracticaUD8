package practica89;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Operaciones {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String DRIVER = "org.mariadb.jdbc.Driver";

    public static void main(String[] args) {

    }

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

    static void alumnoUltimo() {
        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            ResultSet rs = stm.executeQuery("SELECT ultima_intervencion FROM instituto.daw1 WHERE ultima_intervencion IS NOT NULL ORDER BY ultima_intervencion DESC LIMIT 1");

            if (rs.next()) {
                Date ultimaIntervencion = rs.getDate("ultima_intervencion");
                ArrayList<String> alumnos = new ArrayList<>();

                rs = stm.executeQuery("SELECT alumno FROM instituto.daw1 WHERE ultima_intervencion = '" + ultimaIntervencion + "'");

                while (rs.next()) {
                    String alumno = rs.getString("alumno");
                    alumnos.add(alumno);
                }

                if (!alumnos.isEmpty()) {
                    if (alumnos.size() > 1) {
                        Random random = new Random();
                        int indiceAleatorio = random.nextInt(alumnos.size());
                        String alumnoSeleccionado = alumnos.get(indiceAleatorio);
                        System.out.println("Alumno seleccionado: " + alumnoSeleccionado);
                    } else {
                        String alumnoSeleccionado = alumnos.get(0);
                        System.out.println("Alumno seleccionado: " + alumnoSeleccionado);
                    }
                } else {
                    System.out.println("No hay alumnos con la misma fecha de intervención");
                }
            } else {
                System.out.println("No hay alumnos con intervenciones");
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }

    static void alumnoInfo() {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Mostrar la información de un alumno dado

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }

    static void alumnoDarAlta() {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Dar de alta, baja y modificar alumnos

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }

    static void alumnoDarBaja() {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try {
            // Enlazar con el driver
            Class.forName(DRIVER);

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();

            // Dar de alta, baja y modificar alumnos

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }

    static void alumnoModificar(String nombreBD, String nombreTabla, String alumno, String nuevoNombre, Integer nuevasIntervenciones) {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            con.setAutoCommit(false);
            if (nuevasIntervenciones > 0) {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE " + nombreBD + "." + nombreTabla + " SET alumno = ?, intervenciones = ?, ultima_intervencion = NOW() WHERE alumno = ?")) {
                    pstmt.setString(1, nuevoNombre);
                    pstmt.setInt(2, nuevasIntervenciones);
                    pstmt.setString(3, alumno);
                    int filasActualizadas = pstmt.executeUpdate();
                    if (filasActualizadas > 0) {
                        con.commit();
                        System.out.println("El alumno " + alumno + " ha sido modificado correctamente");
                    } else {
                        con.rollback();
                        System.out.println("No se encontró ningún alumno con el nombre " + alumno);
                    }
                }
            } else {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE " + nombreBD + "." + nombreTabla + " SET alumno = ?, intervenciones = ?, ultima_intervencion = NULL WHERE alumno = ?")) {
                    pstmt.setString(1, nuevoNombre);
                    pstmt.setInt(2, nuevasIntervenciones);
                    pstmt.setString(3, alumno);
                    int filasActualizadas = pstmt.executeUpdate();
                    if (filasActualizadas > 0) {
                        con.commit();
                        System.out.println("El alumno " + alumno + " ha sido modificado correctamente");
                    } else {
                        con.rollback();
                        System.out.println("No se encontró ningún alumno con el nombre " + alumno);
                    }
                }
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }
}