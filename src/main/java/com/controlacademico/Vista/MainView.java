package com.controlacademico.Vista;

import com.controlacademico.Modelo.Conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

public class MainView extends JFrame {

    private JDesktopPane desktopPane;

    public MainView(String title) {
        super(title);
        
        // 1. Aplicar el Look and Feel Nimbus para un diseño más moderno/elegante
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si Nimbus no está disponible, se usa el LAF predeterminado.
            System.err.println("No se pudo aplicar Nimbus LAF.");
        }

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 2. Configurar el JDesktopPane con un color de fondo sutil
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(240, 240, 240)); // Gris claro sutil
        add(desktopPane, BorderLayout.CENTER);

        // 3. Configurar el Menú de Navegación
        setJMenuBar(createMenuBar());

        // 4. Configuración para cerrar la conexión
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Conexion.getInstance().closeConnection();
                System.exit(0);
            }
        });
        
        setLocationRelativeTo(null);
    }
    
    // -------------------------------------------------------------------------
    // --- Método para crear la barra de menú con categorías limpias ---
    // -------------------------------------------------------------------------
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú 1: Sistema (Archivo y Salir)
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemSalir = new JMenuItem("Salir del Sistema");
        itemSalir.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        menuSistema.add(itemSalir);
        
        // Menú 2: Gestión de Usuarios
        JMenu menuUsuarios = new JMenu("Usuarios");
        addMenuItem(menuUsuarios, "Gestión de Docentes 🧑‍🏫", new DocentePanel(), 850, 500);
        menuUsuarios.addSeparator(); // Separador para dividir la lógica
        addMenuItem(menuUsuarios, "Gestión de Estudiantes 🎓", new EstudiantePanel(), 1100, 600);

        // Menú 3: Gestión Académica (Estructura y Cursos)
        JMenu menuAcademico = new JMenu("Estructura Académica");
        addMenuItem(menuAcademico, "Periodos Académicos 🗓️", new PeriodosAcademicosPanel(), 700, 400);
        menuAcademico.addSeparator();
        addMenuItem(menuAcademico, "Gestión de Cursos 📚", new CursoPanel(), 900, 550);
        
        // Menú 4: Calificaciones
        JMenu menuCalificaciones = new JMenu("Calificaciones");
        addMenuItem(menuCalificaciones, "Cortes de Evaluación ✂️", new CortesEvaluacionPanel(), 950, 500);
        addMenuItem(menuCalificaciones, "Componentes de Evaluación 🧩", new ComponentesEvaluacionPanel(), 750, 450);
        menuCalificaciones.addSeparator();
        addMenuItem(menuCalificaciones, "Registrar Calificaciones 💯", new CalificacionesPanel(), 900, 550);
        
        // Menú 5: Logística
        JMenu menuLogistica = new JMenu("Clases y Asistencia");
        addMenuItem(menuLogistica, "Registro de Clases 💻", new ClasesPanel(), 1000, 600);
        addMenuItem(menuLogistica, "Registro de Asistencia ✅", new AsistenciaPanel(), 900, 550);

        // Agregar menús a la barra
        menuBar.add(menuSistema);
        menuBar.add(menuUsuarios);
        menuBar.add(menuAcademico);
        menuBar.add(menuCalificaciones);
        menuBar.add(menuLogistica);
        
        return menuBar;
    }
    
    /**
     * Helper para crear JMenuItems y asociarles la acción de abrir un JInternalFrame.
     */
    private void addMenuItem(JMenu parentMenu, String title, JPanel panel, int width, int height) {
        JMenuItem item = new JMenuItem(title);
        item.addActionListener((ActionEvent e) -> {
            
            // Revisa si la ventana ya está abierta para evitar duplicados
            for (JInternalFrame frame : desktopPane.getAllFrames()) {
                if (frame.getTitle().equals(title)) {
                    try {
                        frame.setSelected(true); // Solo la enfoca si ya existe
                    } catch (PropertyVetoException pve) {
                        pve.printStackTrace();
                    }
                    return; // Sale del método si ya está abierta
                }
            }

            // Crear y configurar la subventana interna
            JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
            
            frame.add(panel);
            frame.setSize(width, height); 
            
            // Agregar al escritorio
            desktopPane.add(frame);
            
            // Mostrar y centrar la subventana
            frame.setVisible(true);
            try {
                // Centrar dinámicamente
                frame.setLocation((desktopPane.getWidth() - frame.getWidth()) / 2,
                                  (desktopPane.getHeight() - frame.getHeight()) / 2);
                frame.setSelected(true);
            } catch (PropertyVetoException pve) {
                pve.printStackTrace();
            }
        });
        parentMenu.add(item);
    }
}