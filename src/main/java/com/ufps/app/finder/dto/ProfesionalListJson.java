/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author 
 */
@Data
public class ProfesionalListJson {

    private int id;
    
    private int idpersona;

    private String nombre;

    private String apellido;
    
    private int edad;
    
    private String telefono;
    
    private String email;
    
    private String ciudad;
    
    private boolean estado;

    private ArrayList<ProfesionJson> profesiones;
   
}
