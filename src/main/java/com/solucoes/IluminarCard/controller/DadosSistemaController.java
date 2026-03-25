package com.solucoes.IluminarCard.controller;

import com.solucoes.IluminarCard.service.DadosSistemaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sistema")
public class DadosSistemaController {
    private DadosSistemaService service;
    public DadosSistemaController(DadosSistemaService service){
        this.service = service;
    }
    @PostMapping("/login")
    public String verificarSenha(@RequestParam String senhaDigitada) {
        try{
            boolean resposta = service.verificarSenha(senhaDigitada);
                if(resposta) {
                    return "A senha foi informada corretamente, acesso liberado!";
                }else{
                    return "Senha incorreta, Tente novamente.";
                }
        }catch(RuntimeException ex){
            return("Erro: Não foi possível verificar a senha no banco de dados.") ;
        }
    }
}
