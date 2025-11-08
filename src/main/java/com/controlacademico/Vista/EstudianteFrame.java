package com.controlacademico.Vista;

import com.controlacademico.Modelo.Estudiante;
import com.controlacademico.Modelo.EstudianteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstudianteFrame extends JFrame {
    private EstudianteDAO estudianteDAO;

    private JTextField txtId, txtIdentificacion, txtNombre, txtCorreoInst, txtCorreoPers, txtTelefono, txtGenero, txtTipoDoc;
    private JCheckBox chkVocero;
    private JTextArea txtComentarios;
    private JTable tabla;

    public EstudianteFrame(EstudianteDAO dao) {
        this.estudianteDAO = dao;
        setTitle("Gestión de Estudiantes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));

        txtId = new JTextField();
        txtIdentificacion = new JTextField();
        txtNombre = new JTextField();
        txtCorreoInst = new JTextField();
        txtCorreoPers = new JTextField();
        txtTelefono = new JTextField();
        txtGenero = new JTextField();
        txtTipoDoc = new JTextField();
        chkVocero = new JCheckBox("Es vocero");
        txtComentarios = new JTextArea(3, 20);

        formPanel.add(new JLabel("ID (solo para actualizar/eliminar):"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Identificación:"));
        formPanel.add(txtIdentificacion);
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Correo Institucional:"));
        formPanel.add(txtCorreoInst);
        formPanel.add(new JLabel("Correo Personal:"));
        formPanel.add(txtCorreoPers);
        formPanel.add(new JLabel("Teléfono:"));
        formPanel.add(txtTelefono);
        formPanel.add(new JLabel("Género:"));
        formPanel.add(txtGenero);
        formPanel.add(new JLabel("Tipo Documento:"));
        formPanel.add(txtTipoDoc);
        formPanel.add(new JLabel("Comentarios:"));
        formPanel.add(new JScrollPane(txtComentarios));
        formPanel.add(chkVocero);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnListar = new JButton("Listar Todos");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnActualizar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnListar);

        // Tabla
        tabla = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Estudiantes"));

        // Acciones
        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnBuscar.addActionListener(e -> buscar());
        btnListar.addActionListener(e -> listar());

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void agregar() {
        Estudiante est = obtenerDatosFormulario();
        if (est == null) return;
        boolean ok = estudianteDAO.create(est);
        mensaje(ok, "creado");
    }

    private void actualizar() {
        Estudiante est = obtenerDatosFormulario();
        if (est == null || txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID para actualizar.");
            return;
        }
        est.setEstudiante_id(Integer.parseInt(txtId.getText()));
        boolean ok = estudianteDAO.update(est);
        mensaje(ok, "actualizado");
    }

    private void eliminar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID del estudiante a eliminar.");
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        boolean ok = estudianteDAO.delete(id);
        mensaje(ok, "eliminado");
    }

    private void buscar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID a buscar.");
            return;
        }
        Estudiante est = estudianteDAO.findById(Integer.parseInt(txtId.getText()));
        if (est != null) mostrarEnFormulario(est);
        else JOptionPane.showMessageDialog(this, "Estudiante no encontrado.");
    }

    private void listar() {
        List<Estudiante> lista = estudianteDAO.findAll();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{
                "ID", "Identificación", "Nombre", "Correo Inst.", "Correo Pers.", "Teléfono", "Vocero", "Tipo Doc", "Género"
        });
        for (Estudiante e : lista) {
            model.addRow(new Object[]{
                    e.getEstudiante_id(), e.getIdentificacion(), e.getNombre(),
                    e.getCorreo_institucional(), e.getCorreo_personal(),
                    e.getTelefono(), e.isEs_vocero(), e.getTipo_documento(), e.getGenero()
            });
        }
        tabla.setModel(model);
    }

    private Estudiante obtenerDatosFormulario() {
        if (txtIdentificacion.getText().isEmpty() || txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campos obligatorios: Identificación y Nombre");
            return null;
        }
        Estudiante e = new Estudiante();
        e.setIdentificacion(txtIdentificacion.getText());
        e.setNombre(txtNombre.getText());
        e.setCorreo_institucional(txtCorreoInst.getText());
        e.setCorreo_personal(txtCorreoPers.getText());
        e.setTelefono(txtTelefono.getText());
        e.setGenero(txtGenero.getText());
        e.setTipo_documento(txtTipoDoc.getText());
        e.setComentarios(txtComentarios.getText());
        e.setEs_vocero(chkVocero.isSelected());
        return e;
    }

    private void mostrarEnFormulario(Estudiante e) {
        txtId.setText(String.valueOf(e.getEstudiante_id()));
        txtIdentificacion.setText(e.getIdentificacion());
        txtNombre.setText(e.getNombre());
        txtCorreoInst.setText(e.getCorreo_institucional());
        txtCorreoPers.setText(e.getCorreo_personal());
        txtTelefono.setText(e.getTelefono());
        txtGenero.setText(e.getGenero());
        txtTipoDoc.setText(e.getTipo_documento());
        txtComentarios.setText(e.getComentarios());
        chkVocero.setSelected(e.isEs_vocero());
    }

    private void mensaje(boolean ok, String accion) {
        JOptionPane.showMessageDialog(this,
                ok ? "Estudiante " + accion + " correctamente." :
                        "No se pudo " + accion + " el estudiante.");
    }
}

