package com.controlacademico.Vista;

import com.controlacademico.Controlador.EstudianteControlador;
import com.controlacademico.Modelo.Estudiante;
import com.controlacademico.Modelo.EstudianteDAO;
import com.controlacademico.Modelo.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstudiantePanel extends JPanel {

    private EstudianteControlador controlador;
    private JTable tablaEstudiantes;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtIdentificacion, txtCorreoInst, txtCorreoPers, txtTelefono;
    private JComboBox<String> cmbTipoDocumento, cmbGenero;
    private JCheckBox chkEsVocero;
    private JTextArea txtComentarios;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    public EstudiantePanel() {
        this.controlador = new EstudianteControlador(new EstudianteDAO(Conexion.getInstance().getConnection())); // Nota: EstudianteDAO requiere Connection
        
        setLayout(new BorderLayout(10, 10));

        // --- Formulario ---
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Nombre", "Identificación", "Tipo Doc.", "Género", "Correo Inst.", "Correo Pers.", "Teléfono", "Vocero", "Comentarios"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEstudiantes = new JTable(tableModel);
        tablaEstudiantes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Permite barra horizontal
        tablaEstudiantes.getColumnModel().getColumn(5).setPreferredWidth(150);
        tablaEstudiantes.getColumnModel().getColumn(6).setPreferredWidth(150);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones ---
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        cargarEstudiantes();
        agregarListeners();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; panel.add(txtNombre = new JTextField(15), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 3; panel.add(txtIdentificacion = new JTextField(15), gbc);

        // Fila 2
        gbc.gridy = 1;
        gbc.gridx = 0; panel.add(new JLabel("Tipo Documento:"), gbc);
        gbc.gridx = 1; panel.add(cmbTipoDocumento = new JComboBox<>(new String[]{"TI", "CC", "Pasaporte", "CE"}), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Género:"), gbc);
        gbc.gridx = 3; panel.add(cmbGenero = new JComboBox<>(new String[]{"M", "F", "Otro"}), gbc);

        // Fila 3
        gbc.gridy = 2;
        gbc.gridx = 0; panel.add(new JLabel("Correo Inst.:"), gbc);
        gbc.gridx = 1; panel.add(txtCorreoInst = new JTextField(15), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Correo Pers.:"), gbc);
        gbc.gridx = 3; panel.add(txtCorreoPers = new JTextField(15), gbc);

        // Fila 4
        gbc.gridy = 3;
        gbc.gridx = 0; panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; panel.add(txtTelefono = new JTextField(15), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Es Vocero:"), gbc);
        gbc.gridx = 3; panel.add(chkEsVocero = new JCheckBox(), gbc);

        // Fila 5 - Comentarios (Ocupa más espacio)
        gbc.gridy = 4;
        gbc.gridx = 0; panel.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; 
        txtComentarios = new JTextArea(3, 40);
        JScrollPane scrollComentarios = new JScrollPane(txtComentarios);
        panel.add(scrollComentarios, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRegistrar = new JButton("Registrar Estudiante");
        btnActualizar = new JButton("Actualizar Estudiante");
        btnEliminar = new JButton("Eliminar Estudiante");
        btnLimpiar = new JButton("Limpiar Campos");

        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        return panel;
    }

    private Estudiante obtenerDatosEstudiante() {
        Estudiante estudiante = new Estudiante();
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada != -1 && (btnActualizar.isEnabled() || btnEliminar.isEnabled())) {
             estudiante.setEstudiante_id((int) tableModel.getValueAt(filaSeleccionada, 0));
        }
        
        estudiante.setNombre(txtNombre.getText().trim());
        estudiante.setIdentificacion(txtIdentificacion.getText().trim());
        estudiante.setTipo_documento((String) cmbTipoDocumento.getSelectedItem());
        estudiante.setGenero((String) cmbGenero.getSelectedItem());
        estudiante.setCorreo_institucional(txtCorreoInst.getText().trim());
        estudiante.setCorreo_personal(txtCorreoPers.getText().trim());
        estudiante.setTelefono(txtTelefono.getText().trim());
        estudiante.setEs_vocero(chkEsVocero.isSelected());
        estudiante.setComentarios(txtComentarios.getText().trim());
        
        return estudiante;
    }

    private void cargarEstudiantes() {
        tableModel.setRowCount(0); 
        List<Estudiante> estudiantes = controlador.findAll();
        for (Estudiante est : estudiantes) {
            tableModel.addRow(new Object[]{
                est.getEstudiante_id(),
                est.getNombre(),
                est.getIdentificacion(),
                est.getTipo_documento(),
                est.getGenero(),
                est.getCorreo_institucional(),
                est.getCorreo_personal(),
                est.getTelefono(),
                est.isEs_vocero() ? "Sí" : "No",
                est.getComentarios()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtCorreoInst.setText("");
        txtCorreoPers.setText("");
        txtTelefono.setText("");
        txtComentarios.setText("");
        chkEsVocero.setSelected(false);
        cmbTipoDocumento.setSelectedIndex(0);
        cmbGenero.setSelectedIndex(0);
        tablaEstudiantes.clearSelection();
    }
    
    private void agregarListeners() {
        tablaEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEstudiantes.getSelectedRow() != -1) {
                int fila = tablaEstudiantes.getSelectedRow();
                txtNombre.setText((String) tableModel.getValueAt(fila, 1));
                txtIdentificacion.setText((String) tableModel.getValueAt(fila, 2));
                cmbTipoDocumento.setSelectedItem(tableModel.getValueAt(fila, 3));
                cmbGenero.setSelectedItem(tableModel.getValueAt(fila, 4));
                txtCorreoInst.setText((String) tableModel.getValueAt(fila, 5));
                txtCorreoPers.setText((String) tableModel.getValueAt(fila, 6));
                txtTelefono.setText((String) tableModel.getValueAt(fila, 7));
                chkEsVocero.setSelected("Sí".equals(tableModel.getValueAt(fila, 8)));
                txtComentarios.setText((String) tableModel.getValueAt(fila, 9));
                
                btnRegistrar.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
            } else {
                 btnRegistrar.setEnabled(true);
                 btnActualizar.setEnabled(false);
                 btnEliminar.setEnabled(false);
            }
        });

        btnRegistrar.addActionListener(e -> {
            Estudiante nuevoEstudiante = obtenerDatosEstudiante();
            if (controlador.registrarEstudiante(nuevoEstudiante)) {
                JOptionPane.showMessageDialog(this, "Estudiante registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarEstudiantes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
            Estudiante estudianteAActualizar = obtenerDatosEstudiante();
            if (estudianteAActualizar.getEstudiante_id() > 0) {
                 if (controlador.actualizarEstudiante(estudianteAActualizar)) {
                    JOptionPane.showMessageDialog(this, "Estudiante actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarEstudiantes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                 JOptionPane.showMessageDialog(this, "Seleccione un estudiante para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaEstudiantes.getSelectedRow();
            if (filaSeleccionada != -1) {
                int estudianteId = (int) tableModel.getValueAt(filaSeleccionada, 0);
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar este estudiante?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (controlador.eliminarEstudiante(estudianteId)) {
                        JOptionPane.showMessageDialog(this, "Estudiante eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        limpiarCampos();
                        cargarEstudiantes();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un estudiante para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}
