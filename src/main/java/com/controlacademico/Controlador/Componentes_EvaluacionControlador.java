package com.controlacademico.Controlador;
import com.controlacademico.Modelo.*;

public class Componentes_EvaluacionControlador {

    private Componentes_EvaluacionDAO componentes_EvaluacionDAO;

    public Componentes_EvaluacionControlador(Componentes_EvaluacionDAO componentes_EvaluacionDAO) {
        this.componentes_EvaluacionDAO = componentes_EvaluacionDAO;
    }

    public boolean registrarComponenteEvaluacion(Componentes_Evaluacion componente) {
        return componentes_EvaluacionDAO.crearComponente(componente);
    }

    public boolean actualizarComponenteEvaluacion(Componentes_Evaluacion componente) {
        return componentes_EvaluacionDAO.actualizarComponente(componente);
    }

    public Componentes_Evaluacion obtenerComponenteEvaluacionPorId(int id) {
        return componentes_EvaluacionDAO.obtenerComponentePorId(id);
    }

    public boolean eliminarComponenteEvaluacion(int id) {
        return componentes_EvaluacionDAO.eliminarComponente(id);
    }

}