/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ufps.app.finder.controller;

import com.ufps.app.finder.dto.CuentaBancariaJson;
import com.ufps.app.finder.dto.MensajeJson;
import com.ufps.app.finder.entity.CuentaBancaria;
import com.ufps.app.finder.repository.CuentaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jerson
 */
@RestController
@CrossOrigin
@RequestMapping("/banco")
public class BancoController {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @PostMapping("/actualizar")
    public ResponseEntity suspension(@RequestBody CuentaBancariaJson cbj) {

        CuentaBancaria cb = cuentaBancariaRepository.findById(1);

        cb.setContenido(cbj.getContenido());
        cb.setNombre(cbj.getNombre());

        cuentaBancariaRepository.save(cb);

        MensajeJson msg = new MensajeJson();
        msg.setMsg("ok");

        return ResponseEntity.ok(msg);

    }

    @GetMapping("/consulta")
    public ResponseEntity list() {

        CuentaBancaria cb = cuentaBancariaRepository.findById(1);

        CuentaBancariaJson cbj = new CuentaBancariaJson();

        cbj.setId(cb.getId());
        cbj.setNombre(cb.getNombre());
        cbj.setContenido(cb.getContenido());

        return ResponseEntity.ok(cbj);

    }

}
