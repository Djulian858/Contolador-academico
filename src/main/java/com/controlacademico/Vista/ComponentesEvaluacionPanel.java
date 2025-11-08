package com.controlacademico.Vista;

import com.controlacademico.Controlador.Componentes_EvaluacionControlador;
import com.controlacademico.Controlador.Cortes_EvaluacionControlador;
import com.controlacademico.Modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentesEvaluacionPanel extends JPanel {

    private Componentes_EvaluacionControlador controlador;
    private Cortes_EvaluacionControlador corteControlador;
    
    private JTable tablaComponentes;
    private DefaultTableModel tableModel;
    private JTextField txtNombreComponente, txtPorcentaje;
    private JComboBox<String> cmbCorte;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    private Map<String, Integer> mapaCortes;

    public ComponentesEvaluacionPanel() {
        this.controlador = new Componentes_EvaluacionControlador(new Componentes_EvaluacionDAO(Conexion.getInstance().getConnection()));
        this.corteControlador = new Cortes_EvaluacionControlador(new Cortes_EvaluacionDAO());

        setLayout(new BorderLayout(10, 10));

        // --- Formulario ---
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Nombre Componente", "Porcentaje", "Corte ID"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaComponentes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaComponentes);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones ---
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        cargarComboboxes();
        cargarComponentes();
        agregarListeners();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Componente de Evaluación"));

        txtNombreComponente = new JTextField(15);
        txtPorcentaje = new JTextField(5);
        cmbCorte = new JComboBox<>();

        panel.add(new JLabel("Nombre del Componente:"));
        panel.add(txtNombreComponente);
        panel.add(new JLabel("Porcentaje (%):"));
        panel.add(txtPorcentaje);
        panel.add(new JLabel("Corte de Evaluación:"));
        panel.add(cmbCorte);

        return panel;
    }

    private JPanel createButtonPanel() {
        // ... (Implementación de botones de acción)
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRegistrar = new JButton("Registrar Componente");
        btnActualizar = new JButton("Actualizar Componente");
        btnEliminar = new JButton("Eliminar Componente");
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
        mapaCortes = new HashMap<>();
        cmbCorte.removeAllItems();
        try {
            List<Cortes_Evaluacion> cortes = corteControlador.findAll(); 
            for (Cortes_Evaluacion c : cortes) {
                String nombre = c.getNombreCorte() + " (ID: " + c.getCorteEvaluacionId() + ")";
                cmbCorte.addItem(nombre);
                mapaCortes.put(nombre, c.getCorteEvaluacionId());
            }
        } catch (Exception e) { 
             cmbCorte.addItem("Error al cargar Cortes"); 
             System.err.println("Error cargando cortes: " + e.getMessage());
        }
    }
    
    private Componentes_Evaluacion obtenerDatosComponente() throws NumberFormatException {
        Componentes_Evaluacion componente = new Componentes_Evaluacion();
        int filaSeleccionada = tablaComponentes.getSelectedRow();
        if (filaSeleccionada != -1 && (btnActualizar.isEnabled() || btnEliminar.isEnabled())) {
             componente.setComponenteEvaluacionId((int) tableModel.getValueAt(filaSeleccionada, 0));
        }
        
        componente.setNombre_componente(txtNombreComponente.getText().trim());
        componente.setPorcentaje(new BigDecimal(txtPorcentaje.getText().trim()));
        
        componente.setCorteEvaluacionId(mapaCortes.get(cmbCorte.getSelectedItem()));
        
        return componente;
    }

    private void cargarComponentes() {
        tableModel.setRowCount(0); 
        List<Componentes_Evaluacion> componentes = new Componentes_EvaluacionDAO(Conexion.getInstance().getConnection()).obtenerTodosLosComponentes(); 
        for (Componentes_Evaluacion c : componentes) {
             tableModel.addRow(new Object[]{
                c.getComponenteEvaluacionId(),
                c.getNombre_componente(),
                c.getPorcentaje(),
                c.getCorteEvaluacionId()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNombreComponente.setText("");
        txtPorcentaje.setText("");
        if (cmbCorte.getItemCount() > 0) cmbCorte.setSelectedIndex(0);
        tablaComponentes.clearSelection();
    }
    
    private void agregarListeners() {
        tablaComponentes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaComponentes.getSelectedRow() != -1) {
                int fila = tablaComponentes.getSelectedRow();
                txtNombreComponente.setText((String) tableModel.getValueAt(fila, 1));
                txtPorcentaje.setText(tableModel.getValueAt(fila, 2).toString());
                
                int corteId = (int) tableModel.getValueAt(fila, 3);
                mapaCortes.entrySet().stream().filter(entry -> entry.getValue().equals(corteId))
                    .findFirst().ifPresent(entry -> cmbCorte.setSelectedItem(entry.getKey()));
                
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
                Componentes_Evaluacion nuevoComponente = obtenerDatosComponente();
                if (nuevoComponente.getCorteEvaluacionId() == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione un Corte de Evaluación.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (controlador.registrarComponenteEvaluacion(nuevoComponente)) {
                    JOptionPane.showMessageDialog(this, "Componente registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarComponentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar componente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El porcentaje debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
             try {
                Componentes_Evaluacion componenteAActualizar = obtenerDatosComponente();
                if (componenteAActualizar.getComponenteEvaluacionId() != null && controlador.actualizarComponenteEvaluacion(componenteAActualizar)) {
                    JOptionPane.showMessageDialog(this, "Componente actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarComponentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar componente. Asegúrese de que el ID esté presente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El porcentaje debe ser un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaComponentes.getSelectedRow();
            if (fila != -1) {
                int componenteId = (int) tableModel.getValueAt(fila, 0);
                if (controlador.eliminarComponenteEvaluacion(componenteId)) {
                    JOptionPane.showMessageDialog(this, "Componente eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarComponentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar componente. Revise dependencias.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}
