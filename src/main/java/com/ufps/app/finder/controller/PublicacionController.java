/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.PublicacionJson;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Publicacion;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.ProfesionalRepository;
import com.ufps.app.finder.repository.PublicacionRepository;
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

    @GetMapping("/listactivo")
    public ResponseEntity listActivo() {

        ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) publicacionRepository.findAllByOrderByIdDesc();

        ArrayList<PublicacionJson> lista = new ArrayList<PublicacionJson>();
        for (Publicacion x : publicaciones) {

            Profesional pr = x.getIdProfesional();

            if (!pr.getEstado()) {
                continue;
            }

            PublicacionJson p = PublicaciontoPublicacionJson(x, pr.getId());

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/porprofesional")
    public ResponseEntity porProfesional(@RequestParam("id") int id) {

        Profesional ps = new Profesional();
        ps.setId(id);

        ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) publicacionRepository.findByIdProfesionalOrderByIdDesc(ps);

        if (publicaciones.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no");
            return ResponseEntity.ok(m);
        }

        ArrayList<PublicacionJson> lista = new ArrayList<PublicacionJson>();
        for (Publicacion x : publicaciones) {
            PublicacionJson p = PublicaciontoPublicacionJson(x, ps.getId());

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/porusuario")
    public ResponseEntity porUsuario(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        MensajeJson m = new MensajeJson();

        if (op.isEmpty()) {
            m.setMsg("vacio");
            return new ResponseEntity(m, HttpStatus.BAD_REQUEST);
        }

        Profesional ps = op.get();

        ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) publicacionRepository.findByIdProfesionalOrderByIdDesc(ps);

        ArrayList<PublicacionJson> lista = new ArrayList<PublicacionJson>();
        for (Publicacion x : publicaciones) {

            PublicacionJson p = PublicaciontoPublicacionJson(x, ps.getId());

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/obtener")
    public ResponseEntity obtener(@RequestParam("id") int id) {

        Optional<Publicacion> ox = publicacionRepository.findById(id);

        if (ox.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no");
            return ResponseEntity.ok(m);
        }

        Publicacion x = ox.get();

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
    public ResponseEntity registro(@RequestBody PublicacionJson pub) {

        Publicacion p = new Publicacion();

        System.out.println(pub.toString());

        p.setTitulo(pub.getTitulo());
        p.setResumen(pub.getResumen());
        p.setContenido(pub.getContenido());
        p.setCosto(pub.getCosto());

        Profesional pp = profesionalRepository.findById(pub.getIdProfesional());

        p.setIdProfesional(pp);

        p = publicacionRepository.save(p);

        pub.setId(p.getId());

        return ResponseEntity.ok(pub);

    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody PublicacionJson pub) {

        Optional<Publicacion> op = publicacionRepository.findById(pub.getId());
        if (op.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no");
            return ResponseEntity.ok(m);
        }

        Publicacion p = op.get();

        p.setTitulo(pub.getTitulo());
        p.setResumen(pub.getResumen());
        p.setContenido(pub.getContenido());
        p.setCosto(pub.getCosto());

        p = publicacionRepository.save(p);

        pub.setId(p.getId());

        return ResponseEntity.ok(pub);

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

    private PublicacionJson PublicaciontoPublicacionJson(Publicacion x, int id) {
        PublicacionJson p = new PublicacionJson();
        p.setId(x.getId());
        p.setResumen(x.getResumen());
        p.setTitulo(x.getTitulo());
        p.setContenido(x.getContenido());
        p.setCosto(x.getCosto());
        p.setIdProfesional(id);

        return p;
    }
}
