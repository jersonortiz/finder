/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.EmpresaJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.OfertaProfesionalConsultaJson;
import com.ufps.app.finder.dto.OfertaProfesionalJson;
import com.ufps.app.finder.dto.OfertaTrabajoJson;
import com.ufps.app.finder.dto.ProfesionalListJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.dto.TipoContratoJson;
import com.ufps.app.finder.entity.Empresa;
import com.ufps.app.finder.entity.OfertaProfesional;
import com.ufps.app.finder.entity.OfertaTrabajo;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Sector;
import com.ufps.app.finder.entity.TipoContrato;
import com.ufps.app.finder.entity.Usuario;
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

            OfertaProfesionalJson opj = OfertaProfesionalToJson(x);

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

            OfertaProfesionalJson opj = OfertaProfesionalToJson(x);

            lista.add(opj);

        }

        return ResponseEntity.ok(lista);

    }

    @GetMapping("/listusuario")
    public ResponseEntity listUsuario(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);

        Optional<Profesional> ps = profesionalRepository.findByIdPersona(u);

        if (ps.isEmpty()) {

            MensajeJson m = new MensajeJson();
            m.setMsg("el contratista no existe");
            return ResponseEntity.ok(m);
        }

        Profesional p = ps.get();

        ArrayList<OfertaProfesional> em = (ArrayList<OfertaProfesional>) ofertaProfesionalRepository.findByIdProfesional(p);
        ArrayList<OfertaProfesionalConsultaJson> lista = new ArrayList<OfertaProfesionalConsultaJson>();

        for (OfertaProfesional x : em) {

            OfertaProfesionalConsultaJson opj = OfertaProfesionalConsultaToJson(x);

            lista.add(opj);

        }

        return ResponseEntity.ok(lista);

    }

    @GetMapping("/listoferta")
    public ResponseEntity listOferta(@RequestParam("id") int id) {

        OfertaTrabajo p = new OfertaTrabajo();
        p.setId(id);
        ArrayList<OfertaProfesional> em = (ArrayList<OfertaProfesional>) ofertaProfesionalRepository.findByIdOfertaTrabajo(p);
        ArrayList<OfertaProfesionalConsultaJson> lista = new ArrayList<OfertaProfesionalConsultaJson>();

        for (OfertaProfesional x : em) {

            OfertaProfesionalConsultaJson opj = OfertaProfesionalConsultaToJson(x);

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

        OfertaProfesionalJson ej = OfertaProfesionalToJson(ps.get());

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

    @PostMapping("/postularusuario")
    public ResponseEntity postularUsuario(@RequestBody OfertaProfesionalJson serv) {

        OfertaProfesional op = new OfertaProfesional();

        Usuario u = new Usuario();
        u.setId(serv.getIdProfesional());

        Optional<Profesional> ps = profesionalRepository.findByIdPersona(u);

        Optional<OfertaTrabajo> oot = ofertaTrabajoRepository.findById(serv.getIdOfertaTrabajo());

        if (oot.isEmpty()) {

            MensajeJson m = new MensajeJson();
            m.setMsg("oferta no existe");
            return ResponseEntity.ok(m);
        }

        if (ps.isEmpty()) {

            MensajeJson m = new MensajeJson();
            m.setMsg("profesional no existe");
            return ResponseEntity.ok(m);
        }

        Profesional p = ps.get();

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

    public OfertaProfesionalJson OfertaProfesionalToJson(OfertaProfesional op) {
        OfertaProfesionalJson opcj = new OfertaProfesionalJson();

        opcj.setId(op.getId());
        opcj.setEstado(op.getEstado());
        opcj.setIdOfertaTrabajo(op.getIdOfertaTrabajo().getId());
        opcj.setIdProfesional(op.getIdProfesional().getId());

        return opcj;
    }

    public OfertaProfesionalConsultaJson OfertaProfesionalConsultaToJson(OfertaProfesional op) {
        OfertaProfesionalConsultaJson opcj = new OfertaProfesionalConsultaJson();

        opcj.setId(op.getId());
        opcj.setEstado(op.getEstado());

        Profesional p = op.getIdProfesional();
        Usuario x = p.getIdPersona();

        opcj.setIdProfesional(ProfesionalToProfesionalListJson(p, x));

        opcj.setIdOfertaTrabajo(ofertaTrabajoToOfertaTrabajoJson(op.getIdOfertaTrabajo()));

        return opcj;
    }

    private ProfesionalListJson ProfesionalToProfesionalListJson(Profesional ps, Usuario xper) {

        ProfesionalListJson p = new ProfesionalListJson();
        p.setId(ps.getId());
        p.setNombre(xper.getNombre());
        p.setApellido(xper.getApellido());
        p.setFechaNacimiento(xper.getFechaNacimiento());
        p.setTelefono(xper.getTelefono());
        p.setEmail(xper.getEmail());
        p.setCiudad(ps.getCiudad());
        p.setIdpersona(xper.getId());
        p.setEstado(false);
        if (ps.getEstado()) {
            p.setEstado(true);
        }

        return p;
    }

    public OfertaTrabajoJson ofertaTrabajoToOfertaTrabajoJson(OfertaTrabajo ot) {
        OfertaTrabajoJson otj = new OfertaTrabajoJson();

        otj.setId(ot.getId());
        otj.setIdEmpresa(ot.getIdEmpresa().getId());
        otj.setTitulo(ot.getTitulo());
        otj.setContenido(ot.getContenido());
        otj.setEstado(ot.getEstado());

        Sector se = ot.getIdSector();

        SectorJson s = new SectorJson();
        s.setId(se.getId());
        s.setNombre(se.getNombre());

        otj.setIdSector(s);

        otj.setSalario(ot.getSalario());
        otj.setJornada(ot.getJornada());

        otj.setFecha(ot.getFecha());
        otj.setExperiencia(ot.getExperiencia());

        TipoContratoJson tcj = TipoContratoToTipoContratoJson(ot.getIdTipoContrato());

        otj.setIdTipoContrato(tcj);

        return otj;

    }

    public TipoContratoJson TipoContratoToTipoContratoJson(TipoContrato tc) {

        TipoContratoJson tcj = new TipoContratoJson();

        tcj.setId(tc.getId());
        tcj.setNombre(tc.getNombre());
        tcj.setDescripcion(tc.getDescripcion());

        return tcj;
    }

}
