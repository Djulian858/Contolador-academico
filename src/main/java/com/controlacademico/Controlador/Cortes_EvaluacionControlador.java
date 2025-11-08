package com.controlacademico.Controlador;
import com.controlacademico.Modelo.*;

public class Cortes_EvaluacionControlador {
    private Cortes_EvaluacionDAO cortes_EvaluacionDAO= new Cortes_EvaluacionDAO();

    public Cortes_EvaluacionControlador(Cortes_EvaluacionDAO cortes_EvaluacionDAO) {
        this.cortes_EvaluacionDAO = cortes_EvaluacionDAO;
    }

    public boolean registrarCorteEvaluacion(Cortes_Evaluacion corte) {
        return cortes_EvaluacionDAO.create(corte);
    }

    public boolean actualizarCorteEvaluacion(Cortes_Evaluacion corte) {
        return cortes_EvaluacionDAO.update(corte);
    }

    public Cortes_Evaluacion obtenerCorteEvaluacionPorId(int id) {
        return cortes_EvaluacionDAO.findById(id);
    }

    public boolean eliminarCorteEvaluacion(int id) {
        return cortes_EvaluacionDAO.delete(id);
    }
    
}
