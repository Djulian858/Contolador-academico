package com.controlacademico.Modelo;

public class Docente {
    private int docenteId;
    private String nombreDocente;
    private String identificacion;
    private String tipoIdentificacion;
    private String genero;
    private String correo;
    private String tituloEstudios;
    private String idiomas;
    private String certificaciones;

    public Docente() {
    }

    // getters/setters
    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTituloEstudios() {
        return tituloEstudios;
    }

    public void setTituloEstudios(String tituloEstudios) {
        this.tituloEstudios = tituloEstudios;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getCertificaciones() {
        return certificaciones;
    }

    public void setCertificaciones(String certificaciones) {
        this.certificaciones = certificaciones;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "docenteId=" + docenteId +
                ", nombreDocente='" + nombreDocente + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", tipoIdentificacion='" + tipoIdentificacion + '\'' +
                ", genero='" + genero + '\'' +
                ", correo='" + correo + '\'' +
                ", tituloEstudios='" + tituloEstudios + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", certificaciones='" + certificaciones + '\'' +
                '}';
    }
}
