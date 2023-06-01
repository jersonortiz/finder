/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Servicio;
import com.ufps.app.finder.entity.Usuario;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author
 */
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Servicio findById(Integer id);

    ArrayList<Servicio> findByIdCliente(Usuario id);

    ArrayList<Servicio> findByIdProfesional(Profesional id);

}
