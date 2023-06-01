/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.ProfesionJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.entity.Profesion;
import com.ufps.app.finder.entity.Sector;
import com.ufps.app.finder.repository.ProfesionRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 
 */
@RestController
@CrossOrigin
@RequestMapping("/profesion")
public class ProfesionController {

    @Autowired
    private ProfesionRepository profesionRepository;

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Profesion> profesiones = (ArrayList<Profesion>) profesionRepository.findAll();
        ArrayList<ProfesionJson> lista = new ArrayList<ProfesionJson>();
        for (Profesion x : profesiones) {

            ProfesionJson p = new ProfesionJson();
            p.setId(x.getId());
            p.setProfesion(x.getProfesion());

            Sector sec = x.getIdSector();
            SectorJson s = new SectorJson();
            s.setId(sec.getId());
            s.setNombre(sec.getNombre());

            p.setSector(s);

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

}
