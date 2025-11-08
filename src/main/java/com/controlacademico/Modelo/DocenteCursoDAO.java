package com.controlacademico.Modelo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List; 

public class DocenteCursoDAO {

    private Connection conn;

    public DocenteCursoDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean crearDocenteCurso(DocenteCurso docenteCurso) {
        String sql = "INSERT INTO DocenteCurso (docente_id, curso_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, docenteCurso.getDocenteId());
            stmt.setInt(2, docenteCurso.getCursoId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear docente-curso: " + e.getMessage());
            return false;
        }
    }

    //Actualizar
    public boolean actualizarDocenteCurso(DocenteCurso docenteCurso) {
        String sql = "UPDATE DocenteCurso SET docente_id = ?, curso_id = ? WHERE docente_id = ? AND curso_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, docenteCurso.getDocenteId());
            stmt.setInt(2, docenteCurso.getCursoId());
            stmt.setInt(3, docenteCurso.getDocenteId());
            stmt.setInt(4, docenteCurso.getCursoId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar docente-curso: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarDocenteCurso(int docenteId, int cursoId) {
        String sql = "DELETE FROM DocenteCurso WHERE docente_id = ? AND curso_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, docenteId);
            stmt.setInt(2, cursoId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar docente-curso: " + e.getMessage());
            return false;
        }
    }

    public List<DocenteCurso> obtenerCursosPorDocente(int docenteId) {
        List<DocenteCurso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM DocenteCurso WHERE docente_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, docenteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DocenteCurso docenteCurso = new DocenteCurso();
                    docenteCurso.setDocenteId(rs.getInt("docente_id"));
                    docenteCurso.setCursoId(rs.getInt("curso_id"));
                    cursos.add(docenteCurso);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cursos por docente: " + e.getMessage());
        }
        return cursos;
    }

    public List<DocenteCurso> obtenerDocentesPorCurso(int cursoId) {
        List<DocenteCurso> docentes = new ArrayList<>();
        String sql = "SELECT * FROM DocenteCurso WHERE curso_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cursoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DocenteCurso docenteCurso = new DocenteCurso();
                    docenteCurso.setDocenteId(rs.getInt("docente_id"));
                    docenteCurso.setCursoId(rs.getInt("curso_id"));
                    docentes.add(docenteCurso);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener docentes por curso: " + e.getMessage());
        }
        return docentes;
    }

}
