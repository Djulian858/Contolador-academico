package com.controlacademico.Modelo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Componentes_EvaluacionDAO {
    private Connection conn;

    public Componentes_EvaluacionDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Componentes_Evaluacion> obtenerComponentesPorCorte(int corteEvaluacionId) throws SQLException {
        List<Componentes_Evaluacion> componentes = new ArrayList<>();
        String sql = "SELECT * FROM Componentes_Evaluacion WHERE corte_evaluacion_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, corteEvaluacionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Componentes_Evaluacion componente = new Componentes_Evaluacion();
                    componente.setComponenteEvaluacionId(rs.getInt("componente_evaluacion_id"));
                    componente.setCorteEvaluacionId(rs.getInt("corte_evaluacion_id"));
                    componente.setNombre_componente(rs.getString("nombre_componente"));
                    componente.setPorcentaje(rs.getBigDecimal("porcentaje"));
                    componentes.add(componente);
                }
            }
        }
        return componentes;
    }

    public boolean crearComponente(Componentes_Evaluacion componente) {
        String sql = "INSERT INTO Componentes_Evaluacion (corte_evaluacion_id, nombre_componente, porcentaje) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, componente.getCorteEvaluacionId());
            stmt.setString(2, componente.getNombre_componente());
            stmt.setBigDecimal(3, componente.getPorcentaje());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear componente de evaluación: " + e.getMessage());
            return false;
        }
    }
// Método para eliminar un componente de evaluación por su ID
    public boolean eliminarComponente(int componenteEvaluacionId) {
        String sql = "DELETE FROM Componentes_Evaluacion WHERE componente_evaluacion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, componenteEvaluacionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar componente de evaluación: " + e.getMessage());
            return false;
        }
    }
    
    // Método para actualizar un componente de evaluación existente
    public boolean actualizarComponente(Componentes_Evaluacion componente) {
        String sql = "UPDATE Componentes_Evaluacion SET corte_evaluacion_id = ?, nombre_componente = ?, porcentaje = ? WHERE componente_evaluacion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, componente.getCorteEvaluacionId());
            stmt.setString(2, componente.getNombre_componente());
            stmt.setBigDecimal(3, componente.getPorcentaje());
            stmt.setInt(4, componente.getComponenteEvaluacionId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar componente de evaluación: " + e.getMessage());
            return false;
        }
}

//Metodo para obtener un componente de evaluación por su ID
    public Componentes_Evaluacion obtenerComponentePorId(int componenteEvaluacionId) {
        String sql = "SELECT * FROM Componentes_Evaluacion WHERE componente_evaluacion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, componenteEvaluacionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Componentes_Evaluacion componente = new Componentes_Evaluacion();
                    componente.setComponenteEvaluacionId(rs.getInt("componente_evaluacion_id"));
                    componente.setCorteEvaluacionId(rs.getInt("corte_evaluacion_id"));
                    componente.setNombre_componente(rs.getString("nombre_componente"));
                    componente.setPorcentaje(rs.getBigDecimal("porcentaje"));
                    return componente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener componente de evaluación: " + e.getMessage());
        }
        return null;
    }

    // Método para obtener todos los componentes de evaluación
    public List<Componentes_Evaluacion> obtenerTodosLosComponentes() {
        List<Componentes_Evaluacion> componentes = new ArrayList<>();
        String sql = "SELECT * FROM Componentes_Evaluacion";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Componentes_Evaluacion componente = new Componentes_Evaluacion();
                componente.setComponenteEvaluacionId(rs.getInt("componente_evaluacion_id"));
                componente.setCorteEvaluacionId(rs.getInt("corte_evaluacion_id"));
                componente.setNombre_componente(rs.getString("nombre_componente"));
                componente.setPorcentaje(rs.getBigDecimal("porcentaje"));
                componentes.add(componente);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los componentes de evaluación: " + e.getMessage());
        }
        return componentes;
    }

    
}
