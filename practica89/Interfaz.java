package org.ieslosremedios.daw1.prog.ut8y9;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.ieslosremedios.daw1.prog.ut8y9.BaseDatos.*;
import static org.ieslosremedios.daw1.prog.ut8y9.Operaciones.*;
import static org.ieslosremedios.daw1.prog.ut8y9.ImportarExportar.*;


public class Interfaz extends Frame {
    private final Panel menuPanel;
    private final Frame crearBorrarFrame;
    private final Frame operacionesFrame;
    private final Frame importarExportarFrame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Interfaz gui = new Interfaz();
                gui.setVisible(true);
            }
        });
    }

    private Interfaz() {
        setTitle("Menú Principal");
        setSize(1500, 750);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        // Crear botones del menú principal
        Button botonCrearBorrar = new Button("Crear/Borrar Base de Datos");
        Button botonOperaciones = new Button("Operaciones Base de Datos");
        Button botonImportarExportar = new Button("Importar/Exportar Datos");

        // Configurar ActionListener para cada botón
        botonCrearBorrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCrearBorrarFrame();
            }
        });

        botonOperaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarOperacionesFrame();
            }
        });

        botonImportarExportar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarImportarExportarFrame();
            }
        });

        // Crea panel para los botones del menú
        menuPanel = new Panel(new GridLayout(3, 1, 10, 10));
        menuPanel.add(botonCrearBorrar);
        menuPanel.add(botonOperaciones);
        menuPanel.add(botonImportarExportar);

        // Agrega panel del menú al frame principal
        add(menuPanel);

        // Inicializar los frames correspondientes a cada opción del menú
        crearBorrarFrame = crearBorrar();
        operacionesFrame = operaciones();
        importarExportarFrame = importarExportar();

        // Con esto cerramos el menú principal (paramos la ejecución)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0); // Si no se cierra, usamos la fuerza bruta
            }
        });
    }


    private Frame crearBorrar() {
        Frame frame = new Frame();
        frame.setTitle("Crear/Borrar Base de Datos");
        frame.setSize(1500, 750);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                mostrarMenuPrincipal();
            }
        });

        // Crear el componente menú
        Panel menuPanel = opciones();

        // Agregar el componente menú al frame
        frame.add(menuPanel, BorderLayout.NORTH);

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(5, 2, 12, 12));

        Label labelBDCB = new Label("Base de datos:");
        Label labelTablaCB = new Label("Tabla:");

        TextField campoBDCB = new TextField();
        TextField campoTablaCB = new TextField();

        Button botonCrearBD = new Button("Crear Base de Datos");
        Button botonBorrarBD = new Button("Borrar Base de Datos");
        Button botonCrearTabla = new Button("Crear Tabla");
        Button botonBorrarTabla = new Button("Borrar Tabla");

        botonCrearBD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreBD = campoBDCB.getText();

                crearBaseDeDatos(nombreBD);
                mostrarMensaje("Base de datos " + nombreBD + " creada");
            }
        });

        botonBorrarBD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreBD = campoBDCB.getText();

                borrarBaseDeDatos(nombreBD);
                mostrarMensaje("Base de datos " + nombreBD + " borrada");
            }
        });

        botonCrearTabla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreBD = campoBDCB.getText();
                String nombreTabla = campoTablaCB.getText();

                crearTabla(nombreBD, nombreTabla);
                mostrarMensaje("Tabla " + nombreTabla + " creada");
            }
        });

        botonBorrarTabla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreBD = campoBDCB.getText();
                String nombreTabla = campoTablaCB.getText();

                borrarTabla(nombreBD, nombreTabla);
                mostrarMensaje("Tabla " + nombreTabla + " borrada");
            }
        });

        panel.add(labelBDCB);
        panel.add(campoBDCB);
        panel.add(labelTablaCB);
        panel.add(campoTablaCB);
        panel.add(botonCrearBD);
        panel.add(botonBorrarBD);
        panel.add(botonCrearTabla);
        panel.add(botonBorrarTabla);

        frame.add(panel);

        return frame;
    }

    private Frame operaciones() {
        Frame frame = new Frame();
        frame.setTitle("Operaciones Base de Datos");
        frame.setSize(1500, 750);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                mostrarMenuPrincipal();
            }
        });

        Panel menuPanel = opciones();

        // Agregar el componente menú al frame
        frame.add(menuPanel, BorderLayout.NORTH);

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(11, 2, 10, 10));

        Label labelBDO = new Label("Base de datos:");
        Label labelTablaO = new Label("Tabla:");
        Label labelAlumno = new Label("Alumno:");
        Label labelIntervenciones = new Label("Intervenciones:");
        Label labelUltimaIntervencion = new Label("Última Intervención:");

        TextField campoBDO = new TextField();
        TextField campoTablaO = new TextField();
        TextField campoAlumno = new TextField();
        TextField campoIntervenciones = new TextField();
        TextField campoFecha = new TextField();

        Button botonAlumnoMasParticipaciones = new Button("Alumno con más participaciones");
        Button botonAlumnoMenosParticipaciones = new Button("Alumno con menos participaciones");
        Button botonAlumnosPorDebajoMedia = new Button("Alumnos por debajo de la media");
        Button botonAlumnosPorCantidadIntervenciones = new Button("Alumnos por cantidad de intervenciones");
        Button botonUltimoAlumnoParticipante = new Button("Último alumno en participar");
        Button botonMostrarInformacionAlumno = new Button("Mostrar información de un alumno");
        Button botonDarDeAlta = new Button("Dar de alta");
        Button botonDarDeBaja = new Button("Dar de baja");
        Button botonModificarAlumno = new Button("Modificar alumno");
        Button botonSeleccionarRandom=new Button("Seleccionar alumno aleatorio");
        Button botonResetearIntervenciones=new Button("Resetear intervenciones");

        botonAlumnoMasParticipaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stringCampoBDO = campoBDO.getText();
                String stringCampoTablaO = campoTablaO.getText();
                mostrarMensaje("Alumno con más participaciones: " + alumnoMasParticipativo(stringCampoBDO, stringCampoTablaO));
            }
        });

        botonAlumnoMenosParticipaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stringCampoBDO = campoBDO.getText();
                String stringCampoTablaO = campoTablaO.getText();
                mostrarMensaje("Alumno con menos intervenciones: " + alumnoMenosParticipativo(stringCampoBDO, stringCampoTablaO));
            }
        });

        botonAlumnosPorDebajoMedia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stringCampoBDO = campoBDO.getText();
                String stringCampoTablaO = campoTablaO.getText();
                mostrarMensaje("Alumnos por debajo de la media: " + alumnosDebajoMedia(stringCampoBDO, stringCampoTablaO));
            }
        });

        botonAlumnosPorCantidadIntervenciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stringCampoBDO = campoBDO.getText();
                String stringCampoTablaO = campoTablaO.getText();
                String stringCampoIntervenciones = campoIntervenciones.getText();
                mostrarMensaje("Alumnos con " + stringCampoIntervenciones + " intervenciones, más de " + stringCampoIntervenciones + " y menos de " + stringCampoIntervenciones+ ": " + alumnosIntervencionesValor(stringCampoBDO, stringCampoTablaO, stringCampoIntervenciones));
            }
        });

        botonUltimoAlumnoParticipante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stringCampoTabla=campoTablaO.getText();
                String stringCampoBDO=campoBDO.getText();
                mostrarMensaje("Último alumno en participar: "+alumnoUltimo(stringCampoBDO,stringCampoTabla));
            }
        });

        botonMostrarInformacionAlumno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stringCampoAlumno=campoAlumno.getText();
                String stringCampoTabla=campoTablaO.getText();
                String stringCampoBDO=campoBDO.getText();
                mostrarMensaje("Mostrar información de un alumno: "+alumnoInfo(stringCampoBDO,stringCampoTabla,stringCampoAlumno));
            }
        });

        botonDarDeAlta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String stringCampoAlumno=campoAlumno.getText();
                String stringCampoTabla=campoTablaO.getText();
                String stringCampoBDO=campoBDO.getText();
                mostrarMensaje(alumnoDarAlta(stringCampoBDO,stringCampoTabla,stringCampoAlumno));
            }
        });

        botonDarDeBaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stringCampoAlumno=campoAlumno.getText();
                String stringCampoTabla=campoTablaO.getText();
                String stringCampoBDO=campoBDO.getText();
                mostrarMensaje(alumnoDarBaja(stringCampoBDO,stringCampoTabla,stringCampoAlumno));
            }
        });

        botonModificarAlumno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //String stringCampoAlumno=campoAlumno.getText();
                //String stringCampoTabla=campoTablaO.getText();
                //String stringCampoBDO=campoBDO.getText();
                //String intTervenciones=campoIntervenciones.getText();
                //mostrarMensaje("Modificar alumno"+alumnoModificar(stringCampoBDO,stringCampoTabla,stringCampoAlumno));
            }
        });
        botonSeleccionarRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stringCampoTabla=campoTablaO.getText();
                String stringCampoBDO=campoBDO.getText();
                mostrarMensaje(seleccionarAlumnoAleatorio(stringCampoBDO,stringCampoTabla));
            }
        });
        botonResetearIntervenciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stringCampoTabla=campoTablaO.getText();
                String stringCampoBDO=campoBDO.getText();
                mostrarMensaje(reiniciarIntervenciones(stringCampoBDO,stringCampoTabla));
            }
        });

        panel.add(labelBDO);
        panel.add(campoBDO);
        panel.add(labelTablaO);
        panel.add(campoTablaO);
        panel.add(labelAlumno);
        panel.add(campoAlumno);
        panel.add(labelIntervenciones);
        panel.add(campoIntervenciones);
        panel.add(labelUltimaIntervencion);
        panel.add(campoFecha);
        panel.add(botonAlumnoMasParticipaciones);
        panel.add(botonAlumnoMenosParticipaciones);
        panel.add(botonAlumnosPorDebajoMedia);
        panel.add(botonAlumnosPorCantidadIntervenciones);
        panel.add(botonUltimoAlumnoParticipante);
        panel.add(botonMostrarInformacionAlumno);
        panel.add(botonDarDeAlta);
        panel.add(botonDarDeBaja);
        panel.add(botonModificarAlumno);
        panel.add(botonSeleccionarRandom);
        panel.add(botonResetearIntervenciones);

        frame.add(panel);

        return frame;
    }

    private Frame importarExportar() {
        Frame frame = new Frame();
        frame.setTitle("Importar/Exportar Datos");
        frame.setSize(1500, 750);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                mostrarMenuPrincipal();
            }
        });

        Panel menuPanel = opciones();

        // Agrega el componente menú al frame
        frame.add(menuPanel, BorderLayout.NORTH);

        Panel panel = new Panel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        Label labelBDIO = new Label("Base de datos:");
        Label labelTablaIO = new Label("Tabla:");
        Label labelRutaIO = new Label("Ruta del archivo:");

        TextField campoBDIO = new TextField();
        TextField campoTablaIO = new TextField();
        TextField campoRutaIO = new TextField();

        Button botonImportarXML = new Button("Importar XML");
        Button botonImportarCSV = new Button("Importar CSV");
        Button botonExportarXML = new Button("Exportar XML");
        Button botonExportarCSV = new Button("Exportar CSV");


        botonImportarXML.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreBD = campoBDIO.getText();
                String nombreTabla = campoTablaIO.getText();
                String rutaImportarXML = campoRutaIO.getText();

                // Método que importa el archivo XML
                importarXML(nombreBD, nombreTabla, rutaImportarXML);
                mostrarMensaje("XML importado a la tabla " + nombreTabla);
            }
        });

        botonExportarXML.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreBD = campoBDIO.getText();
                String nombreTabla = campoTablaIO.getText();
                String rutaExportarXML = campoRutaIO.getText();

                // Método que exporta el archivo XML
                exportarXML(nombreBD, nombreTabla, rutaExportarXML);
                mostrarMensaje("XML de la tabla " + nombreTabla + " exportado");

            }
        });

        botonImportarCSV.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreBD = campoBDIO.getText();
                String nombreTabla = campoTablaIO.getText();
                String rutaImportarCSV = campoRutaIO.getText();

                // Método que importa el archivo CSV
                importarCSV(nombreBD, nombreTabla, rutaImportarCSV);
                mostrarMensaje("CSV importado a la tabla " + nombreTabla);

            }
        });

        botonExportarCSV.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreBD = campoBDIO.getText();
                String nombreTabla = campoTablaIO.getText();
                String rutaExportarCSV = campoRutaIO.getText();

                // Método que exporta el archivo CSV
                exportarCSV(nombreBD, nombreTabla, rutaExportarCSV);
                mostrarMensaje("CSV de la tabla " + nombreTabla + " exportado");

            }
        });

        panel.add(labelBDIO);
        panel.add(campoBDIO);
        panel.add(labelTablaIO);
        panel.add(campoTablaIO);
        panel.add(labelRutaIO);
        panel.add(campoRutaIO);
        panel.add(botonImportarXML);
        panel.add(botonImportarCSV);
        panel.add(botonExportarXML);
        panel.add(botonExportarCSV);

        frame.add(panel);

        return frame;
    }

    private Panel opciones() {
        // Crear panel para el menú
        Panel panelOpciones = new Panel(new FlowLayout());

        // Crear botones del menú
        Button botonMenu1 = new Button("Crear/Borrar");
        Button botonMenu2 = new Button("Operaciones");
        Button botonMenu3 = new Button("Importar/Exportar");

        // Configurar ActionListener para cada botón del menú
        botonMenu1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCrearBorrarFrame();
            }
        });

        botonMenu2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarOperacionesFrame();
            }
        });

        botonMenu3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarImportarExportarFrame();
            }
        });

        // Agregar botones al panel del menú
        panelOpciones.add(botonMenu1);
        panelOpciones.add(botonMenu2);
        panelOpciones.add(botonMenu3);

        return panelOpciones;
    }

    private void mostrarMensaje(String mensaje) {
        Dialog dialog = new Dialog(this, "Mensaje");
        dialog.setLayout(new BorderLayout());

        Label labelMensaje = new Label(mensaje);
        Button botonAceptar = new Button("Aceptar");

        botonAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(labelMensaje, BorderLayout.CENTER);
        dialog.add(botonAceptar, BorderLayout.SOUTH);

        dialog.setSize(750, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private void mostrarMenuPrincipal() {
        menuPanel.setVisible(true);
        crearBorrarFrame.setVisible(false);
        operacionesFrame.setVisible(false);
        importarExportarFrame.setVisible(false);
    }

    private void mostrarCrearBorrarFrame() {
        menuPanel.setVisible(true);
        crearBorrarFrame.setVisible(true);
        operacionesFrame.setVisible(false);
        importarExportarFrame.setVisible(false);
    }

    private void mostrarOperacionesFrame() {
        menuPanel.setVisible(true);
        crearBorrarFrame.setVisible(false);
        operacionesFrame.setVisible(true);
        importarExportarFrame.setVisible(false);
    }

    private void mostrarImportarExportarFrame() {
        menuPanel.setVisible(true);
        crearBorrarFrame.setVisible(false);
        operacionesFrame.setVisible(false);
        importarExportarFrame.setVisible(true);
    }
}