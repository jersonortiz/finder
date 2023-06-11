/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.HabilidadJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.ProfesionJson;
import com.ufps.app.finder.dto.ProfesionaProfesionJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.entity.Habilidades;
import com.ufps.app.finder.entity.Profesion;
import com.ufps.app.finder.entity.ProfesionaProfesion;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Sector;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.ProfesionRepository;
import com.ufps.app.finder.repository.ProfesionaProfesionRepository;
import com.ufps.app.finder.repository.ProfesionalRepository;
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
@RequestMapping("/profesion")
public class ProfesionController {

    @Autowired
    private ProfesionRepository profesionRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private ProfesionaProfesionRepository profesionaProfesionRepository;

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

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody ProfesionJson serv) {

        Profesion p = new Profesion();

        p.setProfesion(serv.getProfesion());

        Optional<Sector> so = sectorRepository.findById(serv.getSector().getId());
        MensajeJson msg = new MensajeJson();

        if (so.isEmpty()) {
            msg.setMsg("sector no existe");
            return ResponseEntity.ok(msg);
        }

        Sector s = so.get();

        p.setIdSector(s);

        p = profesionRepository.save(p);

        serv.setId(p.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/asignar")
    public ResponseEntity asginar(@RequestBody ProfesionaProfesionJson ppj) {

        Profesional pr = new Profesional();
        pr.setId(ppj.getIdProfesional());

        Profesion pro = new Profesion();
        pro.setId(ppj.getIdProfesion());

        Optional<ProfesionaProfesion> propro = profesionaProfesionRepository.findByIdProfesionalAndIdProfesion(pr, pro);

        if (propro.isPresent()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("ya asignado");
            return ResponseEntity.ok(m);
        }

        ProfesionaProfesion prodb = new ProfesionaProfesion();
        prodb.setIdProfesion(pro);
        prodb.setIdProfesional(pr);

        ProfesionaProfesionJson rett = new ProfesionaProfesionJson();

        rett.setIdProfesion(ppj.getIdProfesion());
        rett.setIdProfesional(ppj.getIdProfesional());

        prodb = profesionaProfesionRepository.save(prodb);

        rett.setId(prodb.getId());

        return ResponseEntity.ok(rett);

    }

    @PostMapping("/asignarwithuseid")
    public ResponseEntity asginarConIdUsuario(@RequestBody ProfesionaProfesionJson ppj) {

        Usuario u = new Usuario();
        u.setId(ppj.getIdProfesional());

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        if (op.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no es profesional");
            return ResponseEntity.ok(m);
        }

        Profesional pr = op.get();

        Profesion pro = new Profesion();
        pro.setId(ppj.getIdProfesion());

        Optional<ProfesionaProfesion> propro = profesionaProfesionRepository.findByIdProfesionalAndIdProfesion(pr, pro);

        if (propro.isPresent()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("ya asignado");
            return ResponseEntity.ok(m);
        }

        ProfesionaProfesion prodb = new ProfesionaProfesion();
        prodb.setIdProfesion(pro);
        prodb.setIdProfesional(pr);

        ProfesionaProfesionJson rett = new ProfesionaProfesionJson();

        rett.setIdProfesion(ppj.getIdProfesion());
        rett.setIdProfesional(ppj.getIdProfesional());

        prodb = profesionaProfesionRepository.save(prodb);

        rett.setId(prodb.getId());

        return ResponseEntity.ok(rett);

    }

    @PostMapping("/desasignar")
    public ResponseEntity desasignar(@RequestBody ProfesionaProfesionJson ppj) {

        Profesional pr = new Profesional();
        pr.setId(ppj.getIdProfesional());

        Profesion pro = new Profesion();
        pro.setId(ppj.getIdProfesion());

        Optional<ProfesionaProfesion> propro = profesionaProfesionRepository.findByIdProfesionalAndIdProfesion(pr, pro);

        MensajeJson m = new MensajeJson();
        if (propro.isEmpty()) {

            m.setMsg("no");
            return ResponseEntity.ok(m);
        }

        ProfesionaProfesion prodb = propro.get();

        profesionaProfesionRepository.delete(prodb);

        m.setMsg("si");
        return ResponseEntity.ok(m);

    }

    @PostMapping("/desasignarwithuserid")
    public ResponseEntity desasignarWithUserID(@RequestBody ProfesionaProfesionJson ppj) {

        Usuario u = new Usuario();
        u.setId(ppj.getIdProfesional());

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        if (op.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no es profesional");
            return ResponseEntity.ok(m);
        }

        Profesional pr = op.get();

        Profesion pro = new Profesion();
        pro.setId(ppj.getIdProfesion());

        Optional<ProfesionaProfesion> propro = profesionaProfesionRepository.findByIdProfesionalAndIdProfesion(pr, pro);

        MensajeJson m = new MensajeJson();
        if (propro.isEmpty()) {

            m.setMsg("no");
            return ResponseEntity.ok(m);
        }

        ProfesionaProfesion prodb = propro.get();

        profesionaProfesionRepository.delete(prodb);

        m.setMsg("si");
        return ResponseEntity.ok(m);

    }

    @GetMapping("/listprofdisponible")
    public ResponseEntity listDisponible(@RequestParam("id") int id) {

        Profesional p = new Profesional();
        p.setId(id);

        Profesional ps = profesionalRepository.findById(p.getId());

        ArrayList<ProfesionaProfesion> relas = profesionaProfesionRepository.findByIdProfesional(p);

        ArrayList<Profesion> porremover = new ArrayList<Profesion>();
        for (ProfesionaProfesion pp : relas) {
            Profesion pro = pp.getIdProfesion();
            porremover.add(pro);
        }

        ArrayList<Profesion> profesiones = (ArrayList<Profesion>) profesionRepository.findAll();
        profesiones.removeAll(porremover);

        ArrayList<ProfesionJson> lista = new ArrayList<ProfesionJson>();
        for (Profesion x : profesiones) {

            ProfesionJson pj = new ProfesionJson();
            pj.setId(x.getId());
            pj.setProfesion(x.getProfesion());

            Sector sec = x.getIdSector();
            SectorJson s = new SectorJson();
            s.setId(sec.getId());
            s.setNombre(sec.getNombre());

            pj.setSector(s);

            lista.add(pj);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/listprofdisponiblewithuserid")
    public ResponseEntity listDisponibleUser(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        if (op.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no es profesional");
            return ResponseEntity.ok(m);
        }

        Profesional ps = op.get();

        ArrayList<ProfesionaProfesion> relas = profesionaProfesionRepository.findByIdProfesional(ps);

        ArrayList<Profesion> porremover = new ArrayList<Profesion>();
        for (ProfesionaProfesion pp : relas) {
            Profesion pro = pp.getIdProfesion();
            porremover.add(pro);
        }

        ArrayList<Profesion> profesiones = (ArrayList<Profesion>) profesionRepository.findAll();
        profesiones.removeAll(porremover);

        ArrayList<ProfesionJson> lista = new ArrayList<ProfesionJson>();
        for (Profesion x : profesiones) {

            ProfesionJson pj = new ProfesionJson();
            pj.setId(x.getId());
            pj.setProfesion(x.getProfesion());

            Sector sec = x.getIdSector();
            SectorJson s = new SectorJson();
            s.setId(sec.getId());
            s.setNombre(sec.getNombre());

            pj.setSector(s);

            lista.add(pj);
        }
        return ResponseEntity.ok(lista);

    }

}
