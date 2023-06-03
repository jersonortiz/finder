/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.AdminConsultaJson;
import com.ufps.app.finder.dto.ConsultaProfesionalJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.dto.ProfesionJson;
import com.ufps.app.finder.dto.ProfesionalJson;
import com.ufps.app.finder.dto.ProfesionalListJson;
import com.ufps.app.finder.dto.PublicacionJson;
import com.ufps.app.finder.dto.SectorJson;
import com.ufps.app.finder.dto.UsuarioJson;
import com.ufps.app.finder.entity.Profesion;
import com.ufps.app.finder.entity.ProfesionaProfesion;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Publicacion;
import com.ufps.app.finder.entity.Rol;
import com.ufps.app.finder.entity.Sector;
import com.ufps.app.finder.entity.Usuario;
import com.ufps.app.finder.repository.ProfesionaProfesionRepository;
import com.ufps.app.finder.repository.ProfesionalRepository;
import com.ufps.app.finder.repository.PublicacionRepository;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author
 */
@RestController
@CrossOrigin
@RequestMapping("/profesional")
public class ProfesionalController {

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfesionaProfesionRepository profesionaProfesionRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/list")
    public ResponseEntity list() {

        ArrayList<Profesional> ps = (ArrayList<Profesional>) profesionalRepository.findAll();
        ArrayList<ProfesionalListJson> lista = new ArrayList<ProfesionalListJson>();

        for (Profesional x : ps) {

            if (!x.getEstado()) {
                continue;
            }

            Usuario xper = x.getIdPersona();

            ProfesionalListJson p = ProfesionalToProfesionalListJson(x, xper);

            ArrayList< ProfesionJson> pj = new ArrayList<ProfesionJson>();

            System.out.println(p.toString());

            ArrayList<ProfesionaProfesion> relas = profesionaProfesionRepository.findByIdProfesional(x);

            if (relas != null) {
                for (ProfesionaProfesion ppf : relas) {

                    Profesion pro = ppf.getIdProfesion();

                    ProfesionJson pjs = ProfesionToProfesionJson(pro);

                    Sector sec = pro.getIdSector();

                    SectorJson secj = SectorToSectorJson(sec);

                    pjs.setSector(secj);

                    pj.add(pjs);
                }
                p.setProfesiones(pj);

                lista.add(p);
            }
        }
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/consulta")
    public ResponseEntity consulta(@RequestParam("id") int id) {

        Profesional ps = profesionalRepository.findById(id);

        if (ps == null) {
            MensajeJson m = new MensajeJson();
            m.setMsg("profesional no existe");
            return ResponseEntity.ok(m);

        }

        Usuario xper = ps.getIdPersona();

        ConsultaProfesionalJson consulta = new ConsultaProfesionalJson();

        ProfesionalListJson p = ProfesionalToProfesionalListJson(ps, xper);

        ArrayList<ProfesionaProfesion> relas = profesionaProfesionRepository.findByIdProfesional(ps);

        if (relas != null) {
            ArrayList< ProfesionJson> pj = new ArrayList<ProfesionJson>();
            for (ProfesionaProfesion ppf : relas) {

                Profesion pro = ppf.getIdProfesion();

                ProfesionJson pjs = ProfesionToProfesionJson(pro);

                Sector sec = pro.getIdSector();

                SectorJson secj = SectorToSectorJson(sec);

                pjs.setSector(secj);

                pj.add(pjs);
            }
            p.setProfesiones(pj);

        }

        consulta.setProfesional(p);

        ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) publicacionRepository.findByIdProfesionalOrderByIdDesc(ps);
        ArrayList<PublicacionJson> lista = new ArrayList<PublicacionJson>();
        for (Publicacion x : publicaciones) {

            PublicacionJson publicacionretorno = PublicaciontoPublicacionJson(x, id);
            lista.add(publicacionretorno);
        }
        consulta.setPublicaciones(lista);

        return ResponseEntity.ok(consulta);

    }

