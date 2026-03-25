/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.solucoes.IluminarCard.service;

import com.solucoes.IluminarCard.model.DadosSistema;
import com.solucoes.IluminarCard.repository.IDadosSistemaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author André
 */
@Service
public class DadosSistemaService {
    public IDadosSistemaRepository repositorio;
    
    public DadosSistemaService(IDadosSistemaRepository repositorio){
        this.repositorio = repositorio;
    }
    
    public String buscarSenhaNoBanco(){
        int id = 1;
        DadosSistema dados = (repositorio.findById(id).orElseThrow(() -> new RuntimeException("Senha nao existe no banco de dados")));
        return dados.getSenha();
    }
    
    public boolean verificarSenha(String senhaDigitada){
        String senha = buscarSenhaNoBanco();
        return(senha.equals(senhaDigitada));
    }
}   
