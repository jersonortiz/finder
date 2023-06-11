/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.Profesional;
import com.ufps.app.finder.entity.Titulo;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jerson
 */
@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long> {

    Optional<Titulo> findById(Integer id);

    ArrayList<Titulo> findByIdTipoTitulo(Integer id);

    ArrayList<Titulo> findByIdProfesional(Profesional id);

}
