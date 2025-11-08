package com.controlacademico.Vista;

import com.controlacademico.Controlador.AsistenciaControlador;
import com.controlacademico.Modelo.Asistencia;
import com.controlacademico.Modelo.AsistenciaDAO;
import com.controlacademico.Modelo.Curso;
import com.controlacademico.Modelo.CursoDAO;
import com.controlacademico.Modelo.Estudiante;
import com.controlacademico.Modelo.EstudianteDAO;
import com.controlacademico.Modelo.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsistenciaPanel extends JPanel {

    private AsistenciaControlador controlador;
    private EstudianteDAO estudianteDAO;
    private CursoDAO cursoDAO;
    
    private JTable tablaAsistencias;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbEstudiante, cmbCurso, cmbEstado;
    private JTextField txtFechaClase;
    private JTextArea txtNovedades;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    private Map<String, Integer> mapaEstudiantes;
    private Map<String, Integer> mapaCursos;

    public AsistenciaPanel() {
        this.controlador = new AsistenciaControlador(new AsistenciaDAO());
        this.estudianteDAO = new EstudianteDAO(Conexion.getInstance().getConnection());
        this.cursoDAO = new CursoDAO();

        setLayout(new BorderLayout(10, 10));

        // --- Formulario ---
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Estudiante ID", "Curso ID", "Fecha", "Estado", "Novedades"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaAsistencias = new JTable(tableModel);
        tablaAsistencias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaAsistencias.getColumnModel().getColumn(5).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(tablaAsistencias);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones ---
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        cargarComboboxes();
        cargarAsistencias();
        agregarListeners();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Registro de Asistencia"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Estudiante:"), gbc);
        gbc.gridx = 1; panel.add(cmbEstudiante = new JComboBox<>(), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 3; panel.add(cmbCurso = new JComboBox<>(), gbc);

        // Fila 2
        gbc.gridy = 1;
        gbc.gridx = 0; panel.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; panel.add(txtFechaClase = new JTextField("YYYY-MM-DD", 10), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 3; panel.add(cmbEstado = new JComboBox<>(new String[]{"Presente", "Ausente Justificado", "Ausente Injustificado", "Tarde"}), gbc);

        // Fila 3 - Novedades
        gbc.gridy = 2;
        gbc.gridx = 0; gbc.gridwidth = 1; panel.add(new JLabel("Novedades:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtNovedades = new JTextArea(3, 40);
        JScrollPane scrollNovedades = new JScrollPane(txtNovedades);
        panel.add(scrollNovedades, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRegistrar = new JButton("Registrar Asistencia");
        btnActualizar = new JButton("Actualizar Asistencia");
        btnEliminar = new JButton("Eliminar Asistencia");
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
        mapaEstudiantes = new HashMap<>();
        cmbEstudiante.removeAllItems();
        try {
            List<Estudiante> estudiantes = estudianteDAO.findAll();
            for (Estudiante e : estudiantes) {
                String nombre = e.getNombre() + " (ID: " + e.getEstudiante_id() + ")";
                cmbEstudiante.addItem(nombre);
                mapaEstudiantes.put(nombre, e.getEstudiante_id());
            }
        } catch (Exception e) { cmbEstudiante.addItem("Error al cargar Estudiantes"); }

        mapaCursos = new HashMap<>();
        cmbCurso.removeAllItems();
        try {
            List<Curso> cursos = cursoDAO.listarTodos();
            for (Curso c : cursos) {
                String nombre = c.getNombreCurso() + " (ID: " + c.getCursoId() + ")";
                cmbCurso.addItem(nombre);
                mapaCursos.put(nombre, c.getCursoId());
            }
        } catch (Exception e) { cmbCurso.addItem("Error al cargar Cursos"); }
    }
    
    private Asistencia obtenerDatosAsistencia() throws IllegalArgumentException {
        Asistencia asistencia = new Asistencia();
        int filaSeleccionada = tablaAsistencias.getSelectedRow();
        if (filaSeleccionada != -1 && (btnActualizar.isEnabled() || btnEliminar.isEnabled())) {
             asistencia.setAsistenciaId((int) tableModel.getValueAt(filaSeleccionada, 0));
        }
        
        asistencia.setEstudianteId(mapaEstudiantes.get(cmbEstudiante.getSelectedItem()));
        asistencia.setCursoId(mapaCursos.get(cmbCurso.getSelectedItem()));
        asistencia.setFechaClase(Date.valueOf(txtFechaClase.getText().trim()));
        asistencia.setEstadoAsistencia((String) cmbEstado.getSelectedItem());
        asistencia.setNovedades(txtNovedades.getText().trim());
        
        return asistencia;
    }

    private void cargarAsistencias() {
        tableModel.setRowCount(0); 
        List<Asistencia> asistencias = new AsistenciaDAO().findAll();
        for (Asistencia a : asistencias) {
             tableModel.addRow(new Object[]{
                a.getAsistenciaId(),
                a.getEstudianteId(),
                a.getCursoId(),
                a.getFechaClase(),
                a.getEstadoAsistencia(),
                a.getNovedades()
            });
        }
    }
    
    private void limpiarCampos() {
        txtFechaClase.setText("YYYY-MM-DD");
        txtNovedades.setText("");
        if (cmbEstudiante.getItemCount() > 0) cmbEstudiante.setSelectedIndex(0);
        if (cmbCurso.getItemCount() > 0) cmbCurso.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        tablaAsistencias.clearSelection();
    }
    
    private void agregarListeners() {
        tablaAsistencias.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaAsistencias.getSelectedRow() != -1) {
                int fila = tablaAsistencias.getSelectedRow();
                txtFechaClase.setText(tableModel.getValueAt(fila, 3).toString());
                cmbEstado.setSelectedItem(tableModel.getValueAt(fila, 4));
                txtNovedades.setText((String) tableModel.getValueAt(fila, 5));

                int estudianteId = (int) tableModel.getValueAt(fila, 1);
                int cursoId = (int) tableModel.getValueAt(fila, 2);
                
                mapaEstudiantes.entrySet().stream().filter(entry -> entry.getValue().equals(estudianteId))
                    .findFirst().ifPresent(entry -> cmbEstudiante.setSelectedItem(entry.getKey()));
                
                mapaCursos.entrySet().stream().filter(entry -> entry.getValue().equals(cursoId))
                    .findFirst().ifPresent(entry -> cmbCurso.setSelectedItem(entry.getKey()));
                
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
            try {
                Asistencia nuevaAsistencia = obtenerDatosAsistencia();
                if (nuevaAsistencia.getEstudianteId() == null || nuevaAsistencia.getCursoId() == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione Estudiante y Curso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (controlador.registrarAsistencia(nuevaAsistencia)) {
                    JOptionPane.showMessageDialog(this, "Asistencia registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarAsistencias();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar asistencia.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
             try {
                Asistencia asistenciaAActualizar = obtenerDatosAsistencia();
                if (asistenciaAActualizar.getAsistenciaId() != null && controlador.actualizarAsistencia(asistenciaAActualizar)) {
                    JOptionPane.showMessageDialog(this, "Asistencia actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarAsistencias();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar asistencia.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaAsistencias.getSelectedRow();
            if (fila != -1) {
                int asistenciaId = (int) tableModel.getValueAt(fila, 0);
                if (controlador.eliminarAsistencia(asistenciaId)) {
                    JOptionPane.showMessageDialog(this, "Asistencia eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarAsistencias();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar asistencia.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}
