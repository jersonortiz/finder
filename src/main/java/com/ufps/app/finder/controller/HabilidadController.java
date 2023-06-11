/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.CompetenciaJson;
import com.ufps.app.finder.dto.HabilidadJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.entity.Competencia;
import com.ufps.app.finder.entity.Habilidades;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.CompetenciaRepository;
import com.ufps.app.finder.repository.HabilidadRepository;
import com.ufps.app.finder.repository.ProfesionalRepository;
import com.ufps.app.finder.repository.UsuarioRepository;
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
@RequestMapping("/habilidad")
public class HabilidadController {

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private HabilidadRepository habilidadRepository;

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Habilidades> lista = (ArrayList<Habilidades>) habilidadRepository.findAll();

        ArrayList<HabilidadJson> retorno = new ArrayList<HabilidadJson>();

        for (Habilidades x : lista) {

            HabilidadJson cj = HabilidadToHabilidadJson(x);

            retorno.add(cj);

        }

        return ResponseEntity.ok(retorno);

    }

    @GetMapping("/listprofesional")
    public ResponseEntity listProfesional(@RequestParam("id") int id) {

        Profesional ps = new Profesional();
        ps.setId(id);

        ArrayList<Habilidades> lista = (ArrayList<Habilidades>) habilidadRepository.findByIdProfesional(ps);

        ArrayList<HabilidadJson> retorno = new ArrayList<HabilidadJson>();

        for (Habilidades x : lista) {

            HabilidadJson cj = HabilidadToHabilidadJson(x);

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

        ArrayList<Habilidades> lista = (ArrayList<Habilidades>) habilidadRepository.findByIdProfesional(ps);

        ArrayList<HabilidadJson> retorno = new ArrayList<HabilidadJson>();

        for (Habilidades x : lista) {

            HabilidadJson tj = HabilidadToHabilidadJson(x);

            retorno.add(tj);

        }

        return ResponseEntity.ok(retorno);

    }

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody HabilidadJson serv) {

        Profesional p = new Profesional();
        p.setId(serv.getIdProfesional());

        Profesional ps = profesionalRepository.findById(p.getId());

        if (ps == null) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return ResponseEntity.ok(msg);
        }

        Habilidades c = new Habilidades();

        c.setNombre(serv.getNombre());
        c.setCertificado(serv.getCertificado());
        c.setIdProfesional(ps);

        c = habilidadRepository.save(c);
        serv.setId(c.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/registrouser")
    public ResponseEntity registrousuario(@RequestBody HabilidadJson serv) {

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

        Habilidades c = new Habilidades();

        c.setNombre(serv.getNombre());
        c.setCertificado(serv.getCertificado());
        c.setIdProfesional(ps);

        c = habilidadRepository.save(c);
        serv.setId(c.getId());
        return ResponseEntity.ok(serv);
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody HabilidadJson serv) {

        Habilidades c = new Habilidades();
        c.setId(serv.getId());

        Optional<Habilidades> to = habilidadRepository.findById(c.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        c = to.get();

        c.setNombre(serv.getNombre());
        c.setCertificado(serv.getCertificado());

        c = habilidadRepository.save(c);
        serv.setId(c.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/eliminar")
    public ResponseEntity eliminar(@RequestBody HabilidadJson serv) {

        Habilidades t = new Habilidades();
        t.setId(serv.getId());

        Optional<Habilidades> to = habilidadRepository.findById(t.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        t = to.get();

        try {
            habilidadRepository.delete(t);

            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {

            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

    }

    private HabilidadJson HabilidadToHabilidadJson(Habilidades h) {
        HabilidadJson hj = new HabilidadJson();

        hj.setId(h.getId());
        hj.setNombre(h.getNombre());
        hj.setCertificado(h.getCertificado());
        hj.setIdProfesional(h.getIdProfesional().getId());

        return hj;
    }
}
