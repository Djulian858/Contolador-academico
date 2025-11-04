package com.controlacademico.Modelo;

import java.io.Serializable;

public class Estudiante implements Serializable {
    private int estudiante_id;
    private String identificacion;
    private String nombre;
    private String correo_institucional;
    private String correo_personal;
    private String telefono;
    private boolean es_vocero;
    private String comentarios;
    private String tipo_documento;
    private String genero;

    public Estudiante (){}

    public Estudiante(int estudiante_id, String identificacion, String nombre, String correo_institucional,
                      String correo_personal, String telefono, boolean es_vocero, String comentarios,
                      String tipo_documento, String genero) {
        this.estudiante_id = estudiante_id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo_institucional = correo_institucional;
        this.correo_personal = correo_personal;
        this.telefono = telefono;
        this.es_vocero = es_vocero;
        this.comentarios = comentarios;
        this.tipo_documento = tipo_documento;
        this.genero = genero;
    }

    public int getEstudiante_id() {
        return estudiante_id;
    }
    public void setEstudiante_id(int estudiante_id) {
        this.estudiante_id = estudiante_id;
    }
    public String getIdentificacion() {
        return identificacion;
    }
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }   
    public String getCorreo_institucional() {
        return correo_institucional;
    }
    public void setCorreo_institucional(String correo_institucional) {
        this.correo_institucional = correo_institucional;
    }
    public String getCorreo_personal() {
        return correo_personal;
    }

    public void setCorreo_personal(String correo_personal) {
        this.correo_personal = correo_personal;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public boolean isEs_vocero() {
        return es_vocero;
    }
    public void setEs_vocero(boolean es_vocero) {
        this.es_vocero = es_vocero;
    }
    public String getComentarios() {
        return comentarios;
    }
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    public String getTipo_documento() {
        return tipo_documento;
    }
    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "estudiante_id=" + estudiante_id +
                ", identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo_institucional='" + correo_institucional + '\'' +
                ", correo_personal='" + correo_personal + '\'' +
                ", telefono='" + telefono + '\'' +
                ", es_vocero=" + es_vocero +
                ", comentarios='" + comentarios + '\'' +
                ", tipo_documento='" + tipo_documento + '\'' +
                ", genero='" + genero + '\'' +
                '}';
    }
}


