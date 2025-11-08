package com.controlacademico.Vista;

import com.controlacademico.Controlador.ClasesControlador;
import com.controlacademico.Modelo.Clases;
import com.controlacademico.Modelo.ClasesDAO;
import com.controlacademico.Modelo.Curso;
import com.controlacademico.Modelo.CursoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClasesPanel extends JPanel {

    private ClasesControlador controlador;
    private CursoDAO cursoDAO;
    
    private JTable tablaClases;
    private DefaultTableModel tableModel;
    private JTextField txtNumeroClase, txtFechaClase, txtTemaClase;
    private JComboBox<String> cmbCurso;
    private JTextArea txtDescripcionClase, txtComentariosClase;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    private Map<String, Integer> mapaCursos;

    public ClasesPanel() {
        this.controlador = new ClasesControlador(new ClasesDAO());
        this.cursoDAO = new CursoDAO();

        setLayout(new BorderLayout(10, 10));

        // --- Formulario ---
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"ID", "Curso ID", "N° Clase", "Fecha", "Tema", "Descripción", "Comentarios"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaClases = new JTable(tableModel);
        tablaClases.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaClases.getColumnModel().getColumn(5).setPreferredWidth(150);
        tablaClases.getColumnModel().getColumn(6).setPreferredWidth(150);
        JScrollPane scrollPane = new JScrollPane(tablaClases);
        add(scrollPane, BorderLayout.CENTER);

        // --- Botones ---
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        cargarComboboxes();
        cargarClases();
        agregarListeners();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Clase"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1; panel.add(cmbCurso = new JComboBox<>(), gbc);
        gbc.gridx = 2; panel.add(new JLabel("N° Clase:"), gbc);
        gbc.gridx = 3; panel.add(txtNumeroClase = new JTextField(5), gbc);

        // Fila 2
        gbc.gridy = 1;
        gbc.gridx = 0; panel.add(new JLabel("Fecha (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; panel.add(txtFechaClase = new JTextField("YYYY-MM-DD", 10), gbc);
        gbc.gridx = 2; panel.add(new JLabel("Tema:"), gbc);
        gbc.gridx = 3; panel.add(txtTemaClase = new JTextField(15), gbc);

        // Fila 3 - Descripción
        gbc.gridy = 2;
        gbc.gridx = 0; panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtDescripcionClase = new JTextArea(3, 40);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcionClase);
        panel.add(scrollDesc, gbc);
        
        // Fila 4 - Comentarios
        gbc.gridy = 3;
        gbc.gridx = 0; gbc.gridwidth = 1; panel.add(new JLabel("Comentarios:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtComentariosClase = new JTextArea(3, 40);
        JScrollPane scrollComent = new JScrollPane(txtComentariosClase);
        panel.add(scrollComent, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        // ... (Implementación de botones de acción)
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnRegistrar = new JButton("Registrar Clase");
        btnActualizar = new JButton("Actualizar Clase");
        btnEliminar = new JButton("Eliminar Clase");
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
            List<Curso> cursos = cursoDAO.listarTodos();
            for (Curso c : cursos) {
                String nombre = c.getNombreCurso() + " (ID: " + c.getCursoId() + ")";
                cmbCurso.addItem(nombre);
                mapaCursos.put(nombre, c.getCursoId());
            }
        } catch (Exception e) { cmbCurso.addItem("Error al cargar Cursos"); }
    }
    
    private Clases obtenerDatosClase() throws NumberFormatException, IllegalArgumentException {
        Clases clase = new Clases();
        int filaSeleccionada = tablaClases.getSelectedRow();
        if (filaSeleccionada != -1 && (btnActualizar.isEnabled() || btnEliminar.isEnabled())) {
             clase.setClaseId((int) tableModel.getValueAt(filaSeleccionada, 0));
        }
        
        clase.setCursoId(mapaCursos.get(cmbCurso.getSelectedItem()));
        clase.setNumeroClase(Integer.parseInt(txtNumeroClase.getText().trim()));
        clase.setFechaClase(Date.valueOf(txtFechaClase.getText().trim()));
        clase.setTemaClase(txtTemaClase.getText().trim());
        clase.setDescripcionClase(txtDescripcionClase.getText().trim());
        clase.setComentariosClase(txtComentariosClase.getText().trim());
        
        return clase;
    }

    private void cargarClases() {
        tableModel.setRowCount(0); 
        List<Clases> clases = new ClasesDAO().findAll();
        for (Clases c : clases) {
             tableModel.addRow(new Object[]{
                c.getClaseId(),
                c.getCursoId(),
                c.getNumeroClase(),
                c.getFechaClase(),
                c.getTemaClase(),
                c.getDescripcionClase(),
                c.getComentariosClase()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNumeroClase.setText("");
        txtFechaClase.setText("YYYY-MM-DD");
        txtTemaClase.setText("");
        txtDescripcionClase.setText("");
        txtComentariosClase.setText("");
        if (cmbCurso.getItemCount() > 0) cmbCurso.setSelectedIndex(0);
        tablaClases.clearSelection();
    }
    
    private void agregarListeners() {
        tablaClases.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaClases.getSelectedRow() != -1) {
                int fila = tablaClases.getSelectedRow();
                txtNumeroClase.setText(tableModel.getValueAt(fila, 2).toString());
                txtFechaClase.setText(tableModel.getValueAt(fila, 3).toString());
                txtTemaClase.setText((String) tableModel.getValueAt(fila, 4));
                txtDescripcionClase.setText((String) tableModel.getValueAt(fila, 5));
                txtComentariosClase.setText((String) tableModel.getValueAt(fila, 6));

                int cursoId = (int) tableModel.getValueAt(fila, 1);
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
                Clases nuevaClase = obtenerDatosClase();
                if (nuevaClase.getCursoId() == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione un Curso.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (controlador.registrarClase(nuevaClase)) {
                    JOptionPane.showMessageDialog(this, "Clase registrada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarClases();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar clase.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El número de clase debe ser un entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnActualizar.addActionListener(e -> {
             try {
                Clases claseAActualizar = obtenerDatosClase();
                if (claseAActualizar.getClaseId() != null && controlador.actualizarClase(claseAActualizar)) {
                    JOptionPane.showMessageDialog(this, "Clase actualizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarClases();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar clase.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El número de clase debe ser un entero.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use YYYY-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
            int fila = tablaClases.getSelectedRow();
            if (fila != -1) {
                int claseId = (int) tableModel.getValueAt(fila, 0);
                if (controlador.eliminarClase(claseId)) {
                    JOptionPane.showMessageDialog(this, "Clase eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarClases();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar clase.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}