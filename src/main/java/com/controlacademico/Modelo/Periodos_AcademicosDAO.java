package com.controlacademico.Modelo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Periodos_AcademicosDAO {

    private Connection conn;

    public Periodos_AcademicosDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean crearPeriodos_Academicos(Periodos_Academicos periodoAcademico) {
        String sql = "INSERT INTO Periodos_Academicos (nombre_periodo, fecha_inicio, fecha_fin) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, periodoAcademico.getNombrePeriodo());
            stmt.setDate(2, Date.valueOf(periodoAcademico.getFechaInicio()));
            stmt.setDate(3, Date.valueOf(periodoAcademico.getFechaFin()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear periodo académico: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarPeriodos_Academicos(Periodos_Academicos periodoAcademico) {
        String sql = "UPDATE Periodos_Academicos SET nombre_periodo = ?, fecha_inicio = ?, fecha_fin = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, periodoAcademico.getNombrePeriodo());
            stmt.setDate(2, Date.valueOf(periodoAcademico.getFechaInicio()));
            stmt.setDate(3, Date.valueOf(periodoAcademico.getFechaFin()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar periodo académico: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPeriodos_Academicos(int id) {
        String sql = "DELETE FROM Periodos_Academicos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar periodo académico: " + e.getMessage());
            return false;
        }
    }

    public List<Periodos_Academicos> obtenerPeriodos_Academicos() {
        List<Periodos_Academicos> periodos = new ArrayList<>();
        String sql = "SELECT * FROM Periodos_Academicos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Periodos_Academicos periodo = new Periodos_Academicos();
                periodo.setNombrePeriodo(rs.getString("nombre_periodo"));
                periodo.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                periodo.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                periodos.add(periodo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener periodos académicos: " + e.getMessage());
        }
        return periodos;
    }

    // Método para buscar un periodo académico por ID
    public Periodos_Academicos findById(int id) {
        String sql = "SELECT * FROM Periodos_Academicos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Periodos_Academicos periodo = new Periodos_Academicos();
                periodo.setNombrePeriodo(rs.getString("nombre_periodo"));
                periodo.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                periodo.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                return periodo;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar periodo académico: " + e.getMessage());
        }
        return null;
    }
    //obtener todos los periodos academicos
    public List<Periodos_Academicos> listarTodos() {
        List<Periodos_Academicos> lista = new ArrayList<>();
        String sql = "SELECT * FROM Periodos_Academicos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Periodos_Academicos periodo = new Periodos_Academicos();
                periodo.setNombrePeriodo(rs.getString("nombre_periodo"));
                periodo.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                periodo.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                lista.add(periodo);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar periodos académicos: " + e.getMessage());
        }
        return lista;
    }

}
