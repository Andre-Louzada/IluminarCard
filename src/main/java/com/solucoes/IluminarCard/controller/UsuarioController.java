package com.solucoes.IluminarCard.controller;

import com.solucoes.IluminarCard.model.Usuario;
import com.solucoes.IluminarCard.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController // avisa ao spring que esta classe vai funcionar como porta da API
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios")//Define o endereco princial, as requisicoes sao direcionadas
//para os métodos dessa classe
public class UsuarioController {
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint 1: O caixa escaneia o QR Code e o sistema busca os dados do usuário
    @GetMapping("/{id}") // consulta por id
    public Usuario consultarUsuario(@PathVariable String id) {
        try{
            return usuarioService.buscarUsuarioPorId(id); // Retorna os dados do usuário em formato JSON
        }
        catch(RuntimeException ex){
            return null;
        }
    }

    // Endpoint 2: O caixa clica em "Depositar"
    
    //@PathVariable -> extrai algo da url e joga para variavel java
    //exemplo, no @GetMapping("/{id}"), se a URL acessada for .../user_001, o Spring pega o texto "user_001" e injeta na sua variável Java.
    //@RequestParam -> extrai informacoes passadas como parametros adicionais
    // Exemplo: se a requisição de depósito for .../depositar?valor=50.0, o Spring mapeia o 50.0 para a sua variável valor.
    @PostMapping("/{id}/depositar")
    public String depositar(@PathVariable String id, @RequestParam double valor) {
        try{
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            
                if(usuarioService.efetuarDeposito(id, valor)) {
                    return "Sucesso! Novo saldo de " + usuario.getNome() + ": R$ " + usuario.getSaldo();
                }else{
                    return "Erro: Valor de depósito inválido.";
                }
        }catch(RuntimeException ex){
            return "Erro: Usuário não encontrado no evento.";
        }
    }

    // Endpoint 3: O caixa clica em "Cobrar/Retirar"
    @PostMapping("/{id}/retirar")
    public String retirar(@PathVariable String id, @RequestParam double valor) {
        try{
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            
                if(usuarioService.efetuarRetirada(id, valor)) {
                    return "Compra aprovada! Novo saldo de " + usuario.getNome() + ": R$ " + usuario.getSaldo();
                }else{
                    return "Erro: Saldo insuficiente para realizar esta compra.";
                }
        }catch(RuntimeException ex){
            return "Erro: Usuário não encontrado no evento.";
        }
    }
}
