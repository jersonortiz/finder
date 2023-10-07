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
public class EmpresaConsultaJson {

    private Integer id;

    private boolean estado;

    private ArrayList<OfertaTrabajoJson> ofertaTrabajoList;

    private String nombre;
    
    private String email;

    private String telefono;

}
