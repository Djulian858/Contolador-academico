package com.controlacademico.Vista;

import com.controlacademico.Controlador.CalificacionesControlador;
import com.controlacademico.Controlador.Componentes_EvaluacionControlador;
import com.controlacademico.Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalificacionesPanel extends JPanel {

    private CalificacionesControlador controlador;
    private EstudianteDAO estudianteDAO;
    private Componentes_EvaluacionControlador componenteControlador;
    
    private JTable tablaCalificaciones;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbEstudiante, cmbComponente;
    private JTextField txtNota;
    private JTextArea txtComentarios;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    private Map<String, Integer> mapaEstudiantes;
    private Map<String, Integer> mapaComponentes;

    public CalificacionesPanel() {
        this.controlador = new CalificacionesControlador(new CalificacionesDAO());
        this.estudianteDAO = new EstudianteDAO(Conexion.getInstance().getConnection());
        this.componenteControlador = new Componentes_EvaluacionControlador(new Componentes_EvaluacionDAO(Conexion.getInstance().getConnection()));

        setLayout(new BorderLayout(10, 10));

        // --- Formulario ---
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Estudiante ID", "Componente ID", "Nota", "Comentarios"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaCalificaciones = new JTable(tableModel);
        tablaCalificaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaCalificaciones.getColumnModel().getColumn(4).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(tablaCalificaciones);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones ---
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        cargarComboboxes();
        cargarCalificaciones();
        agregarListeners();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Registro de Calificaciones"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Estudiante:"), gbc);
        gbc.gridx = 1; panel.add(cmbEstudiante = new JComboBox<>(), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Componente:"), gbc);
        gbc.gridx = 3; panel.add(cmbComponente = new JComboBox<>(), gbc);

        // Fila 2
        gbc.gridy = 1;
        gbc.gridx = 0; panel.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1; panel.add(txtNota = new JTextField(5), gbc);

        // Fila 3 - Comentarios
        gbc.gridy = 2;
        gbc.gridx = 0; gbc.gridwidth = 1; panel.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtComentarios = new JTextArea(3, 40);
        JScrollPane scrollComentarios = new JScrollPane(txtComentarios);
        panel.add(scrollComentarios, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRegistrar = new JButton("Registrar Calificación");
        btnActualizar = new JButton("Actualizar Calificación");
        btnEliminar = new JButton("Eliminar Calificación");
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

        mapaComponentes = new HashMap<>();
        cmbComponente.removeAllItems();
        try {
            List<Componentes_Evaluacion> componentes = componenteControlador.obtenerTodosLosComponentes();
            for (Componentes_Evaluacion c : componentes) {
                String nombre = c.getNombre_componente() + " (Corte: " + c.getCorteEvaluacionId() + " - ID: " + c.getComponenteEvaluacionId() + ")";
                cmbComponente.addItem(nombre);
                mapaComponentes.put(nombre, c.getComponenteEvaluacionId());
            }
        } catch (Exception e) { cmbComponente.addItem("Error al cargar Componentes"); }
    }
    
    private Calificaciones obtenerDatosCalificacion() throws NumberFormatException {
        Calificaciones calif = new Calificaciones();
        int filaSeleccionada = tablaCalificaciones.getSelectedRow();
        if (filaSeleccionada != -1 && (btnActualizar.isEnabled() || btnEliminar.isEnabled())) {
             calif.setCalificacionId((int) tableModel.getValueAt(filaSeleccionada, 0));
        }
        
        calif.setEstudianteId(mapaEstudiantes.get(cmbEstudiante.getSelectedItem()));
        calif.setComponenteEvaluacionId(mapaComponentes.get(cmbComponente.getSelectedItem()));
        calif.setNota(new BigDecimal(txtNota.getText().trim()));
        calif.setComentariosCalificacion(txtComentarios.getText().trim());
        
        return calif;
    }

    private void cargarCalificaciones() {
        tableModel.setRowCount(0); 
        List<Calificaciones> calificaciones = new CalificacionesDAO().findAll();
        for (Calificaciones c : calificaciones) {
             tableModel.addRow(new Object[]{
                c.getCalificacionId(),
                c.getEstudianteId(),
                c.getComponenteEvaluacionId(),
                c.getNota(),
                c.getComentariosCalificacion()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNota.setText("");
        txtComentarios.setText("");
        if (cmbEstudiante.getItemCount() > 0) cmbEstudiante.setSelectedIndex(0);
        if (cmbComponente.getItemCount() > 0) cmbComponente.setSelectedIndex(0);
        tablaCalificaciones.clearSelection();
    }
    
    private void agregarListeners() {
        tablaCalificaciones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaCalificaciones.getSelectedRow() != -1) {
                int fila = tablaCalificaciones.getSelectedRow();
                txtNota.setText(tableModel.getValueAt(fila, 3).toString());
                txtComentarios.setText((String) tableModel.getValueAt(fila, 4));

                int estudianteId = (int) tableModel.getValueAt(fila, 1);
                int componenteId = (int) tableModel.getValueAt(fila, 2);
                
                mapaEstudiantes.entrySet().stream().filter(entry -> entry.getValue().equals(estudianteId))
                    .findFirst().ifPresent(entry -> cmbEstudiante.setSelectedItem(entry.getKey()));
                
                mapaComponentes.entrySet().stream().filter(entry -> entry.getValue().equals(componenteId))
                    .findFirst().ifPresent(entry -> cmbComponente.setSelectedItem(entry.getKey()));
                
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
                Calificaciones nuevaCalificacion = obtenerDatosCalificacion();
                if (nuevaCalificacion.getEstudianteId() == null || nuevaCalificacion.getComponenteEvaluacionId() == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione Estudiante y Componente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (controlador.registrarCalificacion(nuevaCalificacion)) {
                    JOptionPane.showMessageDialog(this, "Calificación registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarCalificaciones();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar calificación.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La nota debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
             try {
                Calificaciones calificacionAActualizar = obtenerDatosCalificacion();
                if (calificacionAActualizar.getCalificacionId() != null && controlador.actualizarCalificacion(calificacionAActualizar)) {
                    JOptionPane.showMessageDialog(this, "Calificación actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarCalificaciones();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar calificación.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La nota debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaCalificaciones.getSelectedRow();
            if (fila != -1) {
                int calificacionId = (int) tableModel.getValueAt(fila, 0);
                if (controlador.eliminarCalificacion(calificacionId)) {
                    JOptionPane.showMessageDialog(this, "Calificación eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarCalificaciones();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar calificación. Revise dependencias.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}