package com.controlacademico.Modelo;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {

    private Connection conn;

    public AsistenciaDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // CREATE
    public boolean create(Asistencia asistencia) {
        String sql = "INSERT INTO asistencias (estudiante_id, curso_id, fecha_clase, estado_asistencia, novedades) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, asistencia.getEstudianteId());
            stmt.setInt(2, asistencia.getCursoId());
            stmt.setDate(3, asistencia.getFechaClase());
            stmt.setString(4, asistencia.getEstadoAsistencia());
            stmt.setString(5, asistencia.getNovedades());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(" Error al crear asistencia: " + e.getMessage());
            return false;
        }
    }

    // UPDATE
    public boolean update(Asistencia asistencia) {
        String sql = "UPDATE asistencias SET estudiante_id = ?, curso_id = ?, fecha_clase = ?, estado_asistencia = ?, novedades = ? WHERE asistencia_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, asistencia.getEstudianteId());
            stmt.setInt(2, asistencia.getCursoId());
            stmt.setDate(3, asistencia.getFechaClase());
            stmt.setString(4, asistencia.getEstadoAsistencia());
            stmt.setString(5, asistencia.getNovedades());
            stmt.setInt(6, asistencia.getAsistenciaId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(" Error al actualizar asistencia: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM asistencias WHERE asistencia_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(" Error al eliminar asistencia: " + e.getMessage());
            return false;
        }
    }

    // READ by ID
    public Asistencia findById(int id) {
        String sql = "SELECT * FROM asistencias WHERE asistencia_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println(" Error al buscar asistencia: " + e.getMessage());
        }
        return null;
    }

    // READ all
    public List<Asistencia> findAll() { 
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM asistencias";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println(" Error al listar asistencias: " + e.getMessage());
        }
        return lista;
    }

    private Asistencia mapRow(ResultSet rs) throws SQLException {
        Asistencia asistencia = new Asistencia();
        asistencia.setAsistenciaId(rs.getInt("asistencia_id"));
        asistencia.setEstudianteId(rs.getInt("estudiante_id"));
        asistencia.setCursoId(rs.getInt("curso_id"));
        asistencia.setFechaClase(rs.getDate("fecha_clase"));
        asistencia.setEstadoAsistencia(rs.getString("estado_asistencia"));
        asistencia.setNovedades(rs.getString("novedades"));
        return asistencia;
    }
}
