package com.controlacademico.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {

    private Connection conn;

    public DocenteDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // CREATE
    public boolean create(Docente docente) {
        String sql = "INSERT INTO docentes (nombre_docente, identificacion, tipo_identificacion, genero, correo, titulo_estudios, idiomas, certificaciones) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, docente.getNombreDocente());
            stmt.setString(2, docente.getIdentificacion());
            stmt.setString(3, docente.getTipoIdentificacion());
            stmt.setString(4, docente.getGenero());
            stmt.setString(5, docente.getCorreo());
            stmt.setString(6, docente.getTituloEstudios());
            stmt.setString(7, docente.getIdiomas());
            stmt.setString(8, docente.getCertificaciones());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear docente: " + e.getMessage());
            return false;
        }
    }

    // Leer por ID
    public Docente findById(int id) {
        String sql = "SELECT * FROM docentes WHERE docente_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar docente: " + e.getMessage());
        }
        return null;
    }

    // Leer todos
    public List<Docente> findAll() {
        List<Docente> lista = new ArrayList<>();
        String sql = "SELECT * FROM docentes";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar docentes: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE
    public boolean update(Docente docente) {
        String sql = "UPDATE docentes SET nombre_docente=?, identificacion=?, tipo_identificacion=?, genero=?, correo=?, titulo_estudios=?, idiomas=?, certificaciones=? WHERE docente_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, docente.getNombreDocente());
            stmt.setString(2, docente.getIdentificacion());
            stmt.setString(3, docente.getTipoIdentificacion());
            stmt.setString(4, docente.getGenero());
            stmt.setString(5, docente.getCorreo());
            stmt.setString(6, docente.getTituloEstudios());
            stmt.setString(7, docente.getIdiomas());
            stmt.setString(8, docente.getCertificaciones());
            stmt.setInt(9, docente.getDocenteId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar docente: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM docentes WHERE docente_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar docente: " + e.getMessage());
            return false;
        }
    }

    // MAPEO
    private Docente mapRow(ResultSet rs) throws SQLException {
        Docente doc = new Docente();
        doc.setDocenteId(rs.getInt("docente_id"));
        doc.setNombreDocente(rs.getString("nombre_docente"));
        doc.setIdentificacion(rs.getString("identificacion"));
        doc.setTipoIdentificacion(rs.getString("tipo_identificacion"));
        doc.setGenero(rs.getString("genero"));
        doc.setCorreo(rs.getString("correo"));
        doc.setTituloEstudios(rs.getString("titulo_estudios"));
        doc.setIdiomas(rs.getString("idiomas"));
        doc.setCertificaciones(rs.getString("certificaciones"));
        return doc;
    }
}
