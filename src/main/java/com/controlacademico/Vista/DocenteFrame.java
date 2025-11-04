package com.controlacademico.Vista;

import javax.swing.*;
import java.awt.event.*;
import com.controlacademico.Modelo.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.sql.Connection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;   


public class DocenteFrame {
    private JTextField txtNombre, txtIdentificacion, txtTipo_Identificacion, txtGenero, txtCorreo, txtTitulo_Estudios, txtIdiomas, txtCertificaciones;
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar;
    private JTable tabla;
    private DocenteDAO dao;
    public DocenteFrame(Connection conexion) {
        setTitle("Gestión de Docentes");
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dao = new DocenteDAO(conexion);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 20, 100, 25);
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(150, 20, 150, 25);
        add(txtNombre);

        // Resto de los componentes de la interfaz gráfica...

    }

    private void setDefaultCloseOperation(int disposeOnClose) {
    }
    private void setLayout(Object object) {
    }
    private void setSize(int i, int j) {
    }
    private void setTitle(String string) {
    }
    private void add(JLabel lblNombre) {
    }
    private void setLocationRelativeTo(Object object) {
    }

    private void setDefaultCloseOperation(int disposeOnClose) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(disposeOnClose);

    }

    private void setLayout(Object object) {
        getContentPane().setLayout((LayoutManager) object);
    }
    private void setSize(int i, int j) {
        setSize(i, j);
    }
}
