package com.solucoes.IluminarCard.controller;

import com.solucoes.IluminarCard.model.Usuario;
import com.solucoes.IluminarCard.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // TODO nao recomendado, trocar pela URL que será utilizada
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}") 
    public Usuario consultarUsuario(@PathVariable String id) {
        try{
            return usuarioService.buscarUsuarioPorId(id);
        }
        catch(RuntimeException ex){
            return null;
        }
    }

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
