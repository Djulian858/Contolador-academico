package com.controlacademico.Vista;
import javax.swing.*;
import java.awt.*;
 
public class MenuPrincipalframe extends JFrame {
 
    public MenuPrincipalframe() {
        setTitle("Sistema Académico - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
 
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
 
        JButton btnEstudiantes = new JButton("Gestionar Estudiantes");
        JButton btnAsistencias = new JButton("Gestionar Asistencias");
        JButton btnCursos = new JButton("Gestionar Cursos");
        JButton btnCalificaciones = new JButton("Gestionar Calificaciones");
        JButton btnClases = new JButton("Gestionar Clases");
        JButton btnComponentes = new JButton("Gestionar Componentes de Evaluación");
        JButton btnCortes = new JButton("Gestionar Cortes de Evaluación");
        JButton btnDocentes = new JButton("Gestionar Docentes");
        JButton btnDocentesCursos = new JButton("Asignar Docentes a Cursos");
        JButton btnPeriodos = new JButton("Gestionar Periodos Académicos");
        JButton btnReportesAsistencia = new JButton("Reporte de Asistencia");
        JButton btnReportesNotaFinal = new JButton("Reporte Nota Final");
        JButton btnSalir = new JButton("Salir");
 
        // btnEstudiantes.addActionListener(e -> new EstudiantesFrame().setVisible(true));
        // btnAsistencias.addActionListener(e -> new AsistenciasFrame().setVisible(true));
        // btnCursos.addActionListener(e -> new CursosFrame().setVisible(true));
        // btnCalificaciones.addActionListener(e -> new CalificacionesFrame().setVisible(true));
        // btnClases.addActionListener(e -> new ClasesFrame().setVisible(true));
        // btnComponentes.addActionListener(e -> new ComponentesEvaluacionFrame().setVisible(true));
        // btnCortes.addActionListener(e -> new CortesEvaluacionFrame().setVisible(true));
        // btnDocentes.addActionListener(e -> new DocentesFrame().setVisible(true));
        // btnDocentesCursos.addActionListener(e -> new DocentesCursosFrame().setVisible(true));
        // btnPeriodos.addActionListener(e -> new PeriodosAcademicosFrame().setVisible(true));
        // btnReportesAsistencia.addActionListener(e -> new ReporteAsistenciaFrame().setVisible(true));
        // btnReportesNotaFinal.addActionListener(e -> new ReporteNotaFinalFrame().setVisible(true));
 
 
        btnSalir.addActionListener(e -> System.exit(0));
 
        panel.add(btnEstudiantes);
        panel.add(btnAsistencias);
        panel.add(btnCursos);
        panel.add(btnCalificaciones);
        panel.add(btnClases);
        panel.add(btnComponentes);
        panel.add(btnCortes);
        panel.add(btnDocentes);
        panel.add(btnDocentesCursos);
        panel.add(btnPeriodos);
        panel.add(btnReportesAsistencia);
        panel.add(btnReportesNotaFinal);
        panel.add(btnSalir);
 
 
        add(panel);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipalframe().setVisible(true));
    }
}