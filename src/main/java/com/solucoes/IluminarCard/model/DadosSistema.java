/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solucoes.IluminarCard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 *
 * @author André
 */
@Entity
public class DadosSistema {
    @Id
    private Integer id = 1;
    private String senha;
    
    public DadosSistema(){}
    
    public String getSenha(){
        return this.senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
}
