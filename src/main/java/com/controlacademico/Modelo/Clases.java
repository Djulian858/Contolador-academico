package com.controlacademico.Modelo;

import java.io.Serializable;
import java.sql.Date;

public class Clases implements Serializable {
    private Integer claseId;
    private Integer cursoId;
    private Integer numeroClase;
    private Date fechaClase;
    private String temaClase;
    private String descripcionClase;
    private String comentariosClase;

    public Clases() {}

    public Integer getClaseId() {
        return claseId;
    }
    public void setClaseId(Integer claseId) {
        this.claseId = claseId;
    }
    public Integer getCursoId() {
        return cursoId;
    }
    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }
    public Integer getNumeroClase() {
        return numeroClase;
    }
    public void setNumeroClase(Integer numeroClase) {
        this.numeroClase = numeroClase;
    }
    public Date getFechaClase() {
        return fechaClase;
    }
    public void setFechaClase(Date fechaClase) {
        this.fechaClase = fechaClase;
    }
    public String getTemaClase() {
        return temaClase;
    }
    public void setTemaClase(String temaClase) {
        this.temaClase = temaClase;
    }

    public String getDescripcionClase() {
        return descripcionClase;
    }
    public void setDescripcionClase(String descripcionClase) {
        this.descripcionClase = descripcionClase;
    }
    public String getComentariosClase() {
        return comentariosClase;
    }
    public void setComentariosClase(String comentariosClase) {
        this.comentariosClase = comentariosClase;
    }
    @Override
    public String toString() {
        return "Clases{" +
                "claseId=" + claseId +
                ", cursoId=" + cursoId +
                ", numeroClase=" + numeroClase +
                ", fechaClase=" + fechaClase +
                ", temaClase='" + temaClase + '\'' +
                ", descripcionClase='" + descripcionClase + '\'' +
                ", comentariosClase='" + comentariosClase + '\'' +
                '}';
    }
}