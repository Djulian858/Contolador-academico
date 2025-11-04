package com.controlacademico.Modelo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Cortes_Evaluacion implements Serializable {
    private Integer corteEvaluacionId;
    private String nombreCorte;
    private BigDecimal porcentaje;
    private Integer cursoId;
    private Integer periodoAcademicoId;
    private String comentariosCorte;

    public Cortes_Evaluacion() {}

    public Integer getCorteEvaluacionId() {
        return corteEvaluacionId;
    }   
    public void setCorteEvaluacionId(Integer corteEvaluacionId) {
        this.corteEvaluacionId = corteEvaluacionId;
    }
    public String getNombreCorte() {
        return nombreCorte;
    }
    public void setNombreCorte(String nombreCorte) {
        this.nombreCorte = nombreCorte;
    }
    public BigDecimal getPorcentaje() {
        return porcentaje;
    }
    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
    public Integer getCursoId() {
        return cursoId;
    }   
    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }
    public Integer getPeriodoAcademicoId() {
        return periodoAcademicoId;
    }
    public void setPeriodoAcademicoId(Integer periodoAcademicoId) {
        this.periodoAcademicoId = periodoAcademicoId;
    }
    public String getComentariosCorte() {
        return comentariosCorte;
    }
    public void setComentariosCorte(String comentariosCorte) {
        this.comentariosCorte = comentariosCorte;
    }

    @Override
    public String toString() {
        return "Cortes_Evaluacion{" +
                "corteEvaluacionId=" + corteEvaluacionId +
                ", nombreCorte='" + nombreCorte + '\'' +
                ", porcentaje=" + porcentaje +
                ", cursoId=" + cursoId +
                ", periodoAcademicoId=" + periodoAcademicoId +
                ", comentariosCorte='" + comentariosCorte + '\'' +
                '}';
    }
}