package com.controlacademico.Vista;

import com.controlacademico.Controlador.DocenteControlador;
import com.controlacademico.Modelo.Docente;
import com.controlacademico.Modelo.DocenteDAO;
import com.controlacademico.Modelo.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DocentePanel extends JPanel {

    private DocenteControlador controlador;

    // Componentes de la interfaz
    private JTable tablaDocentes;
    private DefaultTableModel tableModel;
    private JTextField txtNombre, txtIdentificacion, txtCorreo, txtTitulo, txtIdiomas, txtCertificaciones;
    private JComboBox<String> cmbTipoIdentificacion, cmbGenero;
    private JButton btnRegistrar, btnActualizar, btnEliminar, btnLimpiar;

    public DocentePanel() {
        // Inicializa el controlador con un DAO que usa la conexión
        this.controlador = new DocenteControlador(new DocenteDAO());
        
        setLayout(new BorderLayout(10, 10)); // Usamos BorderLayout para el panel

        // -----------------
        // Zona Superior (Formulario de Datos)
        // -----------------
        JPanel pnlFormulario = createFormPanel();
        add(pnlFormulario, BorderLayout.NORTH);

        // -----------------
        // Zona Central (Tabla de Docentes)
        // -----------------
        String[] columnas = {"ID", "Nombre", "Identificación", "Tipo ID", "Género", "Correo", "Título", "Idiomas", "Certificaciones"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacemos la tabla no editable
            }
        };
        tablaDocentes = new JTable(tableModel);
        tablaDocentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaDocentes);
        add(scrollPane, BorderLayout.CENTER);

        // -----------------
        // Zona Inferior (Botones de Acción)
        // -----------------
        JPanel pnlAcciones = createButtonPanel();
        add(pnlAcciones, BorderLayout.SOUTH);

        // Cargar datos iniciales
        cargarDocentes();
        
        // Agregar listeners
        agregarListeners();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10)); // 4 filas, 4 columnas
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Docente"));

        // Campos de texto e Inputs
        txtNombre = new JTextField(15);
        txtIdentificacion = new JTextField(15);
        cmbTipoIdentificacion = new JComboBox<>(new String[]{"Cédula", "Pasaporte", "Otro"});
        cmbGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        txtCorreo = new JTextField(15);
        txtTitulo = new JTextField(15);
        txtIdiomas = new JTextField(15);
        txtCertificaciones = new JTextField(15);

        // Añadir componentes al panel con sus etiquetas
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Identificación:"));
        panel.add(txtIdentificacion);

        panel.add(new JLabel("Tipo ID:"));
        panel.add(cmbTipoIdentificacion);
        panel.add(new JLabel("Género:"));
        panel.add(cmbGenero);

        panel.add(new JLabel("Correo:"));
        panel.add(txtCorreo);
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);

        panel.add(new JLabel("Idiomas:"));
        panel.add(txtIdiomas);
        panel.add(new JLabel("Certificaciones:"));
        panel.add(txtCertificaciones);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnRegistrar = new JButton("Registrar Docente");
        btnActualizar = new JButton("Actualizar Docente");
        btnEliminar = new JButton("Eliminar Docente");
        btnLimpiar = new JButton("Limpiar Campos");

        panel.add(btnRegistrar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        
        return panel;
    }

    // Método para mapear los campos del formulario a un objeto Docente
    private Docente obtenerDatosDocente() {
        Docente docente = new Docente();
        try {
            // Si hay una fila seleccionada, obtenemos el ID para la operación de Actualizar/Eliminar
            int filaSeleccionada = tablaDocentes.getSelectedRow();
            if (filaSeleccionada != -1 && btnActualizar.isEnabled() && btnEliminar.isEnabled()) {
                 // El ID está en la columna 0
                docente.setDocenteId((int) tableModel.getValueAt(filaSeleccionada, 0));
            }
        } catch (Exception e) {
             // Manejo de error si el ID no es un entero (no debería pasar con el modelo de tabla)
        }
        
        docente.setNombreDocente(txtNombre.getText().trim());
        docente.setIdentificacion(txtIdentificacion.getText().trim());
        docente.setTipoIdentificacion((String) cmbTipoIdentificacion.getSelectedItem());
        docente.setGenero((String) cmbGenero.getSelectedItem());
        docente.setCorreo(txtCorreo.getText().trim());
        docente.setTituloEstudios(txtTitulo.getText().trim());
        docente.setIdiomas(txtIdiomas.getText().trim());
        docente.setCertificaciones(txtCertificaciones.getText().trim());
        
        return docente;
    }

    private void cargarDocentes() {
        tableModel.setRowCount(0); // Limpiar tabla
        List<Docente> docentes = controlador.listarDocentes();
        for (Docente doc : docentes) {
            tableModel.addRow(new Object[]{
                doc.getDocenteId(),
                doc.getNombreDocente(),
                doc.getIdentificacion(),
                doc.getTipoIdentificacion(),
                doc.getGenero(),
                doc.getCorreo(),
                doc.getTituloEstudios(),
                doc.getIdiomas(),
                doc.getCertificaciones()
            });
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtCorreo.setText("");
        txtTitulo.setText("");
        txtIdiomas.setText("");
        txtCertificaciones.setText("");
        cmbTipoIdentificacion.setSelectedIndex(0);
        cmbGenero.setSelectedIndex(0);
        tablaDocentes.clearSelection();
    }
    
    private void agregarListeners() {
        // Listener para la tabla (para cargar datos en el formulario al seleccionar)
        tablaDocentes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaDocentes.getSelectedRow() != -1) {
                int fila = tablaDocentes.getSelectedRow();
                txtNombre.setText((String) tableModel.getValueAt(fila, 1));
                txtIdentificacion.setText((String) tableModel.getValueAt(fila, 2));
                cmbTipoIdentificacion.setSelectedItem(tableModel.getValueAt(fila, 3));
                cmbGenero.setSelectedItem(tableModel.getValueAt(fila, 4));
                txtCorreo.setText((String) tableModel.getValueAt(fila, 5));
                txtTitulo.setText((String) tableModel.getValueAt(fila, 6));
                txtIdiomas.setText((String) tableModel.getValueAt(fila, 7));
                txtCertificaciones.setText((String) tableModel.getValueAt(fila, 8));
                
                btnRegistrar.setEnabled(false);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
            } else {
                 btnRegistrar.setEnabled(true);
                 btnActualizar.setEnabled(false);
                 btnEliminar.setEnabled(false);
            }
        });

        // Listener para el botón Registrar
        btnRegistrar.addActionListener(e -> {
            Docente nuevoDocente = obtenerDatosDocente();
            if (controlador.registrarDocente(nuevoDocente)) {
                JOptionPane.showMessageDialog(this, "Docente registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDocentes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar docente. Revise los datos y la conexión a la BD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Listener para el botón Actualizar
        btnActualizar.addActionListener(e -> {
            Docente docenteAActualizar = obtenerDatosDocente();
            if (docenteAActualizar.getDocenteId() > 0) {
                 if (controlador.actualizarDocente(docenteAActualizar)) {
                    JOptionPane.showMessageDialog(this, "Docente actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDocentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar docente. Revise los datos y la conexión a la BD.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                 JOptionPane.showMessageDialog(this, "Seleccione un docente para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Listener para el botón Eliminar
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaDocentes.getSelectedRow();
            if (filaSeleccionada != -1) {
                int docenteId = (int) tableModel.getValueAt(filaSeleccionada, 0);
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este docente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (controlador.eliminarDocente(docenteId)) {
                        JOptionPane.showMessageDialog(this, "Docente eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        limpiarCampos();
                        cargarDocentes();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar docente. Revise si hay dependencias.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un docente para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Listener para el botón Limpiar
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }
}