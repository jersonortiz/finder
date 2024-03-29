/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.LoginJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.UsuarioJson;
import com.ufps.app.finder.entity.Rol;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.ProfesionalRepository;
import com.ufps.app.finder.repository.RolRepository;
import com.ufps.app.finder.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @PostMapping("/registro")
    public ResponseEntity registro(@RequestBody UsuarioJson user) {

        System.out.print(user.toString());

        Optional<Usuario> ou = usuarioRepository.findByEmail(user.getEmail());
        if (ou.isPresent()) {
            MensajeJson mm = new MensajeJson();
            mm.setMsg("usuario ya existe");

            return ResponseEntity.ok(mm);
        }

        Usuario u = new Usuario();

        u.setNombre(user.getNombre());
        u.setApellido(user.getApellido());
        u.setDocumento(user.getDocumento());
        u.setEmail(user.getEmail());
        u.setContraseña(user.getContraseña());
        u.setTelefono(user.getTelefono());

        u.setFechaNacimiento(user.getFechaNacimiento());
        System.out.println("edad: " + user.getFechaNacimiento());
        Rol r = new Rol();
        r.setId(user.getRol());
        u.setIdRol(r);

        /*
        if(user.getRol()==2){
            Profesional p = new Profesional();
            p.setIdPersona();
            this.profesionalRepository.save(p);
        }
         */
        Usuario ss = usuarioRepository.save(u);
        user.setId(ss.getId());
        System.out.println(ss.getId());

        return ResponseEntity.ok(user);

    }

    @PostMapping("/edit")
    public ResponseEntity edit(@RequestBody UsuarioJson user) {

        Usuario u = usuarioRepository.findById(user.getId());
        if (u == null) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }

        //u.setId(user.getId());
        u.setNombre(user.getNombre());
        u.setApellido(user.getApellido());
        u.setDocumento(user.getDocumento());
        u.setEmail(user.getEmail());

        Optional<Usuario> oematest = usuarioRepository.findByEmail(user.getEmail());

        if (oematest.isPresent()) {
            Usuario ematest = oematest.get();
            if (!Objects.equals(u.getId(), ematest.getId())) {
                MensajeJson msg = new MensajeJson();
                msg.setMsg("email ya en uso");
                return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
            }
        }

        u.setTelefono(user.getTelefono());
        u.setFechaNacimiento(user.getFechaNacimiento());

        if (u.getIdRol().getId() != user.getRol()) {

            Rol r = rolRepository.findById(user.getRol());

            u.setIdRol(r);
        }

        usuarioRepository.save(u);

        return ResponseEntity.ok(user);

    }

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Usuario> usuarios = (ArrayList<Usuario>) usuarioRepository.findAll();
        ArrayList<UsuarioJson> lista = new ArrayList<UsuarioJson>();
        for (Usuario x : usuarios) {
            UsuarioJson userj = UsuarioToUsuarioJson(x);

            lista.add(userj);
        }
        return ResponseEntity.ok(lista);

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginJson user) {

        Optional<Usuario> ox = usuarioRepository.findByEmail(user.getEmail());
        MensajeJson m = new MensajeJson();

        if (ox.isEmpty()) {

            m.setMsg("No existe el usuario");
            return ResponseEntity.ok(m);
        }

        Usuario x = ox.get();

        if (!x.getContraseña().equals(user.getContraseña())) {

            m.setMsg("Contraseña incorrecta");
            return ResponseEntity.ok(m);
        }

        UsuarioJson userj = UsuarioToUsuarioJson(x);

        return new ResponseEntity(userj, HttpStatus.ACCEPTED);

    }

    @PostMapping("/eliminar")
    public ResponseEntity borrar(@RequestBody UsuarioJson user) {

        Usuario usr = new Usuario();
        usr.setId(user.getId());
        System.out.println(user.toString());

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

    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {
        Usuario x = usuarioRepository.findById(id);

        if (x == null) {
            MensajeJson m = new MensajeJson();
            m.setMsg("no existe el usuario");
            return ResponseEntity.ok(m);

        }

        UsuarioJson userj = UsuarioToUsuarioJson(x);

        return ResponseEntity.ok(userj);
    }

    @GetMapping("/listarol")
    public ResponseEntity consultaRol(@RequestParam("id") int id) {

        Rol r = rolRepository.findById(id);

        ArrayList<Usuario> usuarios = (ArrayList<Usuario>) usuarioRepository.findByIdRol(r);
        ArrayList<UsuarioJson> lista = new ArrayList<UsuarioJson>();
        for (Usuario x : usuarios) {

            UsuarioJson userj = UsuarioToUsuarioJson(x);
            lista.add(userj);

        }
        return ResponseEntity.ok(lista);

    }

    /*
    estado = 2  usuairo
    estado = 3  profesional
    estado = 4  empresa
    estado = 5 pendiente profesional
    estado = 6 pendiente empresa
     */
    @PostMapping("/cancelar")
    public ResponseEntity cancelar(@RequestBody UsuarioJson user) {

        Usuario u = usuarioRepository.findById(user.getId());
        System.out.println(user.getId());

        Rol r = rolRepository.findById(2);
        System.out.println(user.getId());

        u.setIdRol(r);

        usuarioRepository.save(u);

        UsuarioJson ujj = UsuarioToUsuarioJson(u);

        return ResponseEntity.ok(ujj);

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
