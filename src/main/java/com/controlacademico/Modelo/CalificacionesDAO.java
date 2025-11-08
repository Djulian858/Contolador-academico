package com.controlacademico.Modelo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalificacionesDAO {
    private Connection conexion;

    public CalificacionesDAO() {
        this.conexion = Conexion.getInstance().getConnection();
    }

    //Crear
    public boolean create(Calificaciones calificacion) {
        String sql = "INSERT INTO calificaciones (estudiante_id, componente_evaluacion_id, nota, comentarios_calificacion) "
                   + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, calificacion.getEstudianteId());
            stmt.setInt(2, calificacion.getComponenteEvaluacionId());
            stmt.setBigDecimal(3, calificacion.getNota());
            stmt.setString(4, calificacion.getComentariosCalificacion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear calificación: " + e.getMessage());
            return false;
        }
    }

    //Actualizar
    public boolean update(Calificaciones calificacion) {
        String sql = "UPDATE calificaciones SET estudiante_id = ?, componente_evaluacion_id = ?, nota = ?, comentarios_calificacion = ? WHERE calificacion_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, calificacion.getEstudianteId());
            stmt.setInt(2, calificacion.getComponenteEvaluacionId());
            stmt.setBigDecimal(3, calificacion.getNota());
            stmt.setString(4, calificacion.getComentariosCalificacion());
            stmt.setInt(5, calificacion.getCalificacionId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar calificación: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean delete(int id) {
        String sql = "DELETE FROM calificaciones WHERE calificacion_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar calificación: " + e.getMessage());
            return false;
        }
    }

            

    // Leer por ID
    public Calificaciones findById(int id) {
        String sql = "SELECT * FROM calificaciones WHERE calificacion_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar calificación: " + e.getMessage());
        }
        return null;
    }

    // Leer todos
    public List<Calificaciones> findAll() {
        List<Calificaciones> lista = new ArrayList<>();
        String sql = "SELECT * FROM calificaciones";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar calificaciones: " + e.getMessage());
        }
        return lista;
    }

    // Mapeo de ResultSet a Calificaciones
    private Calificaciones mapRow(ResultSet rs) throws SQLException {
        Calificaciones calificacion = new Calificaciones();
        calificacion.setCalificacionId(rs.getInt("calificacion_id"));
        calificacion.setEstudianteId(rs.getInt("estudiante_id"));
        calificacion.setComponenteEvaluacionId(rs.getInt("componente_evaluacion_id"));
        calificacion.setNota(rs.getBigDecimal("nota"));
        calificacion.setComentariosCalificacion(rs.getString("comentarios_calificacion"));
        return calificacion;
    }
}
