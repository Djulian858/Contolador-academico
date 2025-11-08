package com.controlacademico.Controlador;

import com.controlacademico.Modelo.*;

public class CalificacionesControlador {

    private CalificacionesDAO calificacionesDAO;

    public CalificacionesControlador(CalificacionesDAO calificacionesDAO) {
        this.calificacionesDAO = calificacionesDAO;
    }
    public boolean registrarCalificacion(Calificaciones calificaciones) {
        return calificacionesDAO.create(calificaciones);
    }
    public boolean actualizarCalificacion(Calificaciones calificaciones) {
        return calificacionesDAO.update(calificaciones);
    }
    public Calificaciones obtenerCalificacionPorId(int id) {
        return calificacionesDAO.findById(id);
    }
    public boolean eliminarCalificacion(int id) {
        return calificacionesDAO.delete(id);
    }

    
}
