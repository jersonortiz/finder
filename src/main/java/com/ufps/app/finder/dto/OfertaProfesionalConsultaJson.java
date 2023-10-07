/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import lombok.Data;

/**
 *
 * @author jerson
 */
@Data
public class OfertaProfesionalConsultaJson {

    private Integer id;

    private int estado;

    private OfertaTrabajoJson idOfertaTrabajo;

    private ProfesionalListJson idProfesional;

}
