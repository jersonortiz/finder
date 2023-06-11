/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.Habilidades;
import com.ufps.app.finder.entity.Profesion;
import com.ufps.app.finder.entity.Profesional;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jerson
 */
public interface HabilidadRepository extends JpaRepository<Habilidades, Long> {

    ArrayList<Habilidades> findByIdProfesional(Profesional id);

    Optional<Habilidades> findById(Integer id);

}
