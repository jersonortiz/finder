/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import com.ufps.app.finder.entity.Usuario;
import lombok.Data;

/**
 *
 * @author jerson
 */
@Data
public class ProfesionalJson {

    private int id;

    private boolean estado;

    private String ciudad;

    private UsuarioJson idPersona;

}
