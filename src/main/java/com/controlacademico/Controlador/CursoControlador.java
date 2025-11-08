package com.controlacademico.Controlador;
import com.controlacademico.Modelo.*;
public class CursoControlador {
    private CursoDAO cursoDAO;
    public CursoControlador(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }
    public boolean registrarCurso(Curso curso) {
        return cursoDAO.crearCurso(curso);
    }
    public boolean actualizarCurso(Curso curso) {
        return cursoDAO.actualizarCurso(curso);
    }
    public Curso obtenerCursosPorId(int id) {
        return cursoDAO.buscarPorId(id);
    }
    public boolean eliminarCurso(int id) {
        return cursoDAO.eliminarCurso(id);
    }

    

}