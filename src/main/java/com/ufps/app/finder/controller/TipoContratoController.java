/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.dto.TipoContratoJson;
import com.ufps.app.finder.entity.TipoContrato;
import com.ufps.app.finder.repository.TipoContratoRepository;
import com.ufps.app.finder.repository.TipoTituloRepository;
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
 * @author jerson
 */
@RestController
@CrossOrigin
@RequestMapping("/tipocontrato")
public class TipoContratoController {

    @Autowired
    private TipoContratoRepository tipoContratoRepository;

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<TipoContrato> tipos = (ArrayList<TipoContrato>) tipoContratoRepository.findAll();
        ArrayList<TipoContratoJson> lista = new ArrayList<TipoContratoJson>();
        for (TipoContrato x : tipos) {

            TipoContratoJson s = new TipoContratoJson();
            s.setId(x.getId());
            s.setNombre(x.getNombre());
            s.setDescripcion(x.getDescripcion());

            lista.add(s);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {

        Optional<TipoContrato> op = tipoContratoRepository.findById(id);
        MensajeJson msg = new MensajeJson();
        if (op.isEmpty()) {

            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }

        TipoContrato p = op.get();

        TipoContratoJson s = new TipoContratoJson();
        s.setId(p.getId());
        s.setNombre(p.getNombre());
        s.setDescripcion(p.getDescripcion());

        return ResponseEntity.ok(s);
    }

    @PostMapping("/registro")
    public ResponseEntity registroTipo(@RequestBody TipoContratoJson serv) {

        TipoContrato p = new TipoContrato();

        p.setNombre(serv.getNombre());
        p.setDescripcion(serv.getDescripcion());

        p = tipoContratoRepository.save(p);

        serv.setId(p.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/editar")
    public ResponseEntity editarTipo(@RequestBody TipoContratoJson serv) {

        Optional<TipoContrato> op = tipoContratoRepository.findById(serv.getId());
        MensajeJson msg = new MensajeJson();
        if (op.isEmpty()) {

            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }

        TipoContrato p = op.get();

        p.setNombre(serv.getNombre());
        p.setDescripcion(serv.getDescripcion());

        p = tipoContratoRepository.save(p);

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/eliminar")
    public ResponseEntity eliminar(@RequestBody SectorJson serv) {

        Optional<TipoContrato> op = tipoContratoRepository.findById(serv.getId());

        MensajeJson msg = new MensajeJson();

        if (op.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }

        TipoContrato p = op.get();

        try {
            tipoContratoRepository.delete(p);

            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {

            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

    }

}