    @GetMapping("/gestion")
    public ResponseEntity gestion() {

        ArrayList<Profesional> ps = (ArrayList<Profesional>) profesionalRepository.findAll();
        ArrayList<AdminConsultaJson> lista = new ArrayList<AdminConsultaJson>();

        for (Profesional x : ps) {

            AdminConsultaJson acj = new AdminConsultaJson();

            ProfesionalJson pj = ProfesionalToProfesionalJson(x);

            acj.setProfesional(pj);

            ArrayList<ProfesionJson> proj = new ArrayList<ProfesionJson>();

            ArrayList<ProfesionaProfesion> relas = profesionaProfesionRepository.findByIdProfesional(x);

            if (relas != null) {
                for (ProfesionaProfesion ppf : relas) {

                    Profesion pro = ppf.getIdProfesion();

                    ProfesionJson pjs = ProfesionToProfesionJson(pro);

                    Sector sec = pro.getIdSector();

                    SectorJson secj = SectorToSectorJson(sec);

                    pjs.setSector(secj);

                    proj.add(pjs);
                }
                acj.setProfesiones(proj);

            }

            ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) publicacionRepository.findByIdProfesionalOrderByIdDesc(x);
            ArrayList<PublicacionJson> listapublicaciones = new ArrayList<PublicacionJson>();
            for (Publicacion publicacion : publicaciones) {

                PublicacionJson publicacionretorno = PublicaciontoPublicacionJson(publicacion, x.getId());
                listapublicaciones.add(publicacionretorno);
            }
            acj.setPublicaciones(listapublicaciones);

            lista.add(acj);

        }
        return ResponseEntity.ok(lista);

    }

    @PostMapping("/suspender")
    public ResponseEntity suspension(@RequestBody ProfesionalJson pro) {

        Profesional p = new Profesional();
        p.setId(pro.getId());

        Profesional ps = profesionalRepository.findById(p.getId());

        if (ps == null) {
            MensajeJson msg = new MensajeJson();
            msg.setMsg("no existe");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);

        }

        if (ps.getEstado()) {
            ps.setEstado(false);
        } else {
            ps.setEstado(true);
        }

        profesionalRepository.save(ps);

        MensajeJson msg = new MensajeJson();
        msg.setMsg("ok");

        return ResponseEntity.ok(msg);

    }

    @PostMapping("/eliminar")
    public ResponseEntity borrar(@RequestBody ProfesionalJson pro) {

        Profesional p = new Profesional();
        p.setId(pro.getId());

        Profesional ps = profesionalRepository.findById(p.getId());

        Usuario usr = ps.getIdPersona();

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

    @PostMapping("/cambiousuario")
    public ResponseEntity cambioUsuario(@RequestBody ProfesionalJson pro) {

        Profesional p = new Profesional();
        p.setId(pro.getId());

        Profesional ps = profesionalRepository.findById(p.getId());

        MensajeJson msg = new MensajeJson();
        if (ps == null) {
            msg.setMsg("profesional no existe");
            return ResponseEntity.ok(msg);
        }

        Usuario u = ps.getIdPersona();

        Rol r = rolRepository.findById(2);

        u.setIdRol(r);

        try {
            profesionalRepository.delete(ps);
            u = usuarioRepository.save(u);
            
            UsuarioJson ujj = UsuarioToUsuarioJson(u);

            return ResponseEntity.ok(ujj);
        } catch (Exception e) {
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cambiousuarioconusuario")
    public ResponseEntity cambioUsuarioWithUsuario(@RequestBody UsuarioJson pro) {

        Usuario u = new Usuario();
        u.setId(pro.getId());

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        MensajeJson msg = new MensajeJson();
        if (op.isEmpty()) {
            msg.setMsg("usuario no existe");
            return ResponseEntity.ok(msg);
        }

        Profesional ps = op.get();

        u = ps.getIdPersona();

        Rol r = rolRepository.findById(2);

        u.setIdRol(r);

        try {
            profesionalRepository.delete(ps);
            u = usuarioRepository.save(u);
            
            UsuarioJson ujj = UsuarioToUsuarioJson(u);

            return ResponseEntity.ok(ujj);
        } catch (Exception e) {
            msg.setMsg("no");
            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cambioprofesional")
    public ResponseEntity cambioprofesional(@RequestBody UsuarioJson user) {

        Rol r = rolRepository.findById(3);
        Usuario u = usuarioRepository.findById(user.getId());

        u.setIdRol(r);

        usuarioRepository.save(u);

        Profesional p = new Profesional();

        p.setIdPersona(u);
        p.setEstado(true);
        p.setCiudad("cucuta");

        p = profesionalRepository.save(p);
        
        UsuarioJson ujj = UsuarioToUsuarioJson(u);


        return ResponseEntity.ok(ujj);

    }

    @GetMapping("/obtenerporusuario")
    public ResponseEntity loadByUser(@RequestParam("id") int id) {

        Usuario u = new Usuario();
        u.setId(id);

        Optional<Profesional> op = profesionalRepository.findByIdPersona(u);

        if (op.isEmpty()) {
            MensajeJson m = new MensajeJson();
            m.setMsg("vacio");
        }

        ProfesionalJson pj = ProfesionalToProfesionalJson(op.get());

        return ResponseEntity.ok(pj);

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

    private ProfesionalListJson ProfesionalToProfesionalListJson(Profesional ps, Usuario xper) {

        ProfesionalListJson p = new ProfesionalListJson();
        p.setId(ps.getId());
        p.setNombre(xper.getNombre());
        p.setApellido(xper.getApellido());
        p.setEdad(xper.getEdad());
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

    private ProfesionJson ProfesionToProfesionJson(Profesion pro) {

        ProfesionJson pjs = new ProfesionJson();

        pjs.setId(pro.getId());
        pjs.setProfesion(pro.getProfesion());

        return pjs;

    }

    private SectorJson SectorToSectorJson(Sector sec) {
        SectorJson secj = new SectorJson();

        secj.setId(sec.getId());
        secj.setNombre(sec.getNombre());

        return secj;

    }

    /*example
    @RequestMapping(value="/data/{itemid}", method = RequestMethod.GET)
    public @ResponseBody
    item getitem(@PathVariable("itemid") String itemid) {   
    item i = itemDao.findOne(itemid);              
    String itemname = i.getItemname();
    String price = i.getPrice();
    return i;
}
    
    @RequestMapping(value="user", method = RequestMethod.GET)
    public @ResponseBody Item getItem(@RequestParam("data") String itemid){

    Item i = itemDao.findOne(itemid);              
    String itemName = i.getItemName();
    String price = i.getPrice();
    return i;
}
    
     */
}
