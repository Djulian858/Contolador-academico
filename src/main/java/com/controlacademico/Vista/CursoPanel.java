package com.controlacademico.Vista;

import com.controlacademico.Controlador.CursoControlador;
import com.controlacademico.Controlador.DocenteControlador;
import com.controlacademico.Controlador.Periodos_AcademicosControlador;
import com.controlacademico.Modelo.*; // Importar todos los modelos necesarios

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CursoPanel extends JPanel {

    private CursoControlador cursoControlador;
    private Periodos_AcademicosControlador periodoControlador;
    private DocenteControlador docenteControlador;
    
    private JTable tablaCursos;
    private DefaultTableModel tableModel;
    private JTextField txtNombreCurso;
    private JComboBox<String> cmbPeriodo, cmbDocente;
    private JTextArea txtDescripcion;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    private Map<String, Integer> mapaPeriodos;
    private Map<String, Integer> mapaDocentes;

    public CursoPanel() {
        this.cursoControlador = new CursoControlador(new CursoDAO());
        // Se necesitan otros controladores para llenar los ComboBox
        this.periodoControlador = new Periodos_AcademicosControlador(new Periodos_AcademicosDAO(Conexion.getInstance().getConnection()));
        this.docenteControlador = new DocenteControlador(new DocenteDAO());
        
        setLayout(new BorderLayout(10, 10));

        // --- Formulario ---
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Nombre", "Periodo ID", "Docente ID", "Descripción"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaCursos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaCursos);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones ---
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        cargarComboboxes();
        cargarCursos();
        agregarListeners();
    }

    private JPanel createFormPanel() {
        // ... (Implementación similar a DocentePanel/EstudiantePanel para el layout)
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Curso"));

        txtNombreCurso = new JTextField(15);
        cmbPeriodo = new JComboBox<>();
        cmbDocente = new JComboBox<>();
        txtDescripcion = new JTextArea(3, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);

        panel.add(new JLabel("Nombre del Curso:"));
        panel.add(txtNombreCurso);
        
        panel.add(new JLabel("Periodo Académico:"));
        panel.add(cmbPeriodo);

        panel.add(new JLabel("Docente Asignado:"));
        panel.add(cmbDocente);
        
        // Un panel auxiliar para la descripción ya que el GridLayout es rígido
        JPanel pnlDescripcion = new JPanel(new BorderLayout());
        pnlDescripcion.add(new JLabel("Descripción:"), BorderLayout.NORTH);
        pnlDescripcion.add(scrollDescripcion, BorderLayout.CENTER);
        
        JPanel pnlFinal = new JPanel(new BorderLayout());
        pnlFinal.add(panel, BorderLayout.NORTH);
        pnlFinal.add(pnlDescripcion, BorderLayout.CENTER);

        return pnlFinal;
    }

    private JPanel createButtonPanel() {
        // ... (Implementación de botones de acción)
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRegistrar = new JButton("Registrar Curso");
        btnActualizar = new JButton("Actualizar Curso");
        btnEliminar = new JButton("Eliminar Curso");
        btnLimpiar = new JButton("Limpiar Campos");

        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        return panel;
    }

    private void cargarComboboxes() {
        mapaPeriodos = new HashMap<>();
        cmbPeriodo.removeAllItems();
        try {
            List<Periodos_Academicos> periodos = periodoControlador.obtenerTodosLosPeriodosAcademicos();
            for (Periodos_Academicos p : periodos) {
                String nombre = p.getNombrePeriodo() + " (ID: " + p.getPeriodoAcademicoId() + ")";
                cmbPeriodo.addItem(nombre);
                mapaPeriodos.put(nombre, p.getPeriodoAcademicoId());
            }
        } catch (Exception e) {
             cmbPeriodo.addItem("Error al cargar Periodos");
             System.err.println("Error cargando periodos: " + e.getMessage());
        }
        
        mapaDocentes = new HashMap<>();
        cmbDocente.removeAllItems();
        try {
            List<Docente> docentes = docenteControlador.listarDocentes();
            for (Docente d : docentes) {
                String nombre = d.getNombreDocente() + " (ID: " + d.getDocenteId() + ")";
                cmbDocente.addItem(nombre);
                mapaDocentes.put(nombre, d.getDocenteId());
            }
        } catch (Exception e) {
             cmbDocente.addItem("Error al cargar Docentes");
             System.err.println("Error cargando docentes: " + e.getMessage());
        }
    }

    private Curso obtenerDatosCurso() {
        Curso curso = new Curso();
        int filaSeleccionada = tablaCursos.getSelectedRow();
        if (filaSeleccionada != -1 && (btnActualizar.isEnabled() || btnEliminar.isEnabled())) {
             curso.setCursoId((int) tableModel.getValueAt(filaSeleccionada, 0));
        }
        
        curso.setNombreCurso(txtNombreCurso.getText().trim());
        curso.setDescripcionCurso(txtDescripcion.getText().trim());
        
        Integer periodoId = mapaPeriodos.get(cmbPeriodo.getSelectedItem());
        Integer docenteId = mapaDocentes.get(cmbDocente.getSelectedItem());
        
        curso.setPeriodoAcademicoId(periodoId);
        curso.setDocenteId(docenteId);
        
        return curso;
    }

    private void cargarCursos() {
        tableModel.setRowCount(0); 
        List<Curso> cursos = new CursoDAO().listarTodos(); // Uso directo del DAO si el controlador no tiene listarTodos
        for (Curso c : cursos) {
            tableModel.addRow(new Object[]{
                c.getCursoId(),
                c.getNombreCurso(),
                c.getPeriodoAcademicoId(),
                c.getDocenteId(),
                c.getDescripcionCurso()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNombreCurso.setText("");
        txtDescripcion.setText("");
        cmbPeriodo.setSelectedIndex(0);
        cmbDocente.setSelectedIndex(0);
        tablaCursos.clearSelection();
    }
    
    private void agregarListeners() {
        tablaCursos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaCursos.getSelectedRow() != -1) {
                int fila = tablaCursos.getSelectedRow();
                txtNombreCurso.setText((String) tableModel.getValueAt(fila, 1));
                txtDescripcion.setText((String) tableModel.getValueAt(fila, 4));

                // Buscar el valor en el mapa/combobox
                int periodoId = (int) tableModel.getValueAt(fila, 2);
                int docenteId = (int) tableModel.getValueAt(fila, 3);
                
                mapaPeriodos.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(periodoId))
                    .findFirst().ifPresent(entry -> cmbPeriodo.setSelectedItem(entry.getKey()));
                
                mapaDocentes.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(docenteId))
                    .findFirst().ifPresent(entry -> cmbDocente.setSelectedItem(entry.getKey()));
                
                btnRegistrar.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
            } else {
                 btnRegistrar.setEnabled(true);
                 btnActualizar.setEnabled(false);
                 btnEliminar.setEnabled(false);
            }
        });

        // Listeners CRUD
        btnRegistrar.addActionListener(e -> {
            Curso nuevoCurso = obtenerDatosCurso();
            if (nuevoCurso.getPeriodoAcademicoId() == null || nuevoCurso.getDocenteId() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione Periodo Académico y Docente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cursoControlador.registrarCurso(nuevoCurso)) {
                JOptionPane.showMessageDialog(this, "Curso registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarCursos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar curso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            Curso cursoAActualizar = obtenerDatosCurso();
            if (cursoAActualizar.getCursoId() > 0 && cursoControlador.actualizarCurso(cursoAActualizar)) {
                JOptionPane.showMessageDialog(this, "Curso actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarCursos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar curso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaCursos.getSelectedRow();
            if (fila != -1) {
                int cursoId = (int) tableModel.getValueAt(fila, 0);
                if (cursoControlador.eliminarCurso(cursoId)) {
                    JOptionPane.showMessageDialog(this, "Curso eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarCursos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar curso.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}
