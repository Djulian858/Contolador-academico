package com.controlacademico.Vista;

import com.controlacademico.Modelo.Docente;
import com.controlacademico.Modelo.DocenteDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DocenteFrame extends JFrame {
    private DocenteDAO docenteDAO;

    private JTextField txtId, txtNombre, txtIdentificacion, txtTipoIdent, txtGenero, txtCorreo,
            txtTitulo, txtIdiomas, txtCertificaciones;
    private JTable tabla;

    public DocenteFrame(DocenteDAO dao) {
        this.docenteDAO = dao;

        setTitle("Gestión de Docentes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ======== Panel de formulario ========
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Docente"));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtIdentificacion = new JTextField();
        txtTipoIdent = new JTextField();
        txtGenero = new JTextField();
        txtCorreo = new JTextField();
        txtTitulo = new JTextField();
        txtIdiomas = new JTextField();
        txtCertificaciones = new JTextField();

        formPanel.add(new JLabel("ID (solo para actualizar/eliminar):"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Nombre del Docente:"));
        formPanel.add(txtNombre);
        formPanel.add(new JLabel("Identificación:"));
        formPanel.add(txtIdentificacion);
        formPanel.add(new JLabel("Tipo de Identificación:"));
        formPanel.add(txtTipoIdent);
        formPanel.add(new JLabel("Género:"));
        formPanel.add(txtGenero);
        formPanel.add(new JLabel("Correo:"));
        formPanel.add(txtCorreo);
        formPanel.add(new JLabel("Título de Estudios:"));
        formPanel.add(txtTitulo);
        formPanel.add(new JLabel("Idiomas:"));
        formPanel.add(txtIdiomas);
        formPanel.add(new JLabel("Certificaciones:"));
        formPanel.add(txtCertificaciones);

        // ======== Panel de botones ========
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
        // ======== Tabla para mostrar docentes ========
        tabla = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(tabla);
        add(tableScrollPane, BorderLayout.CENTER);
    }

}
