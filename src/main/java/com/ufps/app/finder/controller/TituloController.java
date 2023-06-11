/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.TipoTituloJson;
import com.ufps.app.finder.dto.TituloJson;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.TipoTitulo;
import com.ufps.app.finder.entity.Titulo;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.ProfesionalRepository;
import com.ufps.app.finder.repository.TipoTituloRepository;
import com.ufps.app.finder.repository.TituloRepository;
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
@RequestMapping("/titulo")
public class TituloController {

    @Autowired
    private TipoTituloRepository tipoTituloRepository;

    @Autowired
    private TituloRepository tituloRepository;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Titulo> lista = (ArrayList<Titulo>) tituloRepository.findAll();

        ArrayList<TituloJson> retorno = new ArrayList<TituloJson>();

        for (Titulo x : lista) {

            TituloJson tj = tituloToTituloJson(x);

            retorno.add(tj);

        }

        return ResponseEntity.ok(retorno);

    }

    @GetMapping("/listprofesional")
    public ResponseEntity listProfesional(@RequestParam("id") int id) {

        Profesional ps = new Profesional();
        ps.setId(id);

        ArrayList<Titulo> lista = (ArrayList<Titulo>) tituloRepository.findByIdProfesional(ps);

        ArrayList<TituloJson> retorno = new ArrayList<TituloJson>();

        for (Titulo x : lista) {

            TituloJson tj = tituloToTituloJson(x);

            retorno.add(tj);

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

        ArrayList<Titulo> lista = (ArrayList<Titulo>) tituloRepository.findByIdProfesional(ps);

        ArrayList<TituloJson> retorno = new ArrayList<TituloJson>();

        for (Titulo x : lista) {

            TituloJson tj = tituloToTituloJson(x);

            retorno.add(tj);

        }

        return ResponseEntity.ok(retorno);

    }

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody TituloJson serv) {

        Profesional p = new Profesional();
        p.setId(serv.getIdProfesional());

        Profesional ps = profesionalRepository.findById(p.getId());

        if (ps == null) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return ResponseEntity.ok(msg);
        }

        Titulo t = new Titulo();

        t.setTitulo(serv.getTitulo());
        t.setInstitucion(serv.getInstitucion());
        t.setCertificado(serv.getCertificado());
        t.setIdProfesional(ps);
        Optional<TipoTitulo> tto = tipoTituloRepository.findById(serv.getIdTipoTitulo());

        if (tto.isEmpty()) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("error");
            return ResponseEntity.ok(msg);
        }
        t.setIdTipoTitulo(tto.get());

        t = tituloRepository.save(t);
        serv.setId(t.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/registrouser")
    public ResponseEntity registrousuario(@RequestBody TituloJson serv) {

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

        Titulo t = new Titulo();

        t.setTitulo(serv.getTitulo());
        t.setInstitucion(serv.getInstitucion());
        t.setCertificado(serv.getCertificado());
        t.setIdProfesional(ps);
        Optional<TipoTitulo> tto = tipoTituloRepository.findById(serv.getIdTipoTitulo());

        if (tto.isEmpty()) {

            msg.setMsg("error");
            return ResponseEntity.ok(msg);
        }
        t.setIdTipoTitulo(tto.get());

        t = tituloRepository.save(t);
        serv.setId(t.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/editar")
    public ResponseEntity editar(@RequestBody TituloJson serv) {

        Titulo t = new Titulo();
        t.setId(serv.getId());

        Optional<Titulo> to = tituloRepository.findById(t.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        t = to.get();

        t.setTitulo(serv.getTitulo());
        t.setInstitucion(serv.getInstitucion());
        t.setCertificado(serv.getCertificado());

        Optional<TipoTitulo> tto = tipoTituloRepository.findById(serv.getIdTipoTitulo());

        if (tto.isEmpty()) {

            msg.setMsg("error");
            return ResponseEntity.ok(msg);
        }
        t.setIdTipoTitulo(tto.get());

        t = tituloRepository.save(t);
        serv.setId(t.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/eliminar")
    public ResponseEntity eliminar(@RequestBody TituloJson serv) {

        Titulo t = new Titulo();
        t.setId(serv.getId());

        Optional<Titulo> to = tituloRepository.findById(t.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        t = to.get();

        try {
            tituloRepository.delete(t);

            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {

            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/listtipo")
    public ResponseEntity listTipo() {

        ArrayList<TipoTitulo> lista = (ArrayList<TipoTitulo>) tipoTituloRepository.findAll();

        ArrayList<TipoTituloJson> retorno = new ArrayList<TipoTituloJson>();

        for (TipoTitulo x : lista) {

            TipoTituloJson ttj = tipoTituloToTipoTituloJson(x);

            retorno.add(ttj);

        }

        return ResponseEntity.ok(retorno);

    }

    @PostMapping("/registrotipo")
    public ResponseEntity registroTipo(@RequestBody TipoTituloJson serv) {

        TipoTitulo t = new TipoTitulo();

        t.setTipo(serv.getTipo());

        t = tipoTituloRepository.save(t);
        serv.setId(t.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/editartipo")
    public ResponseEntity editarTipo(@RequestBody TipoTituloJson serv) {

        TipoTitulo t = new TipoTitulo();
        t.setId(serv.getId());

        Optional<TipoTitulo> to = tipoTituloRepository.findById(t.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        t = to.get();

        t.setTipo(serv.getTipo());

        t = tipoTituloRepository.save(t);
        serv.setId(t.getId());

        return ResponseEntity.ok(serv);
    }

    @PostMapping("/eliminartipo")
    public ResponseEntity eliminarTipo(@RequestBody TipoTituloJson serv) {

        TipoTitulo t = new TipoTitulo();
        t.setId(serv.getId());

        Optional<TipoTitulo> to = tipoTituloRepository.findById(t.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        t = to.get();

        try {
            tipoTituloRepository.delete(t);

            msg.setMsg("ok");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {

            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/consultatipo")
    public ResponseEntity consultaTipo(@RequestParam("id") int id) {

        TipoTitulo t = new TipoTitulo();
        t.setId(id);

        Optional<TipoTitulo> to = tipoTituloRepository.findById(t.getId());

        MensajeJson msg = new MensajeJson();
        if (to.isEmpty()) {
            msg.setMsg("no existe");
            return ResponseEntity.ok(msg);
        }
        t = to.get();

        TipoTituloJson cj = tipoTituloToTipoTituloJson(t);

        return ResponseEntity.ok(cj);

    }

    private TituloJson tituloToTituloJson(Titulo t) {
        TituloJson tj = new TituloJson();
        tj.setId(t.getId());
        tj.setTitulo(t.getTitulo());
        tj.setInstitucion(t.getInstitucion());
        tj.setCertificado(t.getCertificado());
        tj.setIdProfesional(t.getIdProfesional().getId());

        tj.setIdTipoTitulo(t.getIdTipoTitulo().getId());

        return tj;

    }

    private TipoTituloJson tipoTituloToTipoTituloJson(TipoTitulo tt) {
        TipoTituloJson ttj = new TipoTituloJson();
        ttj.setId(tt.getId());
        ttj.setTipo(tt.getTipo());

        return ttj;
    }

}
