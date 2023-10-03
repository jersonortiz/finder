/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.OfertaProfesional;
import com.ufps.app.finder.entity.OfertaTrabajo;
import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Servicio;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jerson
 */
@Repository
public interface OfertaProfesionalRepository extends JpaRepository<OfertaProfesional, Long> {

    ArrayList<OfertaProfesional> findByIdProfesional(Profesional id);

    ArrayList<OfertaProfesional> findByIdOfertaTrabajo(OfertaTrabajo id);

    Optional<OfertaProfesional> findByIdProfesionalAndIdOfertaTrabajo(Profesional p, OfertaProfesional of);
}
