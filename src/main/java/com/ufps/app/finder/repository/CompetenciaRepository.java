/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.Competencia;
import com.ufps.app.finder.entity.Profesional;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jerson
 */
@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {

    ArrayList<Competencia> findByIdProfesional(Profesional id);

    Optional<Competencia> findById(Integer id);

}
