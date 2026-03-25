package com.solucoes.IluminarCard.service;

import com.solucoes.IluminarCard.repository.IDadosSistemaRepository;
import com.solucoes.IluminarCard.model.DadosSistema;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializadorSistema implements CommandLineRunner{
    private IDadosSistemaRepository repository;
    
    public InicializadorSistema(IDadosSistemaRepository repository){
        this.repository = repository;
    }
    @Override
    public void run(String... args) throws Exception{
        if(!repository.findById(1).isEmpty())
            System.out.println("✅ Sistema seguro. Aguardando acessos na web.");
        else {
            System.out.println("Insira uma senha para acessos na web.");
            DadosSistema dados = new DadosSistema();
            Scanner scanner = new Scanner(System.in);
            String senhaDigitada = scanner.nextLine();
            dados.setSenha(senhaDigitada); 
            repository.save(dados);
        }
    }
}
