package com.controlacademico.Vista;

import com.controlacademico.Modelo.Conexion;
import javax.swing.SwingUtilities;

public class MainApp {

    public static void main(String[] args) {
        // Inicializa la conexión
        Conexion.getInstance();

        // Ejecuta la interfaz gráfica en el Event Dispatch Thread (EDT) de Swing
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView("Sistema de Control Académico");
            mainView.setVisible(true);
        });
    }
}