/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author
 */
@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    Profesional findById(Integer id);
    
    Optional<Profesional> findByIdPersona(Usuario id);

}
