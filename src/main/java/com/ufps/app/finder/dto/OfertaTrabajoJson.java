/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufps.app.finder.entity.TipoContrato;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author jerson
 */
@Data
public class OfertaTrabajoJson {

    private Integer id;

    private String titulo;

    private String contenido;

    private String salario;

    private int jornada;

    private TipoContratoJson idTipoContrato;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd", timezone = "UTC")
    private Date fecha;

    private int experiencia;

    private int idEmpresa;

    private SectorJson idSector;

}
