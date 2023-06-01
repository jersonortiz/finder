/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.PublicacionJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.entity.Sector;
import com.ufps.app.finder.repository.SectorRepository;
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
@RequestMapping("/sector")
public class SectorController {

    @Autowired
    private SectorRepository sectorRepository;

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Sector> sectores = (ArrayList<Sector>) sectorRepository.findAll();
        ArrayList<SectorJson> lista = new ArrayList<SectorJson>();
        for (Sector x : sectores) {

            SectorJson s = new SectorJson();
            s.setId(x.getId());
            s.setNombre(x.getNombre());

            lista.add(s);
        }
        return ResponseEntity.ok(lista);

    }

}
