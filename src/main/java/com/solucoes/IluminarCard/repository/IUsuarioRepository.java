/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.solucoes.IluminarCard.repository;

import com.solucoes.IluminarCard.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author André
 */
@Repository
public interface IUsuarioRepository extends JpaRepository <Usuario, String>{
    
}
