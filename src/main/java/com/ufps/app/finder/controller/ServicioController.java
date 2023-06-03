/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.ProfesionalJson;
import com.ufps.app.finder.dto.ServicioJson;
import com.ufps.app.finder.dto.ServicioJsonSimpl;
import com.ufps.app.finder.dto.UsuarioJson;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Servicio;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.ProfesionalRepository;
import com.ufps.app.finder.repository.ServicioRepository;
import com.ufps.app.finder.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
    estado =1  : pendiente , el cliente lo solicita ,pero no se a aceptado
    estado= 2 : fue aceptado 
    estado = 3: rechazada
    
    estado= 4: terminado pero sin reseñar
    
    estado = 5:  reseñado
    
    estado = 6 cancelado
    
     */
    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Servicio> listaservicio = (ArrayList<Servicio>) servicioRepository.findAll();

        ArrayList<ServicioJson> retorno = new ArrayList<ServicioJson>();

        for (Servicio x : listaservicio) {

            ServicioJson svj = ServicioToServicioJson(x);

            retorno.add(svj);

        }

        return ResponseEntity.ok(retorno);

    }

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody ServicioJsonSimpl serv) {

        Usuario u = usuarioRepository.findById(serv.getIdCliente());

        Profesional p = profesionalRepository.findById(serv.getIdProfesional());

        Servicio s = new Servicio();

        s.setEstado(1);

        s.setIdCliente(u);
        s.setIdProfesional(p);

        servicioRepository.save(s);

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody ServicioJsonSimpl serv) {

        Servicio s = servicioRepository.findById(serv.getId());

        s.setEstado(serv.getEstado());

        s.setCosto(serv.getCosto());

        Servicio ss = servicioRepository.save(s);

        ServicioJson resp = ServicioToServicioJson(ss);

        return ResponseEntity.ok(resp);

    }

    @PostMapping("/aceptar")
    public ResponseEntity aceptar(@RequestBody ServicioJsonSimpl serv) {

        Servicio s = servicioRepository.findById(serv.getId());

        s.setEstado(2);

        s.setCosto(serv.getCosto());

        Servicio ss = servicioRepository.save(s);

        ServicioJson resp = ServicioToServicioJson(ss);

        return ResponseEntity.ok(resp);

    }

    @PostMapping("/rechazar")
    public ResponseEntity rechazar(@RequestBody ServicioJsonSimpl serv) {

        Servicio s = servicioRepository.findById(serv.getId());

        s.setEstado(3);

        servicioRepository.save(s);

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/terminar")
    public ResponseEntity terminar(@RequestBody ServicioJsonSimpl serv) {

        Servicio s = servicioRepository.findById(serv.getId());

        s.setEstado(4);

        Servicio ss = servicioRepository.save(s);

        ServicioJson resp = ServicioToServicioJson(ss);

        return ResponseEntity.ok(resp);

    }

    @PostMapping("/reseñar")
    public ResponseEntity reseñar(@RequestBody ServicioJsonSimpl serv) {

        Servicio s = servicioRepository.findById(serv.getId());

        s.setEstado(5);

        s.setReseña(serv.getReseña());

        Servicio ss = servicioRepository.save(s);

        ServicioJson resp = ServicioToServicioJson(ss);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {

        Servicio s = servicioRepository.findById(id);

        if (s == null) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no existe");

            return ResponseEntity.ok(m);
        }

        ServicioJson sj = ServicioToServicioJson(s);

        return ResponseEntity.ok(sj);
    }

    @GetMapping("/listusuario")
    public ResponseEntity listUsuario(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);

        ArrayList<Servicio> listaservicio = servicioRepository.findByIdCliente(u);

        ArrayList<ServicioJson> retorno = new ArrayList<ServicioJson>();

        for (Servicio x : listaservicio) {

            ServicioJson svj = ServicioToServicioJson(x);

            retorno.add(svj);

        }

        return ResponseEntity.ok(retorno);

    }

    @GetMapping("/listprofesional")
    public ResponseEntity listProfesional(@RequestParam("id") int id) {

        Profesional p = new Profesional();
        p.setId(id);

        ArrayList<Servicio> listaservicio = servicioRepository.findByIdProfesional(p);

        ArrayList<ServicioJson> retorno = new ArrayList<ServicioJson>();

        for (Servicio x : listaservicio) {

            ServicioJson svj = ServicioToServicioJson(x);

            retorno.add(svj);

        }

        return ResponseEntity.ok(retorno);

    }

    
    @GetMapping("/listprofesionalusuario")
    public ResponseEntity listProfesionalUsuario(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        if (op.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("vacio");
        }

        Profesional p = op.get();

        ArrayList<Servicio> listaservicio = servicioRepository.findByIdProfesional(p);

        ArrayList<ServicioJson> retorno = new ArrayList<ServicioJson>();

        for (Servicio x : listaservicio) {

            ServicioJson svj = ServicioToServicioJson(x);

            retorno.add(svj);

        }

        return ResponseEntity.ok(retorno);

    }

    private ServicioJson ServicioToServicioJson(Servicio s) {

        ServicioJson sj = new ServicioJson();

        sj.setId(s.getId());
        sj.setEstado(s.getEstado());

        if (s.getCosto() != null) {
            sj.setCosto(s.getCosto());
        }

        if (s.getReseña() != null) {
            sj.setReseña(s.getReseña());
        }

        sj.setIdCliente(UsuarioToUsuarioJson(s.getIdCliente()));
        sj.setIdProfesional(ProfesionalToProfesionalJson(s.getIdProfesional()));

        return sj;
    }

    private ProfesionalJson ProfesionalToProfesionalJson(Profesional ps) {

        ProfesionalJson p = new ProfesionalJson();
        p.setId(ps.getId());

        p.setEstado(ps.getEstado());
        p.setCiudad(ps.getCiudad());
        UsuarioJson uj = UsuarioToUsuarioJson(ps.getIdPersona());
        p.setIdPersona(uj);

        return p;
    }

    private UsuarioJson UsuarioToUsuarioJson(Usuario u) {
        UsuarioJson uj = new UsuarioJson();

        uj.setId(u.getId());
        uj.setNombre(u.getNombre());
        uj.setApellido(u.getApellido());
        uj.setEdad(u.getEdad());
        uj.setRol(u.getIdRol().getId());
        uj.setEmail(u.getEmail());
        uj.setDocumento(u.getDocumento());
        uj.setContraseña(u.getContraseña());

        return uj;
    }

}
