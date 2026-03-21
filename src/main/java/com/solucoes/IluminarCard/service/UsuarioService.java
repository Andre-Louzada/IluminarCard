package com.solucoes.IluminarCard.service;

import com.solucoes.IluminarCard.model.Usuario;
import com.solucoes.IluminarCard.repository.IUsuarioRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    // Simulando nosso banco de dados em memória usando um Map (Chave = ID, Valor = Usuario)
    private final IUsuarioRepository repository; 

    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository; 
    }
    
    public Usuario buscarUsuarioPorId(String id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao existe no banco de dados"));
    }
    
    public boolean efetuarDeposito(String id, double valor){
        Usuario usuario = buscarUsuarioPorId(id);
        boolean sucesso = usuario.depositar(valor);
        
        if(sucesso){
            repository.save(usuario);
            System.out.println("[ " + LocalDateTime.now()+ " ]"+"✅ [DEPOSITO APROVADO] R$ " + valor + " para o usuário: " + usuario.getNome() + " | Novo Saldo: R$ " + usuario.getSaldo());
        }else{
            System.out.println("[ " + LocalDateTime.now()+ " ]"+"❌ [DEPOSITO RECUSADO] Tentativa de R$ " + valor + " para o usuário: " + usuario.getNome());
        }
        return sucesso;
    }
    
    public boolean efetuarRetirada(String id, double valor){
        Usuario usuario = buscarUsuarioPorId(id);
        boolean sucesso = usuario.retirar(valor);
        
        if(sucesso){
            repository.save(usuario);
            System.out.println("[ " + LocalDateTime.now()+ " ]"+"✅ [COBRANCA APROVADA] R$ " + valor + " cobrado do usuário: " + usuario.getNome() + " | Novo Saldo: R$ " + usuario.getSaldo());
        }else{
            System.out.println("[ " + LocalDateTime.now()+ " ]"+"❌ [COBRANCA RECUSADA] Saldo insuficiente! Tentativa de cobrar R$ " + valor + " do usuário: " + usuario.getNome() + " (Saldo atual: R$ " + usuario.getSaldo() + ")");
        }
        return sucesso;
    }
}

