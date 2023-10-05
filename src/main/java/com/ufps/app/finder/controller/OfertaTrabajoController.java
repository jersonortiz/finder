/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.EmpresaJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.OfertaTrabajoJson;
import com.ufps.app.finder.dto.PublicacionJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.dto.TipoContratoJson;
import com.ufps.app.finder.entity.Empresa;
import com.ufps.app.finder.entity.OfertaTrabajo;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Publicacion;
import com.ufps.app.finder.entity.Sector;
import com.ufps.app.finder.entity.TipoContrato;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.EmpresaRepository;
import com.ufps.app.finder.repository.OfertaTrabajoRepository;
import com.ufps.app.finder.repository.SectorRepository;
import com.ufps.app.finder.repository.TipoContratoRepository;
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
@RequestMapping("/oferta")
public class OfertaTrabajoController {

    @Autowired
    private OfertaTrabajoRepository ofertaTrabajoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private TipoContratoRepository tipoContratoRepository;

    @GetMapping("/list")
    public ResponseEntity list() {
        ArrayList<OfertaTrabajo> em = (ArrayList<OfertaTrabajo>) ofertaTrabajoRepository.findAllByOrderByIdDesc();
        ArrayList<OfertaTrabajoJson> lista = new ArrayList<OfertaTrabajoJson>();

        for (OfertaTrabajo x : em) {

            OfertaTrabajoJson e = ofertaTrabajoToOfertaTrabajoJson(x);
            lista.add(e);

        }

        return ResponseEntity.ok(lista);

    }

    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {

        Optional<OfertaTrabajo> eo = ofertaTrabajoRepository.findById(id);

        if (eo.isEmpty()) {

            MensajeJson m = new MensajeJson();
            m.setMsg("oferta no existe");
            return ResponseEntity.ok(m);
        }

        OfertaTrabajo ex = eo.get();

        OfertaTrabajoJson ej = ofertaTrabajoToOfertaTrabajoJson(ex);

        return ResponseEntity.ok(ej);

    }

    @GetMapping("/listactivo")
    public ResponseEntity listActivo() {

        ArrayList<OfertaTrabajo> publicaciones = (ArrayList<OfertaTrabajo>) ofertaTrabajoRepository.findAllByOrderByIdDesc();

        ArrayList<OfertaTrabajoJson> lista = new ArrayList<OfertaTrabajoJson>();
        for (OfertaTrabajo x : publicaciones) {

            Empresa pr = x.getIdEmpresa();

            if (!pr.getEstado()) {
                continue;
            }

            OfertaTrabajoJson p = ofertaTrabajoToOfertaTrabajoJson(x);

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/porempresa")
    public ResponseEntity porEmpresa(@RequestParam("id") int id) {

        Empresa ps = new Empresa();
        ps.setId(id);

        ArrayList<OfertaTrabajo> publicaciones = (ArrayList<OfertaTrabajo>) ofertaTrabajoRepository.findByIdEmpresaOrderByIdDesc(ps);

        if (publicaciones.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no");
            return ResponseEntity.ok(m);
        }

        ArrayList<OfertaTrabajoJson> lista = new ArrayList<OfertaTrabajoJson>();
        for (OfertaTrabajo x : publicaciones) {
            OfertaTrabajoJson p = ofertaTrabajoToOfertaTrabajoJson(x);

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/porusuario")
    public ResponseEntity porUsuario(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);

        Optional<Empresa> op = empresaRepository.findByIdPersona(u);

        MensajeJson m = new MensajeJson();

        if (op.isEmpty()) {
            m.setMsg("vacio");
            return new ResponseEntity(m, HttpStatus.BAD_REQUEST);
        }

        Empresa ps = op.get();

        ArrayList<OfertaTrabajo> publicaciones = (ArrayList<OfertaTrabajo>) ofertaTrabajoRepository.findByIdEmpresaOrderByIdDesc(ps);

        ArrayList<OfertaTrabajoJson> lista = new ArrayList<OfertaTrabajoJson>();
        for (OfertaTrabajo x : publicaciones) {

            OfertaTrabajoJson p = ofertaTrabajoToOfertaTrabajoJson(x);

            lista.add(p);
        }
        return ResponseEntity.ok(lista);

    }

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody OfertaTrabajoJson pub) {

        OfertaTrabajo p = new OfertaTrabajo();

        p.setTitulo(pub.getTitulo());

        Optional<Empresa> op = empresaRepository.findById(pub.getIdEmpresa());

        p.setIdEmpresa(op.get());
        p.setTitulo(pub.getTitulo());
        p.setContenido(pub.getContenido());
        p.setJornada(pub.getJornada());
        p.setSalario(pub.getSalario());
        p.setFecha(pub.getFecha());
        p.setExperiencia(pub.getExperiencia());

        Optional<Sector> so = sectorRepository.findById(pub.getIdSector().getId());

        p.setIdSector(so.get());

        Optional<Empresa> pp = empresaRepository.findById(pub.getIdEmpresa());

        p.setIdEmpresa(pp.get());

        Optional<TipoContrato> otc = tipoContratoRepository.findById(pub.getIdTipoContrato().getId());

        p.setIdTipoContrato(otc.get());

        p = ofertaTrabajoRepository.save(p);

        pub.setId(p.getId());

        return ResponseEntity.ok(pub);

    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody OfertaTrabajoJson pub) {

        Optional<OfertaTrabajo> op = ofertaTrabajoRepository.findById(pub.getId());
        if (op.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no");
            return ResponseEntity.ok(m);
        }

        OfertaTrabajo p = op.get();

        p.setTitulo(pub.getTitulo());

        Optional<Empresa> oe = empresaRepository.findById(pub.getIdEmpresa());

        p.setIdEmpresa(oe.get());
        p.setTitulo(pub.getTitulo());
        p.setContenido(pub.getContenido());
        p.setJornada(pub.getJornada());
        p.setSalario(pub.getSalario());
        p.setFecha(pub.getFecha());
        p.setExperiencia(pub.getExperiencia());

        Optional<Sector> so = sectorRepository.findById(pub.getIdSector().getId());

        p.setIdSector(so.get());

        Optional<Empresa> pp = empresaRepository.findById(pub.getIdEmpresa());

        p.setIdEmpresa(pp.get());

        Optional<TipoContrato> otc = tipoContratoRepository.findById(pub.getIdTipoContrato().getId());

        p.setIdTipoContrato(otc.get());

        p = ofertaTrabajoRepository.save(p);

        pub.setId(p.getId());

        return ResponseEntity.ok(pub);

    }

    @PostMapping("/eliminar")
    public ResponseEntity borrar(@RequestBody OfertaTrabajoJson id) {

        OfertaTrabajo p = new OfertaTrabajo();
        p.setId(id.getId());

        try {
            ofertaTrabajoRepository.delete(p);
            MensajeJson msg = new MensajeJson();
            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
    }

    public OfertaTrabajoJson ofertaTrabajoToOfertaTrabajoJson(OfertaTrabajo ot) {
        OfertaTrabajoJson otj = new OfertaTrabajoJson();

        otj.setId(ot.getId());
        otj.setIdEmpresa(ot.getIdEmpresa().getId());
        otj.setTitulo(ot.getTitulo());
        otj.setContenido(ot.getContenido());

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
