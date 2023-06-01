/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import com.ufps.app.finder.entity.Sector;
import lombok.Data;

/**
 *
 * @author 
 */
@Data
public class ProfesionJson {

    private int id;

    private String profesion;

    private SectorJson sector;
}
