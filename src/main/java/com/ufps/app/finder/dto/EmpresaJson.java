/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author jerson
 */
@Data
public class EmpresaJson {

    private Integer id;

    private boolean estado;

    private ArrayList<OfertaTrabajoJson> ofertaTrabajoList;

    private int idPersona;

}
