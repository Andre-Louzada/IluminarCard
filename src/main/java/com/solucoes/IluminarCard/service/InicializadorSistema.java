package com.solucoes.IluminarCard.service;

import com.solucoes.IluminarCard.model.DadosSistema;
import com.solucoes.IluminarCard.repository.IDadosSistemaRepository;
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
        if(!repository.findById(1).isEmpty()){
            DadosSistema dadosSalvos = repository.findById(1).get();
            String senhaDoBanco = dadosSalvos.getSenha();
            System.out.println("✅ Sistema seguro. Aguardando acessos na web com a senha: " + senhaDoBanco);
        }else {
            System.out.println("Insira uma senha para acessos na web.");
            DadosSistema dados = new DadosSistema();
            Scanner scanner = new Scanner(System.in);
            String senhaDigitada = scanner.nextLine();
            dados.setSenha(senhaDigitada); 
            repository.save(dados);
            DadosSistema dadosSalvos = repository.findById(1).get();
            String senhaDoBanco = dadosSalvos.getSenha();
            System.out.println("A senha para acessar o sistema é:" + senhaDigitada + "||" + senhaDoBanco);
        }
    }
}
