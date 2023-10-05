/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.EmpresaJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.OfertaTrabajoJson;
import com.ufps.app.finder.dto.ProfesionalJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.dto.TipoContratoJson;
import com.ufps.app.finder.dto.UsuarioJson;
import com.ufps.app.finder.entity.Empresa;
import com.ufps.app.finder.entity.OfertaTrabajo;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Rol;
import com.ufps.app.finder.entity.Sector;
import com.ufps.app.finder.entity.TipoContrato;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.EmpresaRepository;
import com.ufps.app.finder.repository.OfertaTrabajoRepository;
import com.ufps.app.finder.repository.RolRepository;
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
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private OfertaTrabajoRepository ofertaTrabajoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/list")
    public ResponseEntity list() {
        ArrayList<Empresa> em = (ArrayList<Empresa>) empresaRepository.findAll();
        ArrayList<EmpresaJson> lista = new ArrayList<EmpresaJson>();

        for (Empresa x : em) {
            if (!x.getEstado()) {
                continue;
            }
            EmpresaJson e = empresaToEmpresaJson(x);

            e.setOfertaTrabajoList(new ArrayList<OfertaTrabajoJson>());
            lista.add(e);

        }

        return ResponseEntity.ok(lista);

    }

    @GetMapping("/listadm")
    public ResponseEntity listAdmin() {
        ArrayList<Empresa> em = (ArrayList<Empresa>) empresaRepository.findAll();
        ArrayList<EmpresaJson> lista = new ArrayList<EmpresaJson>();

        for (Empresa x : em) {

            EmpresaJson e = empresaToEmpresaJson(x);

            e.setOfertaTrabajoList(new ArrayList<OfertaTrabajoJson>());
            lista.add(e);

        }

        return ResponseEntity.ok(lista);

    }

    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {

        Optional<Empresa> eo = empresaRepository.findById(id);

        if (eo.isEmpty()) {

            MensajeJson m = new MensajeJson();
            m.setMsg("empresa no existe");
            return ResponseEntity.ok(m);
        }

        Empresa ex = eo.get();

        EmpresaJson ej = empresaToEmpresaJson(ex);

        ArrayList<OfertaTrabajo> ofl = ofertaTrabajoRepository.findByIdEmpresa(ex);

        ArrayList<OfertaTrabajoJson> lista = new ArrayList<OfertaTrabajoJson>();

        for (OfertaTrabajo x : ofl) {
            OfertaTrabajoJson otj = ofertaTrabajoToOfertaTrabajoJson(x);
            lista.add(otj);

        }

        ej.setOfertaTrabajoList(lista);
        return ResponseEntity.ok(ej);

    }

    @GetMapping("/consultauser")
    public ResponseEntity consultaUser(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);
        Optional<Empresa> eo = empresaRepository.findByIdPersona(u);

        if (eo.isEmpty()) {

            MensajeJson m = new MensajeJson();
            m.setMsg("empresa no existe");
            return ResponseEntity.ok(m);
        }

        Empresa ex = eo.get();

        EmpresaJson ej = empresaToEmpresaJson(ex);

        ArrayList<OfertaTrabajo> ofl = ofertaTrabajoRepository.findByIdEmpresa(ex);

        ArrayList<OfertaTrabajoJson> lista = new ArrayList<OfertaTrabajoJson>();

        for (OfertaTrabajo x : ofl) {
            OfertaTrabajoJson otj = ofertaTrabajoToOfertaTrabajoJson(x);
            lista.add(otj);

        }

        ej.setOfertaTrabajoList(lista);

        return ResponseEntity.ok(ej);

    }

    @PostMapping("/suspender")
    public ResponseEntity suspension(@RequestBody EmpresaJson pro) {

        Empresa empp = new Empresa();
        empp.setId(pro.getId());

        Optional<Empresa> eo = empresaRepository.findById(pro.getId());

        if (eo.isEmpty()) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no existe");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);

        }

        Empresa em = eo.get();

        if (em.getEstado()) {
            em.setEstado(false);
        } else {
            em.setEstado(true);
        }

        empresaRepository.save(em);

        MensajeJson msg = new MensajeJson();
        msg.setMsg("ok");

        return ResponseEntity.ok(msg);

    }

    @PostMapping("/eliminar")
    public ResponseEntity borrar(@RequestBody EmpresaJson pro) {

        Empresa p = new Empresa();
        p.setId(pro.getId());

        Optional<Empresa> ps = empresaRepository.findById(p.getId());
        if (ps.isEmpty()) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no existe");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
        Usuario usr = ps.get().getIdPersona();

        try {
            usuarioRepository.delete(usr);
            MensajeJson msg = new MensajeJson();
            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
    }

    /*
    toma el campo de nombre de usuario para el nombre de la empresa
     */
    @PostMapping("/cambioempresa")
    public ResponseEntity cambioEmpresa(@RequestBody UsuarioJson user) {

        Usuario u = usuarioRepository.findById(user.getId());

        if (u == null) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no existe");
            return ResponseEntity.ok(m);
        }

        System.out.println(user.getId());
        Optional<Empresa> pfind = empresaRepository.findByIdPersona(u);

        if (pfind.isPresent()) {

            MensajeJson m = new MensajeJson();
            m.setMsg("ya es empresa");
            return ResponseEntity.ok(m);

        }

        Rol r = rolRepository.findById(4);
        System.out.println(user.getId());

        u.setIdRol(r);

        usuarioRepository.save(u);

        Empresa p = new Empresa();

        p.setIdPersona(u);
        p.setEstado(true);
        p.setNombre(user.getNombre());

        p = empresaRepository.save(p);

        EmpresaJson ujj = empresaToEmpresaJson(p);

        return ResponseEntity.ok(ujj);

    }

    @PostMapping("/cambiousuario")
    public ResponseEntity cambioUsuario(@RequestBody EmpresaJson pro) {

        Empresa p = new Empresa();
        p.setId(pro.getId());

        Optional<Empresa> ps = empresaRepository.findById(p.getId());

        if (ps.isEmpty()) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no existe");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

        Empresa em = ps.get();

        Usuario u = em.getIdPersona();

        Rol r = rolRepository.findById(2);

        u.setIdRol(r);

        try {
            empresaRepository.delete(em);
            u = usuarioRepository.save(u);

            UsuarioJson ujj = UsuarioToUsuarioJson(u);

            return ResponseEntity.ok(ujj);
        } catch (Exception e) {
            MensajeJson mssg = new MensajeJson();
            mssg.setMsg("no");
            return new ResponseEntity(mssg, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cambiousuarioconusuario")
    public ResponseEntity cambioUsuarioWithUsuario(@RequestBody UsuarioJson pro) {

        Usuario u = new Usuario();
        u.setId(pro.getId());

        Optional<Empresa> op = empresaRepository.findByIdPersona(u);

        MensajeJson msg = new MensajeJson();
        if (op.isEmpty()) {
            msg.setMsg("usuario no existe");
            return ResponseEntity.ok(msg);
        }

        Empresa ps = op.get();

        u = ps.getIdPersona();

        Rol r = rolRepository.findById(2);

        u.setIdRol(r);

        try {
            empresaRepository.delete(ps);
            u = usuarioRepository.save(u);

            UsuarioJson ujj = UsuarioToUsuarioJson(u);

            return ResponseEntity.ok(ujj);
        } catch (Exception e) {
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/obtenerporusuario")
    public ResponseEntity loadByUser(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);

        Optional<Empresa> op = empresaRepository.findByIdPersona(u);

        if (op.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("vacio");
        }

        EmpresaJson pj = empresaToEmpresaJson(op.get());

        return ResponseEntity.ok(pj);

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

    public EmpresaJson empresaToEmpresaJson(Empresa e) {
        EmpresaJson ej = new EmpresaJson();

        ej.setId(e.getId());
        ej.setIdPersona(e.getIdPersona().getId());
        ej.setNombre(e.getNombre());
        ej.setEstado(e.getEstado());

        return ej;
    }

    public TipoContratoJson TipoContratoToTipoContratoJson(TipoContrato tc) {

        TipoContratoJson tcj = new TipoContratoJson();

        tcj.setId(tc.getId());
        tcj.setNombre(tc.getNombre());
        tcj.setDescripcion(tc.getDescripcion());

        return tcj;
    }

    private UsuarioJson UsuarioToUsuarioJson(Usuario u) {
        UsuarioJson uj = new UsuarioJson();

        uj.setId(u.getId());
        uj.setNombre(u.getNombre());
        uj.setApellido(u.getApellido());
        uj.setRol(u.getIdRol().getId());
        uj.setEmail(u.getEmail());
        uj.setDocumento(u.getDocumento());
        uj.setContraseña(u.getContraseña());
        uj.setTelefono(u.getTelefono());
        uj.setFechaNacimiento(u.getFechaNacimiento());

        return uj;
    }

}
