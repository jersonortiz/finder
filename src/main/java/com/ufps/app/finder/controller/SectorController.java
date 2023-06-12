/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.PublicacionJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.entity.Sector;
import com.ufps.app.finder.repository.SectorRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {

        Optional<Sector> op = sectorRepository.findById(id);
        MensajeJson msg = new MensajeJson();
        if (op.isEmpty()) {

            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }

        Sector p = op.get();

        SectorJson sj = new SectorJson();

        sj.setId(p.getId());
        sj.setNombre(p.getNombre());

        return ResponseEntity.ok(sj);
    }

    @PostMapping("/registro")
    public ResponseEntity registroTipo(@RequestBody SectorJson serv) {

        Sector p = new Sector();

        p.setNombre(serv.getNombre());

        p = sectorRepository.save(p);

        serv.setId(p.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/editar")
    public ResponseEntity editarTipo(@RequestBody SectorJson serv) {

        Optional<Sector> op = sectorRepository.findById(serv.getId());
        MensajeJson msg = new MensajeJson();
        if (op.isEmpty()) {

            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }

        Sector p = op.get();

        p.setNombre(serv.getNombre());

        p = sectorRepository.save(p);

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/eliminar")
    public ResponseEntity eliminar(@RequestBody SectorJson serv) {

        Optional<Sector> op = sectorRepository.findById(serv.getId());

        MensajeJson msg = new MensajeJson();

        if (op.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }

        Sector p = op.get();

        try {
            sectorRepository.delete(p);

            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {

            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

    }

}
