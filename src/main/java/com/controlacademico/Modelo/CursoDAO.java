package com.controlacademico.Modelo;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List; 


public class CursoDAO {
    private Connection conn;

    public CursoDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    
    public boolean crearCurso(Curso curso) {
        String sql = "INSERT INTO cursos (nombre_curso, periodo_academico_id, docente_id, descripcion_curso) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNombreCurso());
            stmt.setInt(2, curso.getPeriodoAcademicoId());
            stmt.setInt(3, curso.getDocenteId());
            stmt.setString(4, curso.getDescripcionCurso());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear curso: " + e.getMessage());
            return false;
        }
    }
    // READ by ID
    public Curso buscarPorId(int id) {
        String sql = "SELECT * FROM cursos WHERE curso_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar curso: " + e.getMessage());
        }
        return null;
    }

    
    public List<Curso> listarTodos() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar cursos: " + e.getMessage());
        }
        return lista;
    }

    
    private Curso mapRow(ResultSet rs) throws SQLException {
        Curso curso = new Curso();
        curso.setCursoId(rs.getInt("curso_id"));
        curso.setNombreCurso(rs.getString("nombre_curso"));
        curso.setPeriodoAcademicoId(rs.getInt("periodo_academico_id"));
        curso.setDocenteId(rs.getInt("docente_id"));
        curso.setDescripcionCurso(rs.getString("descripcion_curso"));
        return curso;
    }

    
    public boolean actualizarCurso(Curso curso) {
        String sql = "UPDATE cursos SET nombre_curso = ?, periodo_academico_id = ?, docente_id = ?, descripcion_curso = ? WHERE curso_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNombreCurso());
            stmt.setInt(2, curso.getPeriodoAcademicoId());
            stmt.setInt(3, curso.getDocenteId());
            stmt.setString(4, curso.getDescripcionCurso());
            stmt.setInt(5, curso.getCursoId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar curso: " + e.getMessage());
            return false;
        }
    }

    
    public boolean eliminarCurso(int id) {      
        String sql = "DELETE FROM cursos WHERE curso_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar curso: " + e.getMessage());
            return false;
        }
    }

    //obtener cursos por id de docente
    public List<Curso> obtenerCursosPorDocenteId(int docenteId) {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos WHERE docente_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, docenteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar cursos por docente: " + e.getMessage());
        }
        return lista;
    }

    //obtener cursos por id de periodo academico
    public List<Curso> obtenerCursosPorPeriodoAcademicoId(int periodoAcademicoId)   {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos WHERE periodo_academico_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, periodoAcademicoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar cursos por periodo académico: " + e.getMessage());
        }
        return lista;
    }

    




}
