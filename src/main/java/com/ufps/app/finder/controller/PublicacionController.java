/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.ProfesionalListJson;
import com.ufps.app.finder.dto.PublicacionJson;
import com.ufps.app.finder.dto.UsuarioJson;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Publicacion;
import com.ufps.app.finder.repository.ProfesionalRepository;
import com.ufps.app.finder.repository.PublicacionRepository;
import com.ufps.app.finder.repository.UsuarioRepository;
import java.util.ArrayList;
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
@RequestMapping("/publicacion")
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) publicacionRepository.findAllByOrderByIdDesc();
        ArrayList<PublicacionJson> lista = new ArrayList<PublicacionJson>();
        for (Publicacion x : publicaciones) {
            PublicacionJson p = new PublicacionJson();
            p.setId(x.getId());
            p.setResumen(x.getResumen());
            p.setTitulo(x.getTitulo());
            p.setContenido(x.getContenido());
            p.setCosto(x.getCosto());
            p.setIdProfesional(x.getIdProfesional().getId());

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/listprofesionalid")
    public ResponseEntity listbyidprofesional(@RequestParam("id") int id) {

        Profesional ps = new Profesional();
        ps.setId(id);

        ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) publicacionRepository.findByIdProfesionalOrderByIdDesc(ps);
        ArrayList<PublicacionJson> lista = new ArrayList<PublicacionJson>();
        for (Publicacion x : publicaciones) {
            PublicacionJson p = new PublicacionJson();
            p.setId(x.getId());
            p.setResumen(x.getResumen());
            p.setTitulo(x.getTitulo());
            p.setContenido(x.getContenido());
            p.setCosto(x.getCosto());

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/obtener")
    public ResponseEntity obtener(@RequestParam("id") int id) {

        Publicacion x = publicacionRepository.findById(id);

        PublicacionJson p = new PublicacionJson();
        p.setId(x.getId());
        p.setResumen(x.getResumen());
        p.setTitulo(x.getTitulo());
        p.setContenido(x.getContenido());
        p.setCosto(x.getCosto());
        p.setIdProfesional(x.getIdProfesional().getId());

        return ResponseEntity.ok(p);

    }

    @PostMapping("/registro")
    public ResponseEntity registro(PublicacionJson pub) {

        Publicacion p = new Publicacion();

        p.setTitulo(pub.getTitulo());
        p.setResumen(pub.getResumen());
        p.setContenido(pub.getContenido());
        p.setCosto(pub.getCosto());

        Profesional pp = profesionalRepository.findById(pub.getIdProfesional());

        p.setIdProfesional(pp);

        publicacionRepository.save(p);

        return ResponseEntity.ok(pub);

    }

    @PostMapping("/editar")
    public ResponseEntity editar(PublicacionJson pub) {

        Publicacion p = publicacionRepository.findById(pub.getId());

        p.setTitulo(pub.getTitulo());
        p.setResumen(pub.getResumen());
        p.setContenido(pub.getContenido());
        p.setCosto(pub.getCosto());

        publicacionRepository.save(p);

        return ResponseEntity.ok(p);

    }

    @PostMapping("/eliminar")
    public ResponseEntity borrar(@RequestBody PublicacionJson id) {

        Publicacion p = new Publicacion();
        p.setId(id.getId());

        try {
            publicacionRepository.delete(p);
            MensajeJson msg = new MensajeJson();
            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
    }

}
