/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Publicacion;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 
 */
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    //Publicacion findByIdProfesionalOrderByIdDesc(Integer id);
    
    ArrayList<Publicacion> findAllByOrderByIdDesc();
    
    ArrayList<Publicacion> findByIdProfesionalOrderByIdDesc(Profesional id);
    
    Optional<Publicacion> findById(Integer id);
    
    
    
    
    
}
