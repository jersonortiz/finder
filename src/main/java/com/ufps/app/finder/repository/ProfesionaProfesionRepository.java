/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.ProfesionaProfesion;
import com.ufps.app.finder.entity.Profesional;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 
 */
@Repository
public interface ProfesionaProfesionRepository extends JpaRepository<ProfesionaProfesion, Long> {
    
    ArrayList<ProfesionaProfesion> findByIdProfesional(Profesional id);
    
    ArrayList<ProfesionaProfesion> findByIdProfesion(Profesional id);

}
