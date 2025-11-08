package com.controlacademico.Vista;

import com.controlacademico.Controlador.Periodos_AcademicosControlador;
import com.controlacademico.Modelo.Periodos_Academicos;
import com.controlacademico.Modelo.Periodos_AcademicosDAO;
import com.controlacademico.Modelo.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PeriodosAcademicosPanel extends JPanel {

    private Periodos_AcademicosControlador controlador;
    private JTable tablaPeriodos;
    private DefaultTableModel tableModel;
    private JTextField txtNombrePeriodo, txtFechaInicio, txtFechaFin;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    public PeriodosAcademicosPanel() {
        this.controlador = new Periodos_AcademicosControlador(new Periodos_AcademicosDAO(Conexion.getInstance().getConnection()));
        
        setLayout(new BorderLayout(10, 10));

        // --- Formulario ---
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Nombre Periodo", "Fecha Inicio", "Fecha Fin"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaPeriodos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaPeriodos);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones ---
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        cargarPeriodos();
        agregarListeners();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Periodo Académico"));

        txtNombrePeriodo = new JTextField(15);
        txtFechaInicio = new JTextField("YYYY-MM-DD", 10);
        txtFechaFin = new JTextField("YYYY-MM-DD", 10);

        panel.add(new JLabel("Nombre del Periodo:"));
        panel.add(txtNombrePeriodo);
        panel.add(new JLabel("Fecha de Inicio (YYYY-MM-DD):"));
        panel.add(txtFechaInicio);
        panel.add(new JLabel("Fecha de Fin (YYYY-MM-DD):"));
        panel.add(txtFechaFin);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRegistrar = new JButton("Registrar Periodo");
        btnActualizar = new JButton("Actualizar Periodo");
        btnEliminar = new JButton("Eliminar Periodo");
        btnLimpiar = new JButton("Limpiar Campos");

        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        return panel;
    }

    private Periodos_Academicos obtenerDatosPeriodo() throws DateTimeParseException {
        Periodos_Academicos periodo = new Periodos_Academicos();
        int filaSeleccionada = tablaPeriodos.getSelectedRow();
        if (filaSeleccionada != -1 && (btnActualizar.isEnabled() || btnEliminar.isEnabled())) {
             periodo.setPeriodoAcademicoId((int) tableModel.getValueAt(filaSeleccionada, 0));
        }
        
        periodo.setNombrePeriodo(txtNombrePeriodo.getText().trim());
        periodo.setFechaInicio(LocalDate.parse(txtFechaInicio.getText().trim()));
        periodo.setFechaFin(LocalDate.parse(txtFechaFin.getText().trim()));
        
        return periodo;
    }

    private void cargarPeriodos() {
        tableModel.setRowCount(0); 
        List<Periodos_Academicos> periodos = controlador.obtenerTodosLosPeriodosAcademicos();
        for (Periodos_Academicos p : periodos) {
            // Nota: Se asume que Periodos_AcademicosDAO.listarTodos() devuelve el ID, aunque no está en el mapRow de tu DAO.
            // Para fines de esta GUI, se asume que el ID se puede obtener.
             tableModel.addRow(new Object[]{
                p.getPeriodoAcademicoId(),
                p.getNombrePeriodo(),
                p.getFechaInicio(),
                p.getFechaFin()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNombrePeriodo.setText("");
        txtFechaInicio.setText("YYYY-MM-DD");
        txtFechaFin.setText("YYYY-MM-DD");
        tablaPeriodos.clearSelection();
    }
    
    private void agregarListeners() {
        tablaPeriodos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPeriodos.getSelectedRow() != -1) {
                int fila = tablaPeriodos.getSelectedRow();
                txtNombrePeriodo.setText((String) tableModel.getValueAt(fila, 1));
                txtFechaInicio.setText(tableModel.getValueAt(fila, 2).toString());
                txtFechaFin.setText(tableModel.getValueAt(fila, 3).toString());
                
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
                Periodos_Academicos nuevoPeriodo = obtenerDatosPeriodo();
                if (controlador.crearPeriodoAcademico(nuevoPeriodo)) {
                    JOptionPane.showMessageDialog(this, "Periodo registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarPeriodos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar periodo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            try {
                Periodos_Academicos periodoAActualizar = obtenerDatosPeriodo();
                if (periodoAActualizar.getPeriodoAcademicoId() != null && controlador.actualizarPeriodoAcademico(periodoAActualizar)) {
                    JOptionPane.showMessageDialog(this, "Periodo actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarPeriodos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar periodo. Asegúrese de que el ID esté presente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaPeriodos.getSelectedRow();
            if (fila != -1) {
                int periodoId = (int) tableModel.getValueAt(fila, 0);
                if (controlador.eliminarPeriodoAcademico(periodoId)) {
                    JOptionPane.showMessageDialog(this, "Periodo eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarPeriodos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar periodo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}
