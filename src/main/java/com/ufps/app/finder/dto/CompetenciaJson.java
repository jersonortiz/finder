/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jerson
 */
@Data
public class CompetenciaJson {

    private Integer id;

    private String competencia;

    String descripcion;

    private int idProfesional;

}
