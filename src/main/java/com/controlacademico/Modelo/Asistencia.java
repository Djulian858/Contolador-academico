package com.controlacademico.Modelo;

    import java.io.Serializable;
import java.time.LocalDate;

public class Asistencia implements Serializable {
    private Integer asistenciaId;
    private Integer estudianteId;
    private Integer cursoId;
    private LocalDate fechaClase;
    private String estadoAsistencia;
    private String novedades;

    public Asistencia() {}

    public Integer getAsistenciaId() {
        return asistenciaId;
    }
    public void setAsistenciaId(Integer asistenciaId) {
        this.asistenciaId = asistenciaId;
    }
    public Integer getEstudianteId() {
        return estudianteId;
    }
    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }
    public Integer getCursoId() {
        return cursoId;
    }
    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }
    public LocalDate getFechaClase() {
        return fechaClase;
    }
    public void setFechaClase(LocalDate fechaClase) {
        this.fechaClase = fechaClase;
    }
    public String getEstadoAsistencia() {
        return estadoAsistencia;
    }
    public void setEstadoAsistencia(String estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }
    public String getNovedades() {
        return novedades;
    }
    public void setNovedades(String novedades) {
        this.novedades = novedades;
    }
    @Override
    public String toString() {
        return "Asistencia{" +
                "asistenciaId=" + asistenciaId +
                ", estudianteId=" + estudianteId +
                ", cursoId=" + cursoId +
                ", fechaClase=" + fechaClase +
                ", estadoAsistencia='" + estadoAsistencia + '\'' +
                ", novedades='" + novedades + '\'' +
                '}';
    }
}