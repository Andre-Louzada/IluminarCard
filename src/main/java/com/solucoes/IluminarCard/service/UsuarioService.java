package com.solucoes.IluminarCard.service;

import com.solucoes.IluminarCard.model.Usuario;
import com.solucoes.IluminarCard.repository.IUsuarioRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final IUsuarioRepository repository; 

    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository; 
        registrarLog(">>> SISTEMA INICIADO: Conectado ao banco de dados com sucesso. <<<");   
    }
    
    public Usuario buscarUsuarioPorId(String id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao existe no banco de dados"));
    }
    
    public boolean efetuarDeposito(String id, double valor){
        Usuario usuario = buscarUsuarioPorId(id);
        boolean sucesso = usuario.depositar(valor);
        
        if(sucesso){
            repository.save(usuario);
            registrarLog("✅ [DEPOSITO APROVADO] R$ " + valor + " para: " + usuario.getNome() + " | Novo Saldo: R$ " + usuario.getSaldo());
            System.out.println("[ " + LocalDateTime.now()+ " ]"+"✅ [DEPOSITO APROVADO] R$ " + valor + " para o usuário: " + usuario.getNome() + " | Novo Saldo: R$ " + usuario.getSaldo());
        }else{
            registrarLog("❌ [DEPOSITO RECUSADO] Tentativa de R$ " + valor + " para: " + usuario.getNome());
            System.out.println("[ " + LocalDateTime.now()+ " ]"+"❌ [DEPOSITO RECUSADO] Tentativa de R$ " + valor + " para o usuário: " + usuario.getNome());
        }
        return sucesso;
    }
    
    public boolean efetuarRetirada(String id, double valor){
        Usuario usuario = buscarUsuarioPorId(id);
        boolean sucesso = usuario.retirar(valor);
        
        if(sucesso){
            repository.save(usuario);
            registrarLog("✅ [COBRANCA APROVADA] R$ " + valor + " de: " + usuario.getNome() + " | Novo Saldo: R$ " + usuario.getSaldo());
            System.out.println("[ " + LocalDateTime.now()+ " ]"+"✅ [COBRANCA APROVADA] R$ " + valor + " cobrado do usuário: " + usuario.getNome() + " | Novo Saldo: R$ " + usuario.getSaldo());
        }else{
            registrarLog("❌ [COBRANCA RECUSADA] Saldo insuficiente! Cobrança: R$ " + valor + " de: " + usuario.getNome() + " (Saldo: R$ " + usuario.getSaldo() + ")");
            System.out.println("[ " + LocalDateTime.now()+ " ]"+"❌ [COBRANCA RECUSADA] Saldo insuficiente! Tentativa de cobrar R$ " + valor + " do usuário: " + usuario.getNome() + " (Saldo atual: R$ " + usuario.getSaldo() + ")");
        }
        return sucesso;
    }
    private void registrarLog(String mensagem) {
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String linhaLog = "[" + dataHora + "] " + mensagem;

        System.out.println(linhaLog);

        try (FileWriter fw = new FileWriter("log_operacoes_iluminarcard.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(linhaLog);
        } catch (IOException e) {
            System.out.println("❌ ERRO GRAVE: Não foi possível salvar o log no arquivo TXT!");
        }
    }
}

