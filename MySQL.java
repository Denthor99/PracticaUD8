package org.ieslosremedios.daw1.prog.ut8y9;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MySQL {
    public static void main(String[] args) {
        //alumnoMasMenosParticipativo();
        //alumnosDebajoMedia();
        //alumnosIntervencionesValor();
        alumnoUltimo();
        //intervencionesACero();
        //añadirIntervencion("Juanma Sab");
        //quitarIntervencion("Juanma sab");
        //darBajaAlumno("John");
        //darDeAltaAlumno("Juanma Saborido");
        //modificarAlumno("Danie","Dani",0);
        //seleccionarAlumnoAleatorio();
    }

    static void alumnoMasMenosParticipativo() {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try {
            // Enlazar con el driver
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, "root", "");
            Statement stm = con.createStatement();

            // El alumno con más intervenciones
            String auxQuery1 = "SELECT MAX(intervenciones) AS maximo FROM instituto.daw1";
            ResultSet auxResul = stm.executeQuery(auxQuery1);
            int maxIntervenciones = 0;
            if (auxResul.next()) {
                maxIntervenciones = auxResul.getInt("maximo");
            }
            auxQuery1 = "SELECT alumno FROM instituto.daw1 WHERE intervenciones = " + maxIntervenciones;
            auxResul = stm.executeQuery(auxQuery1);
            List<String> alumnos = new ArrayList<>();
            while (auxResul.next()) {
                alumnos.add(auxResul.getString("alumno"));
            }

            // Seleccionar un alumno aleatorio de la lista
            Random random = new Random();
            int index = random.nextInt(alumnos.size());
            String alumnoSeleccionado = alumnos.get(index);

            System.out.println("Alumno con más intervenciones: " + alumnoSeleccionado);

            // Calcular el valor de los que tienen menos participaciones
            String auxQuery2 = "SELECT MIN(intervenciones) AS minimo FROM instituto.daw1";
            ResultSet auxResul2 = stm.executeQuery(auxQuery2);
            int minIntervenciones = 0;
            if (auxResul2.next()) {
                minIntervenciones = auxResul2.getInt("minimo");
            }
            auxQuery2 = "SELECT alumno FROM instituto.daw1 WHERE intervenciones = " + minIntervenciones;
            auxResul2 = stm.executeQuery(auxQuery2);
            List<String> alumnosMin = new ArrayList<>();
            while (auxResul2.next()) {
                alumnosMin.add(auxResul2.getString("alumno"));
            }

            // Seleccionar un alumno aleatorio de la lista
            Random random2 = new Random();
            int index2 = random2.nextInt(alumnosMin.size());
            String alumnoSeleccionado2 = alumnosMin.get(index2);

            System.out.println("Alumno con menos intervenciones: " + alumnoSeleccionado2);

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }


    static void alumnosDebajoMedia() {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try {
            // Enlazar con el driver
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, "root", "");
            Statement stm = con.createStatement();

            // Alumnos que están por debajo de la media de intervenciones por alumno
            String query1 = "SELECT alumno FROM instituto.daw1 WHERE intervenciones<(SELECT AVG(intervenciones) FROM instituto.daw1);";
            ResultSet resultado1 = stm.executeQuery(query1);

            System.out.println("Alumnos con intervenciones por debajo de la media: ");
            while (resultado1.next()) {
                System.out.println("- " + resultado1.getString("alumno"));
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }

    static void alumnosIntervencionesValor() {
        String url = "jdbc:mariadb://localhost:3306/instituto";

        String parametro = "2";

        try {
            // Enlazar con el driver
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, "root", "");
            Statement stm = con.createStatement();

            // Alumnos que tienen un número de participaciones superior, inferior o igual a un valor indicado
            String query1 = "SELECT alumno FROM instituto.daw1 WHERE intervenciones=" + parametro;
            ResultSet resultado1 = stm.executeQuery(query1);

            System.out.println("Alumnos con intervenciones iguales a " + parametro + ":");
            while (resultado1.next()) {
                System.out.println("- " + resultado1.getString("alumno"));
            }

            String query2 = "SELECT alumno FROM instituto.daw1 WHERE intervenciones>" + parametro;
            ResultSet resultado2 = stm.executeQuery(query2);

            System.out.println("Alumnos con intervenciones por encima de " + parametro + ":");
            while (resultado2.next()) {
                System.out.println("- " + resultado2.getString("alumno"));
            }

            String query3 = "SELECT alumno FROM instituto.daw1 WHERE intervenciones<" + parametro;
            ResultSet resultado3 = stm.executeQuery(query3);

            System.out.println("Alumnos con intervenciones por debajo de " + parametro + ":");
            while (resultado3.next()) {
                System.out.println("- " + resultado3.getString("alumno"));
            }

            stm.close();
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }


        public static void alumnoUltimo() {
            String url = "jdbc:mariadb://localhost:3306/instituto";
            try (Connection con = DriverManager.getConnection(url, "root", "");
                 Statement stmt = con.createStatement()) {

                ResultSet rs = stmt.executeQuery("SELECT ultima_intervencion FROM instituto.daw1 WHERE ultima_intervencion IS NOT NULL ORDER BY ultima_intervencion DESC LIMIT 1");

                if (rs.next()) {
                    Date ultimaIntervencion = rs.getDate("ultima_intervencion");
                    ArrayList<String> alumnos = new ArrayList<>();

                    rs = stmt.executeQuery("SELECT alumno FROM instituto.daw1 WHERE ultima_intervencion = '" + ultimaIntervencion + "'");

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

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    void alumnoInfo() {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try {
            // Enlazar con el driver
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, "root", "");
            Statement stm = con.createStatement();

            // Mostrar la información de un alumno dado

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }
    static void intervencionesACero(){
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,"root","");
            Statement stm= con.createStatement();
            String byeIntervenciones="UPDATE instituto.daw1 SET intervenciones=0,ultima_intervencion=NULL";
            System.out.println("Se han puesto a 0 las intervenciones de los "+stm.executeUpdate(byeIntervenciones)+" alumnos");
            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: "+e.getMessage());
        }
    }
    static void añadirIntervencion(String alumno){
        String url="jdbc:mariadb://localhost:3306/instituto";
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con=DriverManager.getConnection(url,"root","");
            Statement stm=con.createStatement();
            String adicionIntervencion="UPDATE instituto.daw1 SET intervenciones=intervenciones +1,ultima_intervención=NOW() WHERE alumno LIKE('%"+alumno+"%')";
            stm.executeQuery(adicionIntervencion);
            System.out.println("Se ha añadido una intervención al alumno "+alumno);

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: "+e.getMessage());
        }
    }
    /***/
    static void quitarIntervencion(String alumno){
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, "root", "");
            Statement stm = con.createStatement();

            // Obtener intervenciones y fecha anterior
            String obtenerDatosAnteriores = "SELECT intervenciones, ultima_intervencion FROM instituto.daw1 WHERE alumno LIKE '%" + alumno + "%'";
            ResultSet resultado = stm.executeQuery(obtenerDatosAnteriores);
            resultado.next();
            int intervenciones = resultado.getInt("intervenciones");
            String fechaAnterior = resultado.getString("ultima_intervencion");

            // Actualizar intervenciones y fecha
            String quitarIntervencion = "UPDATE instituto.daw1 SET intervenciones = intervenciones - 1";
            if (intervenciones > 1) {
                quitarIntervencion += ", ultima_intervencion = '" + fechaAnterior + "'";
            } else {
                quitarIntervencion += ", ultima_intervencion = NULL";
            }
            quitarIntervencion += " WHERE alumno LIKE '%" + alumno + "%'";

            stm.executeUpdate(quitarIntervencion);
            System.out.println("Se ha quitado una intervención al alumno " + alumno);

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }
    /**
     * Con este método eliminamos a un alumno de la base de datos. Usamos PreparedStatement, ya que nos permite crear consultas
     * parametrizadas
     * */
    static void darBajaAlumno(String alumno) {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try (Connection con = DriverManager.getConnection(url, "root", "");
             PreparedStatement pstmt = con.prepareStatement("DELETE FROM instituto.daw1 WHERE alumno = ?")) {

            pstmt.setString(1, alumno);
            pstmt.executeUpdate();
            System.out.println("El alumno "+alumno + " ha sido dado de baja");

        } catch (SQLException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }
    /**
     * Haciendo uso de PreparedStatement, daremos de alta a un usuario en la base de datos. Solo se añadirá el nombre pues
     * un alumno nuevo no tendrá muchas intervenciones que digamos.
     * */
    static void darDeAltaAlumno(String alumno) {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try (Connection con = DriverManager.getConnection(url, "root", "");
             PreparedStatement pstmt = con.prepareStatement("INSERT INTO instituto.daw1 VALUES(null,?,0,null)")) {

            pstmt.setString(1, alumno);
            pstmt.executeUpdate();
            System.out.println("El alumno "+alumno + " ha sido dado de alta");

        } catch (SQLException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }

    /**
     * Este metodo nos permite modificar un alumno concreto. Podemos modificar tanto el nombre como las intervenciones. Además,
     * si en caso de que se quiera poner en las intervenciones a cero a ese usuario, la fecha pasará a ser null
     * */
    static void modificarAlumno(String alumno, String nuevoNombre, Integer nuevasIntervenciones) {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try (Connection con = DriverManager.getConnection(url, "root", "")) {
            con.setAutoCommit(false);
            if (nuevasIntervenciones > 0) {
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE instituto.daw1 SET alumno = ?, intervenciones = ?, ultima_intervencion = NOW() WHERE alumno = ?")) {
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
                try (PreparedStatement pstmt = con.prepareStatement("UPDATE instituto.daw1 SET alumno = ?, intervenciones = ?, ultima_intervencion = NULL WHERE alumno = ?")) {
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
    /**
     * Este métoodo escoge a un alumno aleatorio mediante una consulta a la base de datos. Se añaden todos los alumno
     * a un ArrayList usando un iterador.
     * */
    static void seleccionarAlumnoAleatorio() {
        String url = "jdbc:mariadb://localhost:3306/instituto";
        try (Connection con = DriverManager.getConnection(url, "root", "");
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT alumno FROM instituto.daw1");
            ArrayList<String> alumnos = new ArrayList<>();

            while (rs.next()) {
                String alumno = rs.getString("alumno");
                alumnos.add(alumno);
            }
            if (alumnos.isEmpty()) {
                System.out.println("No hay alumnos en la base de datos.");
                return;
            }
            Random random = new Random();
            int indiceAleatorio = random.nextInt(alumnos.size());
            String alumnoSeleccionado = alumnos.get(indiceAleatorio);
            System.out.println("Alumno seleccionado: " + alumnoSeleccionado);
        } catch (SQLException e) {
            throw new RuntimeException(e);
    }
}
}