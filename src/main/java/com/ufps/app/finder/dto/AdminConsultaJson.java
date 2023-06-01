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
public class AdminConsultaJson {
    
    private ProfesionalJson profesional;
    
    private ArrayList<ProfesionJson> profesiones;
    
    private ArrayList<PublicacionJson> publicaciones;
    
    
}
