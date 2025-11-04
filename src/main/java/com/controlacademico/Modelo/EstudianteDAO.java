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

    public void create(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiantes (identificacion, nombre, correo_institucional, correo_personal, telefono, es_vocero, comentarios, tipo_documento, genero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estudiante.getIdentificacion());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCorreo_institucional());
            ps.setString(4, estudiante.getCorreo_personal());
            ps.setString(5, estudiante.getTelefono());
            ps.setBoolean(6, estudiante.isEs_vocero());
            ps.setString(7, estudiante.getComentarios());
            ps.setString(8, estudiante.getTipo_documento());
            ps.setString(9, estudiante.getGenero());

            ps.executeUpdate();
        }
    }

    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
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

                estudiantes.add(estudiante);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estudiantes;
    }

    public void agregarEstudiante(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (identificacion, nombre, correo_institucional, correo_personal, telefono, es_vocero, comentarios, tipo_documento, genero) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, estudiante.getIdentificacion());
            pstmt.setString(2, estudiante.getNombre());
            pstmt.setString(3, estudiante.getCorreo_institucional());
            pstmt.setString(4, estudiante.getCorreo_personal());
            pstmt.setString(5, estudiante.getTelefono());
            pstmt.setBoolean(6, estudiante.isEs_vocero());
            pstmt.setString(7, estudiante.getComentarios());
            pstmt.setString(8, estudiante.getTipo_documento());
            pstmt.setString(9, estudiante.getGenero());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Métodos adicionales para actualizar y eliminar estudiantes pueden ser añadidos aquí

    public void actualizarEstudiante(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET identificacion = ?, nombre = ?, correo_institucional = ?, correo_personal = ?, telefono = ?, es_vocero = ?, comentarios = ?, tipo_documento = ?, genero = ? WHERE estudiante_id = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, estudiante.getIdentificacion());
            pstmt.setString(2, estudiante.getNombre());
            pstmt.setString(3, estudiante.getCorreo_institucional());
            pstmt.setString(4, estudiante.getCorreo_personal());
            pstmt.setString(5, estudiante.getTelefono());
            pstmt.setBoolean(6, estudiante.isEs_vocero());
            pstmt.setString(7, estudiante.getComentarios());
            pstmt.setString(8, estudiante.getTipo_documento());
            pstmt.setString(9, estudiante.getGenero());
            pstmt.setInt(10, estudiante.getEstudiante_id());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarEstudiante(int estudianteId) {
        String sql = "DELETE FROM estudiantes WHERE estudiante_id = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, estudianteId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

           

}

    