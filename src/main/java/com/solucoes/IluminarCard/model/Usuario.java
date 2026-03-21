package com.solucoes.IluminarCard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Usuario{
    @Id
    private String id;
    private String nome;
    private double saldo;
    
    public Usuario(){};
    
    public Usuario(String id, String nome) {
        
        this.id = id;
        this.nome = nome;
        this.saldo = 0.0; 
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public double getSaldo() { return saldo; }

    public boolean depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return true;
        }
        return false;
    }

    public boolean retirar(double valor) {
        int numero = Integer.parseInt(id.replaceAll("[^0-9]", "")); 
        if(numero <= 10){
            this.saldo -= valor;
            return true;
        }
        else if (valor > 0 && this.saldo >= valor) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }
}
