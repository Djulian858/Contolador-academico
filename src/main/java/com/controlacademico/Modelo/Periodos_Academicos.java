package com.controlacademico.Modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Periodos_Academicos implements Serializable {
    private Integer periodoAcademicoId;
    private String nombrePeriodo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Periodos_Academicos() {}

    public Integer getPeriodoAcademicoId() {
        return periodoAcademicoId;
    }

    public void setPeriodoAcademicoId(Integer periodoAcademicoId) {
        this.periodoAcademicoId = periodoAcademicoId;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    @Override
    public String toString() {          
        return "Periodos_Academicos{" +
                "periodoAcademicoId=" + periodoAcademicoId +
                ", nombrePeriodo='" + nombrePeriodo + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }

}