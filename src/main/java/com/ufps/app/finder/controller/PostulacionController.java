/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.EmpresaJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.OfertaProfesionalJson;
import com.ufps.app.finder.entity.Empresa;
import com.ufps.app.finder.entity.OfertaProfesional;
import com.ufps.app.finder.entity.OfertaTrabajo;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.repository.OfertaProfesionalRepository;
import com.ufps.app.finder.repository.OfertaTrabajoRepository;
import com.ufps.app.finder.repository.ProfesionalRepository;
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
@RequestMapping("/postulacion")
public class PostulacionController {
    
    @Autowired
    OfertaProfesionalRepository ofertaProfesionalRepository;
    
    @Autowired
    private OfertaTrabajoRepository ofertaTrabajoRepository;
    
    @Autowired
    private ProfesionalRepository profesionalRepository;
    
    @GetMapping("/list")
    public ResponseEntity list() {
        ArrayList<OfertaProfesional> em = (ArrayList<OfertaProfesional>) ofertaProfesionalRepository.findAll();
        ArrayList<OfertaProfesionalJson> lista = new ArrayList<OfertaProfesionalJson>();
        
        for (OfertaProfesional x : em) {
            
            OfertaProfesionalJson opj = new OfertaProfesionalJson();
            
            opj.setId(x.getId());
            opj.setIdProfesional(x.getIdProfesional().getId());
            opj.setIdOfertaTrabajo(x.getIdOfertaTrabajo().getId());
            opj.setEstado(x.getEstado());
            
            lista.add(opj);
            
        }
        
        return ResponseEntity.ok(lista);
        
    }
    
    @GetMapping("/listprofesional")
    public ResponseEntity listProfesional(@RequestParam("id") int id) {
        
        Profesional p = new Profesional();
        p.setId(id);
        ArrayList<OfertaProfesional> em = (ArrayList<OfertaProfesional>) ofertaProfesionalRepository.findByIdProfesional(p);
        ArrayList<OfertaProfesionalJson> lista = new ArrayList<OfertaProfesionalJson>();
        
        for (OfertaProfesional x : em) {
            
            OfertaProfesionalJson opj = new OfertaProfesionalJson();
            
            opj.setId(x.getId());
            opj.setIdProfesional(x.getIdProfesional().getId());
            opj.setIdOfertaTrabajo(x.getIdOfertaTrabajo().getId());
            opj.setEstado(x.getEstado());
            
            lista.add(opj);
            
        }
        
        return ResponseEntity.ok(lista);
        
    }
    
    @GetMapping("/listoferta")
    public ResponseEntity listOferta(@RequestParam("id") int id) {
        
        OfertaTrabajo p = new OfertaTrabajo();
        p.setId(id);
        ArrayList<OfertaProfesional> em = (ArrayList<OfertaProfesional>) ofertaProfesionalRepository.findByIdOfertaTrabajo(p);
        ArrayList<OfertaProfesionalJson> lista = new ArrayList<OfertaProfesionalJson>();
        
        for (OfertaProfesional x : em) {
            
            OfertaProfesionalJson opj = new OfertaProfesionalJson();
            
            opj.setId(x.getId());
            opj.setIdProfesional(x.getIdProfesional().getId());
            opj.setIdOfertaTrabajo(x.getIdOfertaTrabajo().getId());
            opj.setEstado(x.getEstado());
            
            lista.add(opj);
            
        }
        
        return ResponseEntity.ok(lista);
        
    }
    
    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {
        
        Optional<OfertaProfesional> ps = ofertaProfesionalRepository.findById(id);
        
        if (ps.isEmpty()) {
            
            MensajeJson m = new MensajeJson();
            m.setMsg("oferta no existe");
            return ResponseEntity.ok(m);
        }
        
        OfertaProfesional ex = ps.get();
        
        OfertaProfesionalJson ej = new OfertaProfesionalJson();
        
        ej.setId(ex.getId());
        ej.setEstado(ex.getEstado());
        ej.setIdOfertaTrabajo(ex.getIdOfertaTrabajo().getId());
        ej.setIdProfesional(ex.getIdProfesional().getId());
        
        return ResponseEntity.ok(ej);
        
    }
    
    @PostMapping("/postular")
    public ResponseEntity postular(@RequestBody OfertaProfesionalJson serv) {
        
        OfertaProfesional op = new OfertaProfesional();
        
        Profesional p = profesionalRepository.findById(serv.getIdProfesional());
        
        Optional<OfertaTrabajo> oot = ofertaTrabajoRepository.findById(serv.getIdOfertaTrabajo());
        
        if (oot.isEmpty()) {
            
            MensajeJson m = new MensajeJson();
            m.setMsg("oferta no existe");
            return ResponseEntity.ok(m);
        }
        
        if (p == null) {
            
            MensajeJson m = new MensajeJson();
            m.setMsg("profesional no existe");
            return ResponseEntity.ok(m);
        }
        
        OfertaTrabajo ot = oot.get();
        
        Optional<OfertaProfesional> tp = ofertaProfesionalRepository.findByIdProfesionalAndIdOfertaTrabajo(p, ot);
        
        if (tp.isPresent()) {
            
            MensajeJson m = new MensajeJson();
            m.setMsg("ya esta postulado");
            return ResponseEntity.ok(m);
        }
        
        op.setIdProfesional(p);
        op.setIdOfertaTrabajo(ot);
        op.setEstado(0);
        op = ofertaProfesionalRepository.save(op);
        
        serv.setId(op.getId());
        
        return ResponseEntity.ok(serv);
    }
    
    @PostMapping("/retirarpostulacion")
    public ResponseEntity borrar(@RequestBody OfertaProfesionalJson pro) {
        
        OfertaProfesional p = new OfertaProfesional();
        p.setId(pro.getId());
        
        Optional<OfertaProfesional> ps = ofertaProfesionalRepository.findById(p.getId());
        if (ps.isEmpty()) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no existe");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
        
        try {
            ofertaProfesionalRepository.delete(ps.get());
            MensajeJson msg = new MensajeJson();
            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/aceptar")
    public ResponseEntity aceptar(@RequestBody OfertaProfesionalJson serv) {
        
        OfertaProfesional p = new OfertaProfesional();
        p.setId(serv.getId());
        
        Optional<OfertaProfesional> ps = ofertaProfesionalRepository.findById(p.getId());
        
        if (ps.isEmpty()) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no existe");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
        
        OfertaProfesional op = ps.get();
        
        op.setEstado(1);
        
        op = ofertaProfesionalRepository.save(op);
        
        serv.setEstado(1);
        
        return ResponseEntity.ok(serv);
    }
    
    @PostMapping("/rechazar")
    public ResponseEntity rechazar(@RequestBody OfertaProfesionalJson serv) {
        
        OfertaProfesional p = new OfertaProfesional();
        p.setId(serv.getId());
        
        Optional<OfertaProfesional> ps = ofertaProfesionalRepository.findById(p.getId());
        
        if (ps.isEmpty()) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no existe");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
        
        OfertaProfesional op = ps.get();
        
        op.setEstado(2);
        
        op = ofertaProfesionalRepository.save(op);
        
        serv.setEstado(2);
        
        return ResponseEntity.ok(serv);
    }
    
}
