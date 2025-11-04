package com.controlacademico.Modelo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Calificaciones implements Serializable {
    private Integer calificacionId;
    private Integer estudianteId;
    private Integer componenteEvaluacionId;
    private BigDecimal nota;
    private String comentariosCalificacion;

    public Calificaciones() {}

    public Integer getCalificacionId() {
        return calificacionId;
    }

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public Integer getComponenteEvaluacionId() {
        return componenteEvaluacionId;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public String getComentariosCalificacion() {
        return comentariosCalificacion;
    }

    public void setCalificacionId(Integer calificacionId) {
        this.calificacionId = calificacionId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public void setComponenteEvaluacionId(Integer componenteEvaluacionId) {
        this.componenteEvaluacionId = componenteEvaluacionId;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public void setComentariosCalificacion(String comentariosCalificacion) {
        this.comentariosCalificacion = comentariosCalificacion;
    }

@Override
    public String toString() {
        return "Calificaciones{" +
                "calificacionId=" + calificacionId +
                ", estudianteId=" + estudianteId +
                ", componenteEvaluacionId=" + componenteEvaluacionId +
                ", nota=" + nota +
                ", comentariosCalificacion='" + comentariosCalificacion + '\'' +
                '}';
    }

}