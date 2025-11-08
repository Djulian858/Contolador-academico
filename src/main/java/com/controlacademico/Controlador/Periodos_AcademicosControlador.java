package com.controlacademico.Controlador;
import com.controlacademico.Modelo.*;
import java.util.List;


public class Periodos_AcademicosControlador {
    private Periodos_AcademicosDAO periodoAcademicoDAO;

    public Periodos_AcademicosControlador(Periodos_AcademicosDAO periodoAcademicoDAO) {
        this.periodoAcademicoDAO = periodoAcademicoDAO;
    }

    public boolean crearPeriodoAcademico(Periodos_Academicos periodo) {
        return periodoAcademicoDAO.crearPeriodos_Academicos(periodo);
    }

    public boolean actualizarPeriodoAcademico(Periodos_Academicos periodo) {
        return periodoAcademicoDAO.actualizarPeriodos_Academicos(periodo);
    }

    public boolean eliminarPeriodoAcademico(int id) {
        return periodoAcademicoDAO.eliminarPeriodos_Academicos(id);
    }

    public Periodos_Academicos obtenerPeriodoAcademicoPorId(int id) {
        return periodoAcademicoDAO.findById(id);
    }

    public List<Periodos_Academicos> obtenerTodosLosPeriodosAcademicos() {
        return periodoAcademicoDAO.listarTodos();
    }
}
