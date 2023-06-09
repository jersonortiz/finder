/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author 
 */
@Data
public class UsuarioJson {

    private int id;

    private String nombre;
    
    private String apellido;

    private String documento;

    private String telefono;

    private String email;

    private String contraseña;

    private int rol;
    
    //private int edad;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd", timezone = "America/Bogota")
    private Date fechaNacimiento;
    
}
