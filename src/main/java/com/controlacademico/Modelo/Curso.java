package com.controlacademico.Modelo;

public class Curso {

   private Integer cursoId;
   private String nombreCurso;
   private Integer periodoAcademicoId;
   private Integer docenteId;
   private String descripcionCurso;
   
    public Curso() {}

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }   
    public String getNombreCurso() {
        return nombreCurso;
    }
    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }
    public Integer getPeriodoAcademicoId() {
        return periodoAcademicoId;
    }
    public void setPeriodoAcademicoId(Integer periodoAcademicoId) {
        this.periodoAcademicoId = periodoAcademicoId;
    }
    public Integer getDocenteId() {
        return docenteId;
    }
    public void setDocenteId(Integer docenteId) {
        this.docenteId = docenteId;
    }
    public String getDescripcionCurso() {
        return descripcionCurso;
    }
    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = descripcionCurso;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "cursoId=" + cursoId +
                ", nombreCurso='" + nombreCurso + '\'' +
                ", periodoAcademicoId=" + periodoAcademicoId +
                ", docenteId=" + docenteId +
                ", descripcionCurso='" + descripcionCurso + '\'' +
                '}';
    }
}