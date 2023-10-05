/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import com.ufps.app.finder.entity.OfertaTrabajo;
import com.ufps.app.finder.entity.Profesional;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author jerson
 */
@Data
public class OfertaProfesionalJson {
      
    private Integer id;
  
    private int estado;

    private int idOfertaTrabajo;
  
    private int idProfesional;
    
    
}
