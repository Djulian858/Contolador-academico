package com.controlacademico.Vista;



import com.controlacademico.Modelo.*;

import clojure.tools.nrepl.Connection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class EstudianteFrame extends JFrame {

    private JTextField txtId, txtIdentificacion, txtNombre, txtCorreoInstitucional, txtCorreoPersonal, txtTelefono, txtTipoDocumento, txtGenero;
    private JCheckBox chkVocero;
    private JTextArea txtComentarios;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar;
    private JTable tabla;
    private EstudianteDAO dao;

    public EstudianteFrame(Connection conexion) {
        setTitle("Gestión de Estudiantes");
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dao = new EstudianteDAO(conexion);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(30, 20, 100, 25);
        add(lblId);
        txtId = new JTextField();
        txtId.setBounds(150, 20, 100, 25);
        add(txtId);

        JLabel lblIdent = new JLabel("Identificación:");
        lblIdent.setBounds(30, 60, 100, 25);
        add(lblIdent);
        txtIdentificacion = new JTextField();
        txtIdentificacion.setBounds(150, 60, 150, 25);
        add(txtIdentificacion);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 100, 100, 25);
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(150, 100, 150, 25);
        add(txtNombre);

        JLabel lblCorreoI = new JLabel("Correo Institucional:");
        lblCorreoI.setBounds(30, 140, 130, 25);
        add(lblCorreoI);
        txtCorreoInstitucional = new JTextField();
        txtCorreoInstitucional.setBounds(170, 140, 200, 25);
        add(txtCorreoInstitucional);

        JLabel lblCorreoP = new JLabel("Correo Personal:");
        lblCorreoP.setBounds(30, 180, 120, 25);
        add(lblCorreoP);
        txtCorreoPersonal = new JTextField();
        txtCorreoPersonal.setBounds(170, 180, 200, 25);
        add(txtCorreoPersonal);

        JLabel lblTel = new JLabel("Teléfono:");
        lblTel.setBounds(30, 220, 100, 25);
        add(lblTel);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(150, 220, 150, 25);
        add(txtTelefono);

        JLabel lblVocero = new JLabel("¿Vocero?:");
        lblVocero.setBounds(30, 260, 100, 25);
        add(lblVocero);
        chkVocero = new JCheckBox();
        chkVocero.setBounds(150, 260, 50, 25);
        add(chkVocero);

        JLabel lblDoc = new JLabel("Tipo Documento:");
        lblDoc.setBounds(30, 300, 120, 25);
        add(lblDoc);
        txtTipoDocumento = new JTextField();
        txtTipoDocumento.setBounds(150, 300, 150, 25);
        add(txtTipoDocumento);

        JLabel lblGen = new JLabel("Género:");
        lblGen.setBounds(30, 340, 100, 25);
        add(lblGen);
        txtGenero = new JTextField();
        txtGenero.setBounds(150, 340, 150, 25);
        add(txtGenero);

        JLabel lblCom = new JLabel("Comentarios:");
        lblCom.setBounds(30, 380, 100, 25);
        add(lblCom);
        txtComentarios = new JTextArea();
        JScrollPane scrollCom = new JScrollPane(txtComentarios);
        scrollCom.setBounds(150, 380, 200, 60);
        add(scrollCom);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(400, 40, 100, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(400, 80, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(400, 120, 100, 30);
        add(btnEliminar);

        btnListar = new JButton("Listar");
        btnListar.setBounds(400, 160, 100, 30);
        add(btnListar);

        tabla = new JTable();
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 460, 720, 100);
        add(scroll);

        // Acciones
        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnListar.addActionListener(e -> listar());
    }

    private void agregar() {
        Estudiante est = new Estudiante();
        est.setEstudiante_id(Integer.parseInt(txtIdentificacion.getText()));
        est.setNombre(txtNombre.getText());
        est.setCorreo_institucional(txtCorreoInstitucional.getText());
        est.setCorreo_personal(txtCorreoPersonal.getText());
        est.setTelefono(txtTelefono.getText());
        est.setEs_vocero(chkVocero.isSelected());
        est.setComentarios(txtComentarios.getText());
        est.setTipo_documento(txtTipoDocumento.getText());
        est.setGenero(txtGenero.getText());

        if (dao.create(est)) {
            JOptionPane.showMessageDialog(this, "Estudiante agregado correctamente");
            listar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar estudiante");
        }
    }

    private void actualizar() {
        Estudiante est = new Estudiante();
        est.setEstudiante_id(Integer.parseInt(txtId.getText()));
        est.setIdentificacion(txtIdentificacion.getText());
        est.setNombre(txtNombre.getText());
        est.setCorreo_institucional(txtCorreoInstitucional.getText());
        est.setCorreo_personal(txtCorreoPersonal.getText());
        est.setTelefono(txtTelefono.getText());
        est.setEs_vocero(chkVocero.isSelected());
        est.setComentarios(txtComentarios.getText());
        est.setTipo_documento(txtTipoDocumento.getText());
        est.setGenero(txtGenero.getText());

        if (dao.update(est)) {
            JOptionPane.showMessageDialog(this, "Estudiante actualizado correctamente");
            listar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar estudiante");
        }
    }

    private void eliminar() {
        int id = Integer.parseInt(txtId.getText());
        if (dao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente");
            listar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar estudiante");
        }
    }

    private void listar() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Identificación");
        model.addColumn("Nombre");
        model.addColumn("Correo Institucional");
        model.addColumn("Correo Personal");
        model.addColumn("Teléfono");
        model.addColumn("Vocero");
        model.addColumn("Tipo Documento");
        model.addColumn("Género");

        List<Estudiante> lista = dao.findAll();
        for (Estudiante e : lista) {
            model.addRow(new Object[]{
                e.getEstudiante_id(),
                e.getIdentificacion(),
                e.getNombre(),
                e.getCorreo_institucional(),
                e.getCorreo_personal(),
                e.getTelefono(),
                e.isEs_vocero(),
                e.getTipo_documento(),
                e.getGenero()
            });
        }

        tabla.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstudianteFrame().setVisible(true));
    }
}
