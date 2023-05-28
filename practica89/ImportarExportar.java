package org.ieslosremedios.daw1.prog.ut8y9;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class ImportarExportar {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static String NOMBRE_BD;
    private static String NOMBRE_TABLA;
    private static String RUTA;

/*
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("Importación/Exportación Base de Datos");
            System.out.println("1. Importar fichero XML");
            System.out.println("2. Exportar fichero XML");
            System.out.println("3. Importar fichero CSV");
            System.out.println("4. Exportar fichero CSV");
            System.out.println("5. Salir");
            System.out.println();
            System.out.print("Introduce una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    escaner();
                    importarXML(NOMBRE_BD, NOMBRE_TABLA, RUTA);
                    break;
                case 2:
                    escaner();
                    exportarXML(NOMBRE_BD, NOMBRE_TABLA, RUTA);
                    break;
                case 3:
                    escaner();
                    importarCSV(NOMBRE_BD, NOMBRE_TABLA, RUTA);
                    break;
                case 4:
                    escaner();
                    exportarCSV(NOMBRE_BD, NOMBRE_TABLA, RUTA);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    System.out.println();
            }
        } while (opcion != 5);
    }

    static void escaner() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la base de datos: ");
        NOMBRE_BD = scanner.nextLine();
        System.out.print("Ingrese el nombre de la tabla: ");
        NOMBRE_TABLA = scanner.nextLine();
        System.out.print("Ingrese la ruta del archivo: ");
        RUTA = scanner.nextLine();
        scanner.close();
    }
*/
    static void importarXML(String nombreBD, String nombreTabla, String ruta) {
        try {
            Connection con = DriverManager.getConnection(DB_URL + nombreBD, DB_USER, DB_PASSWORD);
            Document document = new SAXBuilder().build(new File(ruta));
            Element rootElement = document.getRootElement();

            for (Element rowElement : rootElement.getChildren("row")) {
                int id = Integer.parseInt(rowElement.getChildText("id"));
                String alumno = rowElement.getChildText("alumno");
                int intervenciones = Integer.parseInt(rowElement.getChildText("intervenciones"));
                String ultimaIntervencion = rowElement.getChildText("ultima_intervencion");

                String query = "INSERT INTO " + nombreTabla + " (id, alumno, intervenciones, ultima_intervencion) VALUES (?, ?, ?, ?)";
                PreparedStatement stm = con.prepareStatement(query);
                stm.setInt(1, id);
                stm.setString(2, alumno);
                stm.setInt(3, intervenciones);
                stm.setString(4, ultimaIntervencion);
                stm.executeUpdate();
                stm.close();
            }

            System.out.println("XML importado a la tabla con éxito");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void exportarXML(String nombreBD, String nombreTabla, String ruta) {
        try {
            Connection con = DriverManager.getConnection(DB_URL + nombreBD, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();
            String query = "SELECT * FROM " + nombreTabla;
            ResultSet resultSet = stm.executeQuery(query);

            Document document = new Document();
            Element rootElement = new Element("database");
            document.setRootElement(rootElement);

            while (resultSet.next()) {
                Element rowElement = new Element("row");
                rootElement.addContent(rowElement);

                Element idElement = new Element("id");
                idElement.setText(String.valueOf(resultSet.getInt("id")));
                rowElement.addContent(idElement);

                Element alumnoElement = new Element("alumno");
                alumnoElement.setText(resultSet.getString("alumno"));
                rowElement.addContent(alumnoElement);

                Element intervencionesElement = new Element("intervenciones");
                intervencionesElement.setText(String.valueOf(resultSet.getInt("intervenciones")));
                rowElement.addContent(intervencionesElement);

                Element ultimaIntervencionElement = new Element("ultima_intervencion");
                ultimaIntervencionElement.setText(resultSet.getString("ultima_intervencion"));
                rowElement.addContent(ultimaIntervencionElement);
            }

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, new FileWriter(ruta));

            System.out.println("Tabla exportada a XML con éxito");

            resultSet.close();
            stm.close();
            con.close();

        } catch (SQLException | IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void importarCSV(String nombreBD, String nombreTabla, String ruta) {
        try {
            Connection con = DriverManager.getConnection(DB_URL + nombreBD, DB_USER, DB_PASSWORD);
            String query = "INSERT INTO " + nombreTabla + " (id, alumno, intervenciones, ultima_intervencion) VALUES (?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(query);

            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String line;
            boolean primeraLinea = true;
            while ((line = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String alumno = data[1];
                int intervenciones = Integer.parseInt(data[2]);
                String ultimaIntervencion = data[3];

                stm.setInt(1, id);
                stm.setString(2, alumno);
                stm.setInt(3, intervenciones);
                stm.setString(4, ultimaIntervencion);
                stm.executeUpdate();
            }

            br.close();
            stm.close();
            con.close();

            System.out.println("CSV importado a la tabla con éxito");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void exportarCSV(String nombreBD, String nombreTabla, String ruta) {
        try {
            Connection con = DriverManager.getConnection(DB_URL + nombreBD, DB_USER, DB_PASSWORD);
            Statement stm = con.createStatement();
            String query = "SELECT * FROM " + nombreTabla;
            ResultSet rs = stm.executeQuery(query);

            FileWriter fileWriter = new FileWriter(ruta);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Escribe encabezados de columnas
            for (int i = 1; i <= columnCount; i++) {
                printWriter.print(rsmd.getColumnName(i));
                if (i < columnCount) {
                    printWriter.print(",");
                }
            }
            printWriter.println();

            // Escribe datos de filas
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    printWriter.print(rs.getString(i));
                    if (i < columnCount) {
                        printWriter.print(",");
                    }
                }
                printWriter.println();
            }

            printWriter.close();
            fileWriter.close();
            rs.close();
            stm.close();
            con.close();

            System.out.println("Tabla exportada a CSV con éxito");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}