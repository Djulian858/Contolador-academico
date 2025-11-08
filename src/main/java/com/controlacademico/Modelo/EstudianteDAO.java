package com.controlacademico.Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EstudianteDAO {
    private Connection conexion;

    

    public EstudianteDAO(Connection conexion) {
        this.conexion = conexion;
    }


    // Crear
    public boolean create(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (identificacion, nombre, correo_institucional, correo_personal, telefono, es_vocero, comentarios, tipo_documento, genero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estudiante.getIdentificacion());
            stmt.setString(2, estudiante.getNombre());
            stmt.setString(3, estudiante.getCorreo_institucional());
            stmt.setString(4, estudiante.getCorreo_personal());
            stmt.setString(5, estudiante.getTelefono());
            stmt.setBoolean(6, estudiante.isEs_vocero());
            stmt.setString(7, estudiante.getComentarios());
            stmt.setString(8, estudiante.getTipo_documento());
            stmt.setString(9, estudiante.getGenero());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear estudiante: " + e.getMessage());
            return false;
        }
    }

    // Actualizar
    public boolean update(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET identificacion = ?, nombre = ?, correo_institucional = ?, correo_personal = ?, telefono = ?, es_vocero = ?, comentarios = ?, tipo_documento = ?, genero = ? WHERE estudiante_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estudiante.getIdentificacion());
            stmt.setString(2, estudiante.getNombre());
            stmt.setString(3, estudiante.getCorreo_institucional());
            stmt.setString(4, estudiante.getCorreo_personal());
            stmt.setString(5, estudiante.getTelefono());
            stmt.setBoolean(6, estudiante.isEs_vocero());
            stmt.setString(7, estudiante.getComentarios());
            stmt.setString(8, estudiante.getTipo_documento());
            stmt.setString(9, estudiante.getGenero());
            stmt.setInt(10, estudiante.getEstudiante_id());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean delete(int id) {
        String sql = "DELETE FROM estudiantes WHERE estudiante_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }

    }

    // Leer por ID
    public Estudiante findById(int id) {    
        String sql = "SELECT * FROM estudiantes WHERE estudiante_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        return null;
    }

    

    

// EstudianteDAO.java
// ...
// Leer todos
public List<Estudiante> findAll() {
    List<Estudiante> estudiantes = new ArrayList<>();
    String sql = "SELECT * FROM estudiantes"; // <-- Consulta SQL
    try (PreparedStatement stmt = conexion.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            estudiantes.add(mapRow(rs));
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar estudiantes: " + e.getMessage());
    }
    return estudiantes;
}
// ...





    // Mapeo de ResultSet a objeto Estudiante
    private Estudiante mapRow(ResultSet rs) throws SQLException {   
        Estudiante estudiante = new Estudiante();
        estudiante.setEstudiante_id(rs.getInt("estudiante_id"));
        estudiante.setIdentificacion(rs.getString("identificacion"));
        estudiante.setNombre(rs.getString("nombre"));
        estudiante.setCorreo_institucional(rs.getString("correo_institucional"));
        estudiante.setCorreo_personal(rs.getString("correo_personal"));
        estudiante.setTelefono(rs.getString("telefono"));
        estudiante.setEs_vocero(rs.getBoolean("es_vocero"));
        estudiante.setComentarios(rs.getString("comentarios"));
        estudiante.setTipo_documento(rs.getString("tipo_documento"));
        estudiante.setGenero(rs.getString("genero"));
        return estudiante;
    }

}