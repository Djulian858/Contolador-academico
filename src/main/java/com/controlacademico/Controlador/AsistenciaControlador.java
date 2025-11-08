package com.controlacademico.Controlador;

import com.controlacademico.Modelo.*;

public class AsistenciaControlador {

    private AsistenciaDAO asistenciaDAO= new AsistenciaDAO();

    public AsistenciaControlador(AsistenciaDAO asistenciaDAO) {
        this.asistenciaDAO = asistenciaDAO;
    }

    public boolean registrarAsistencia(Asistencia asistencia) {
        return asistenciaDAO.create(asistencia);
}

    public boolean actualizarAsistencia(Asistencia asistencia) {
        return asistenciaDAO.update(asistencia);
    }
    public Asistencia obtenerAsistenciaPorId(int id) {
        return asistenciaDAO.findById(id);
    }

    public boolean eliminarAsistencia(int id) {
        return asistenciaDAO.delete(id);
    }



}

