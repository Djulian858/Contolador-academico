
package com.controlacademico.Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
 
public class Conexion {
 
    private static Conexion instance;
    private Connection connection;
 
    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
 
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));
 
            String url = props.getProperty("URL");
            String user = props.getProperty("USER");
            String password = props.getProperty("PASSWORD");
 
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida correctamente a la base de datos.");
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    public static synchronized Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }
 
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("⚠️ Conexión cerrada, reintentando...");
                instance = new Conexion();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
 
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
 

                