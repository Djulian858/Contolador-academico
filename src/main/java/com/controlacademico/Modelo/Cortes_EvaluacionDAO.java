package com.controlacademico.Modelo;


import java.sql.*;
import java.util.ArrayList;     
import java.util.List;


public class Cortes_EvaluacionDAO {

    private Connection conn;

    public Cortes_EvaluacionDAO() {
        this.conn = Conexion.getInstance().getConnection();
    }

    // CREATE
    public boolean create(Cortes_Evaluacion corte) {
      String sql = "INSERT INTO cortes_evaluacion (curso_id,periodo_academico_id, nombre_corte, porcentaje, comentarios_corte) "
                 + "VALUES (?, ?, ?, ?, ?)";
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
          stmt.setInt(1, corte.getCursoId());
          stmt.setInt(2, corte.getPeriodoAcademicoId());
          stmt.setString(3, corte.getNombreCorte());
          stmt.setBigDecimal(4, corte.getPorcentaje());
          stmt.setString(5, corte.getComentariosCorte());
          return stmt.executeUpdate() > 0;
      } catch (SQLException e) {
          System.err.println("Error al crear corte de evaluación: " + e.getMessage());
          return false;
      }
    }

    //UPDATE
    public boolean update(Cortes_Evaluacion corte) {
        String sql = "UPDATE cortes_evaluacion SET curso_id = ?, periodo_academico_id = ?, nombre_corte = ?, porcentaje = ?, comentarios_corte = ? WHERE corte_evaluacion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, corte.getCursoId());
            stmt.setInt(2, corte.getPeriodoAcademicoId());
            stmt.setString(3, corte.getNombreCorte());
            stmt.setBigDecimal(4, corte.getPorcentaje());
            stmt.setString(5, corte.getComentariosCorte());
            stmt.setInt(6, corte.getCorteEvaluacionId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar corte de evaluación: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM cortes_evaluacion WHERE corte_evaluacion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar corte de evaluación: " + e.getMessage());
            return false;
        }
    }

    // READ by ID
    public Cortes_Evaluacion findById(int id) {
        String sql = "SELECT * FROM cortes_evaluacion WHERE corte_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar corte de evaluación: " + e.getMessage());
        }
        return null;
    }

    // READ all
    public List<Cortes_Evaluacion> findAll() {
        List<Cortes_Evaluacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM cortes_evaluacion";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar cortes de evaluación: " + e.getMessage());
        }
        return lista;
    }

    
    private Cortes_Evaluacion mapRow(ResultSet rs) throws SQLException {    
        Cortes_Evaluacion corte = new Cortes_Evaluacion();
        corte.setCorteEvaluacionId(rs.getInt("corte_evaluacion_id"));
        corte.setCursoId(rs.getInt("curso_id"));
        corte.setPeriodoAcademicoId(rs.getInt("periodo_academico_id"));
        corte.setNombreCorte(rs.getString("nombre_corte"));
        corte.setPorcentaje(rs.getBigDecimal("porcentaje"));
        corte.setComentariosCorte(rs.getString("comentarios_corte"));
        return corte;
    }

}

