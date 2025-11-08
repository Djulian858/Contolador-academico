package com.controlacademico.Controlador;

import java.util.List;

import com.controlacademico.Modelo.*;

public class EstudianteControlador {

    private EstudianteDAO estudianteDAO;

    public EstudianteControlador(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    public boolean registrarEstudiante(Estudiante estudiante) {
        return estudianteDAO.create(estudiante);
    }

    public boolean actualizarEstudiante(Estudiante estudiante) {
        return estudianteDAO.update(estudiante);
    }

    public Estudiante obtenerEstudiantePorId(int id) {
        return estudianteDAO.findById(id);
    }

    public boolean eliminarEstudiante(int id) {
        return estudianteDAO.delete(id);
    }

public List<Estudiante> findAll() {
    // LLama al método findAll() del DAO para obtener la lista de estudiantes
    return estudianteDAO.findAll();
}
}
