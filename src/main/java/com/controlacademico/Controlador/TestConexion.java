package com.controlacademico.Controlador;

import java.sql.Connection;

import com.controlacademico.Modelo.Conexion;

public class TestConexion {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        if (conn != null) {
            System.out.println("La conexión a la base de datos fue exitosa.");
            conexion.cerrarConexion();
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
    }
    
}
