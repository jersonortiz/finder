/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ufps.app.finder.repository;

import com.ufps.app.finder.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Usuario findById(Integer id);

    Optional<Usuario> findByEmail(String email);

    void deleteById(Integer id);
}
