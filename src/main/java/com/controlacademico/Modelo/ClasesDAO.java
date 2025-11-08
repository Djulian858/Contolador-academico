package com.controlacademico.Modelo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClasesDAO { 
    private Connection conn;

    public boolean create(Clases clases) {
        String sql = "INSERT INTO clases (curso_id, numero_clase,fecha_clase, tema_clase, descripcion_clase, comentarios_clase) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clases.getCursoId());
            stmt.setInt(2, clases.getNumeroClase());
            stmt.setDate(3, clases.getFechaClase());
            stmt.setString(4, clases.getTemaClase());
            stmt.setString(5, clases.getDescripcionClase());
            stmt.setString(6, clases.getComentariosClase());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(" Error al crear clase: " + e.getMessage());
            return false;
        }
        }



    // UPDATE
    public boolean update(Clases clases) {
        String sql = "UPDATE clases SET curso_id = ?, numero_clase = ?, fecha_clase = ?, tema_clase = ?, descripcion_clase = ?, comentarios_clase = ? WHERE clase_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clases.getCursoId());
            stmt.setInt(2, clases.getNumeroClase());
            stmt.setDate(3, clases.getFechaClase());
            stmt.setString(4, clases.getTemaClase());
            stmt.setString(5, clases.getDescripcionClase());
            stmt.setString(6, clases.getComentariosClase());
            stmt.setInt(7, clases.getClaseId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(" Error al actualizar clase: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM clases WHERE clase_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(" Error al eliminar clase: " + e.getMessage());
            return false;
        }
    }

    // READ by ID
    public Clases findById(int id) {    
        String sql = "SELECT * FROM clases WHERE clase_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println(" Error al buscar clase: " + e.getMessage());
        }
        return null;
    }

    // READ all
    public List<Clases> findAll() {
        List<Clases> lista = new ArrayList<>();
        String sql = "SELECT * FROM clases";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println(" Error al listar clases: " + e.getMessage());
        }
        return lista;
    }

    // Mapeo de ResultSet a objeto Clases

    private Clases mapRow(ResultSet rs) throws SQLException {
        Clases clases = new Clases();
        clases.setClaseId(rs.getInt("clase_id"));
        clases.setCursoId(rs.getInt("curso_id"));
        clases.setFechaClase(rs.getDate("fecha_clase"));
        clases.setTemaClase(rs.getString("tema_clase"));
        clases.setDescripcionClase(rs.getString("descripcion_clase"));
        clases.setComentariosClase(rs.getString("comentarios_clase"));
        return clases;
    }

    

}



