/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.EmpresaJson;
import com.ufps.app.finder.entity.Empresa;
import com.ufps.app.finder.repository.EmpresaRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jerson
 */
@RestController
@CrossOrigin
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping("/list")
    public ResponseEntity list() {
        ArrayList<Empresa> em = (ArrayList<Empresa>) empresaRepository.findAll();
        ArrayList<EmpresaJson> lista = new ArrayList<EmpresaJson>();
        
        for(Empresa x :em){
            if(x.getEstado()>1){
                continue;
            }
        }

    }

}
