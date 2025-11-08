package com.controlacademico.Vista;

import com.controlacademico.Controlador.Cortes_EvaluacionControlador;
import com.controlacademico.Controlador.CursoControlador;
import com.controlacademico.Controlador.Periodos_AcademicosControlador;
import com.controlacademico.Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CortesEvaluacionPanel extends JPanel {

    private Cortes_EvaluacionControlador controlador;
    private CursoControlador cursoControlador;
    private Periodos_AcademicosControlador periodoControlador;
    
    private JTable tablaCortes;
    private DefaultTableModel tableModel;
    private JTextField txtNombreCorte, txtPorcentaje;
    private JComboBox<String> cmbCurso, cmbPeriodo;
    private JTextArea txtComentarios;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    private Map<String, Integer> mapaCursos;
    private Map<String, Integer> mapaPeriodos;

    public CortesEvaluacionPanel() {
        this.controlador = new Cortes_EvaluacionControlador(new Cortes_EvaluacionDAO());
        this.cursoControlador = new CursoControlador(new CursoDAO());
        this.periodoControlador = new Periodos_AcademicosControlador(new Periodos_AcademicosDAO(Conexion.getInstance().getConnection()));

        setLayout(new BorderLayout(10, 10));

        // --- Formulario ---
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Nombre Corte", "Porcentaje", "Curso ID", "Periodo ID", "Comentarios"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaCortes = new JTable(tableModel);
        tablaCortes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaCortes.getColumnModel().getColumn(5).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(tablaCortes);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones ---
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        cargarComboboxes();
        cargarCortes();
        agregarListeners();
    }

    private JPanel createFormPanel() {
        // ... (Implementación de formulario con GridBagLayout)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Corte de Evaluación"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nombre del Corte:"), gbc);
        gbc.gridx = 1; panel.add(txtNombreCorte = new JTextField(15), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Porcentaje (%):"), gbc);
        gbc.gridx = 3; panel.add(txtPorcentaje = new JTextField(5), gbc);

        // Fila 2
        gbc.gridy = 1;
        gbc.gridx = 0; panel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1; panel.add(cmbCurso = new JComboBox<>(), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Periodo:"), gbc);
        gbc.gridx = 3; panel.add(cmbPeriodo = new JComboBox<>(), gbc);

        // Fila 3 - Comentarios
        gbc.gridy = 2;
        gbc.gridx = 0; panel.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtComentarios = new JTextArea(3, 40);
        JScrollPane scrollComentarios = new JScrollPane(txtComentarios);
        panel.add(scrollComentarios, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        // ... (Implementación de botones de acción)
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRegistrar = new JButton("Registrar Corte");
        btnActualizar = new JButton("Actualizar Corte");
        btnEliminar = new JButton("Eliminar Corte");
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
        mapaCursos = new HashMap<>();
        cmbCurso.removeAllItems();
        try {
            List<Curso> cursos = new CursoDAO().listarTodos();
            for (Curso c : cursos) {
                String nombre = c.getNombreCurso() + " (ID: " + c.getCursoId() + ")";
                cmbCurso.addItem(nombre);
                mapaCursos.put(nombre, c.getCursoId());
            }
        } catch (Exception e) { cmbCurso.addItem("Error al cargar Cursos"); }
        
        mapaPeriodos = new HashMap<>();
        cmbPeriodo.removeAllItems();
        try {
            List<Periodos_Academicos> periodos = periodoControlador.obtenerTodosLosPeriodosAcademicos();
            for (Periodos_Academicos p : periodos) {
                String nombre = p.getNombrePeriodo() + " (ID: " + p.getPeriodoAcademicoId() + ")";
                cmbPeriodo.addItem(nombre);
                mapaPeriodos.put(nombre, p.getPeriodoAcademicoId());
            }
        } catch (Exception e) { cmbPeriodo.addItem("Error al cargar Periodos"); }
    }
    
    private Cortes_Evaluacion obtenerDatosCorte() throws NumberFormatException {
        Cortes_Evaluacion corte = new Cortes_Evaluacion();
        int filaSeleccionada = tablaCortes.getSelectedRow();
        if (filaSeleccionada != -1 && (btnActualizar.isEnabled() || btnEliminar.isEnabled())) {
             corte.setCorteEvaluacionId((int) tableModel.getValueAt(filaSeleccionada, 0));
        }
        
        corte.setNombreCorte(txtNombreCorte.getText().trim());
        corte.setPorcentaje(new BigDecimal(txtPorcentaje.getText().trim()));
        corte.setComentariosCorte(txtComentarios.getText().trim());
        
        corte.setCursoId(mapaCursos.get(cmbCurso.getSelectedItem()));
        corte.setPeriodoAcademicoId(mapaPeriodos.get(cmbPeriodo.getSelectedItem()));
        
        return corte;
    }

    private void cargarCortes() {
        tableModel.setRowCount(0); 
        List<Cortes_Evaluacion> cortes = new Cortes_EvaluacionDAO().findAll(); 
        for (Cortes_Evaluacion c : cortes) {
             tableModel.addRow(new Object[]{
                c.getCorteEvaluacionId(),
                c.getNombreCorte(),
                c.getPorcentaje(),
                c.getCursoId(),
                c.getPeriodoAcademicoId(),
                c.getComentariosCorte()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNombreCorte.setText("");
        txtPorcentaje.setText("");
        txtComentarios.setText("");
        if (cmbCurso.getItemCount() > 0) cmbCurso.setSelectedIndex(0);
        if (cmbPeriodo.getItemCount() > 0) cmbPeriodo.setSelectedIndex(0);
        tablaCortes.clearSelection();
    }
    
    private void agregarListeners() {
        tablaCortes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaCortes.getSelectedRow() != -1) {
                int fila = tablaCortes.getSelectedRow();
                txtNombreCorte.setText((String) tableModel.getValueAt(fila, 1));
                txtPorcentaje.setText(tableModel.getValueAt(fila, 2).toString());
                txtComentarios.setText((String) tableModel.getValueAt(fila, 5));

                int cursoId = (int) tableModel.getValueAt(fila, 3);
                int periodoId = (int) tableModel.getValueAt(fila, 4);
                
                mapaCursos.entrySet().stream().filter(entry -> entry.getValue().equals(cursoId))
                    .findFirst().ifPresent(entry -> cmbCurso.setSelectedItem(entry.getKey()));
                
                mapaPeriodos.entrySet().stream().filter(entry -> entry.getValue().equals(periodoId))
                    .findFirst().ifPresent(entry -> cmbPeriodo.setSelectedItem(entry.getKey()));
                
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
                Cortes_Evaluacion nuevoCorte = obtenerDatosCorte();
                if (nuevoCorte.getCursoId() == null || nuevoCorte.getPeriodoAcademicoId() == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione Curso y Periodo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (controlador.registrarCorteEvaluacion(nuevoCorte)) {
                    JOptionPane.showMessageDialog(this, "Corte registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarCortes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar corte.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El porcentaje debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
             try {
                Cortes_Evaluacion corteAActualizar = obtenerDatosCorte();
                if (corteAActualizar.getCorteEvaluacionId() != null && controlador.actualizarCorteEvaluacion(corteAActualizar)) {
                    JOptionPane.showMessageDialog(this, "Corte actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarCortes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar corte. Asegúrese de que el ID esté presente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El porcentaje debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaCortes.getSelectedRow();
            if (fila != -1) {
                int corteId = (int) tableModel.getValueAt(fila, 0);
                if (controlador.eliminarCorteEvaluacion(corteId)) {
                    JOptionPane.showMessageDialog(this, "Corte eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarCortes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar corte. Revise dependencias.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}