package com.controlacademico.Controlador;
import com.controlacademico.Modelo.*;
import java.util.List;

public class DocenteCursoControlador {
    private DocenteCursoDAO docenteCursoDAO;

    public DocenteCursoControlador(DocenteCursoDAO docenteCursoDAO) {
        this.docenteCursoDAO = docenteCursoDAO;
    }

    public boolean asignarCursoADocente(DocenteCurso docenteCurso) {
        return docenteCursoDAO.crearDocenteCurso(docenteCurso);

    }

    public boolean eliminarAsignacion(int docenteId, int cursoId) {
        return docenteCursoDAO.eliminarDocenteCurso(docenteId, cursoId);
    }

    public List<DocenteCurso> obtenerCursosDeDocente(int docenteId) {
        return docenteCursoDAO.obtenerCursosPorDocente(docenteId);
    }

    public List<DocenteCurso> obtenerDocentesDeCurso(int cursoId) {
        return docenteCursoDAO.obtenerDocentesPorCurso(cursoId);
    }
}
