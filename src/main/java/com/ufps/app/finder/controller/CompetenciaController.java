/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.CompetenciaJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.entity.Competencia;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.CompetenciaRepository;
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
@RequestMapping("/competencia")
public class CompetenciaController {

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private CompetenciaRepository competenciaRepository;

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Competencia> lista = (ArrayList<Competencia>) competenciaRepository.findAll();

        ArrayList<CompetenciaJson> retorno = new ArrayList<CompetenciaJson>();

        for (Competencia x : lista) {

            CompetenciaJson cj = competenciaToCompetenciaJson(x);

            retorno.add(cj);

        }

        return ResponseEntity.ok(retorno);

    }

    @GetMapping("/listprofesional")
    public ResponseEntity listProfesional(@RequestParam("id") int id) {

        Profesional ps = new Profesional();
        ps.setId(id);

        ArrayList<Competencia> lista = (ArrayList<Competencia>) competenciaRepository.findByIdProfesional(ps);

        ArrayList<CompetenciaJson> retorno = new ArrayList<CompetenciaJson>();

        for (Competencia x : lista) {

            CompetenciaJson cj = competenciaToCompetenciaJson(x);

            retorno.add(cj);

        }

        return ResponseEntity.ok(retorno);

    }

    @GetMapping("/listuser")
    public ResponseEntity listUser(@RequestParam("id") int id) {

        System.out.println(id);
        Usuario u = new Usuario();
        u.setId(id);

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        MensajeJson msg = new MensajeJson();
        if (op.isEmpty()) {
            msg.setMsg("usuario no existe");
            return ResponseEntity.ok(msg);
        }

        Profesional ps = op.get();

        ArrayList<Competencia> lista = (ArrayList<Competencia>) competenciaRepository.findByIdProfesional(ps);

        ArrayList<CompetenciaJson> retorno = new ArrayList<CompetenciaJson>();

        for (Competencia x : lista) {

            CompetenciaJson tj = competenciaToCompetenciaJson(x);

            retorno.add(tj);

        }

        return ResponseEntity.ok(retorno);

    }

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody CompetenciaJson serv) {

        Profesional p = new Profesional();
        p.setId(serv.getIdProfesional());

        Profesional ps = profesionalRepository.findById(p.getId());

        if (ps == null) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return ResponseEntity.ok(msg);
        }

        Competencia c = new Competencia();

        c.setCompetencia(serv.getCompetencia());
        c.setDescripcion(serv.getDescripcion());
        c.setIdProfesional(ps);

        c = competenciaRepository.save(c);
        serv.setId(c.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/registrouser")
    public ResponseEntity registrousuario(@RequestBody CompetenciaJson serv) {

        System.out.println(serv);
        Usuario u = new Usuario();
        u.setId(serv.getIdProfesional());

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        MensajeJson msg = new MensajeJson();
        if (op.isEmpty()) {
            msg.setMsg("usuario no existe");
            return ResponseEntity.ok(msg);
        }

        Profesional ps = op.get();

        Competencia c = new Competencia();

        c.setCompetencia(serv.getCompetencia());
        c.setDescripcion(serv.getDescripcion());
        c.setIdProfesional(ps);

        c = competenciaRepository.save(c);
        serv.setId(c.getId());
        return ResponseEntity.ok(serv);
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody CompetenciaJson serv) {

        Competencia c = new Competencia();
        c.setId(serv.getId());

        Optional<Competencia> to = competenciaRepository.findById(c.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        c = to.get();

        c.setCompetencia(serv.getCompetencia());
        c.setDescripcion(serv.getDescripcion());

        c = competenciaRepository.save(c);
        serv.setId(c.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/eliminar")
    public ResponseEntity eliminar(@RequestBody CompetenciaJson serv) {

        Competencia t = new Competencia();
        t.setId(serv.getId());

        Optional<Competencia> to = competenciaRepository.findById(t.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        t = to.get();

        try {
            competenciaRepository.delete(t);

            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {

            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {

        Competencia t = new Competencia();
        t.setId(id);

        Optional<Competencia> to = competenciaRepository.findById(t.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        t = to.get();

        CompetenciaJson cj = competenciaToCompetenciaJson(t);

        return ResponseEntity.ok(cj);

    }

    private CompetenciaJson competenciaToCompetenciaJson(Competencia c) {
        CompetenciaJson cj = new CompetenciaJson();

        cj.setId(c.getId());
        cj.setCompetencia(c.getCompetencia());
        cj.setDescripcion(c.getDescripcion());
        cj.setIdProfesional(c.getIdProfesional().getId());

        return cj;

    }

}
