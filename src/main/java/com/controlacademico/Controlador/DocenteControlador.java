package com.controlacademico.Controlador;
import com.controlacademico.Modelo.*;
import java.util.List;


public class DocenteControlador {
    private DocenteDAO docenteDAO;

    public DocenteControlador(DocenteDAO docenteDAO) {
        this.docenteDAO = docenteDAO;
    }

    public boolean registrarDocente(Docente docente) {
        return docenteDAO.create(docente);
    }

    public boolean actualizarDocente(Docente docente) {
        return docenteDAO.update(docente);
    }

    public Docente obtenerDocentePorId(int id) {
        return docenteDAO.findById(id);
    }

    public boolean eliminarDocente(int id) {
        return docenteDAO.delete(id);
    }

    public List<Docente> listarDocentes() {
        return docenteDAO.findAll();
    }
}
