package com.controlacademico.Modelo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Componentes_Evaluacion implements Serializable {
    private Integer componenteEvaluacionId;
    private Integer corteEvaluacionId;
    private String nombre_componente;
    private BigDecimal porcentaje;

    public Componentes_Evaluacion() {}

    public Integer getComponenteEvaluacionId() {
        return componenteEvaluacionId;
    }
    public void setComponenteEvaluacionId(Integer componenteEvaluacionId) {
        this.componenteEvaluacionId = componenteEvaluacionId;
    }
    public Integer getCorteEvaluacionId() {
        return corteEvaluacionId;
    }
    public void setCorteEvaluacionId(Integer corteEvaluacionId) {
        this.corteEvaluacionId = corteEvaluacionId;
    }       
    public String getNombre_componente() {
        return nombre_componente;
    }
    public void setNombre_componente(String nombre_componente) {
        this.nombre_componente = nombre_componente;
    }
    public BigDecimal getPorcentaje() {
        return porcentaje;
    }
    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public String toString() {
        return "Componentes_Evaluacion{" +
                "componenteEvaluacionId=" + componenteEvaluacionId +
                ", corteEvaluacionId=" + corteEvaluacionId +
                ", nombre_componente='" + nombre_componente + '\'' +
                ", porcentaje=" + porcentaje +
                '}';
    }
}