package com.controlacademico.Controlador;
import com.controlacademico.Modelo.*;
public class ClasesControlador {

    private ClasesDAO clasesDAO;
    public ClasesControlador(ClasesDAO clasesDAO) {
        this.clasesDAO = clasesDAO;
    }
    public boolean registrarClase(Clases clases) {
        return clasesDAO.create(clases);
    }
    public boolean actualizarClase(Clases clases) {
        return clasesDAO.update(clases);
    }
    public Clases obtenerClasePorId(int id) {
        return clasesDAO.findById(id);
    }
    public boolean eliminarClase(int id) {
        return clasesDAO.delete(id);
    }
}
