/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.Empresa;
import com.ufps.app.finder.entity.OfertaTrabajo;
import com.ufps.app.finder.entity.Sector;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jerson
 */
@Repository
public interface OfertaTrabajoRepository extends JpaRepository<OfertaTrabajo, Long> {

    Optional<OfertaTrabajo> findByIdEmpresa(Empresa id);

    Optional<OfertaTrabajo> findById(Integer id);

    Optional<OfertaTrabajo> findByIdSector(Sector id);

}
