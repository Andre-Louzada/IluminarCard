let usuarioAtualId = "";
let html5QrcodeScanner = null;

function onScanSuccess(decodedText, decodedResult) {
    usuarioAtualId = decodedText; 
    html5QrcodeScanner.clear();
    document.getElementById('reader').style.display = 'none';
    buscarUsuarioNoBackend(usuarioAtualId);
}
function buscarUsuarioNoBackend(){
    fetch('/api/usuarios/' + usuarioAtualId)
        .then(resposta => {
            if(!resposta.ok) throw new Error("Usuário não encontrado");
            return resposta.json();
        })
        .then(usuario => {
            // Preenche a tela com os dados vindos do Java
            let numeroDoCliente = parseInt(usuarioAtualId.replace("user_", ""));
            if(numeroDoCliente <10)
                document.getElementById('nomeUsuario').innerText = "Organização: " + usuario.nome;
            else
                document.getElementById('nomeUsuario').innerText = "Iluminado: " + usuario.nome;
            document.getElementById('saldoUsuario').innerText = "Saldo: R$ " + usuario.saldo.toFixed(2);
            
            document.getElementById('verso-container').style.display = 'block';
            document.getElementById('mensagemSistema').innerText = "";
            document.getElementById('mensagemBuscaManual').innerText = "";
        })
        .catch(erro => {
            alert("Erro: QR Code inválido ou usuário não encontrado no sistema.");
            
        });
}


function escanear(){
    html5QrcodeScanner = new Html5QrcodeScanner("reader", { fps: 10, qrbox: {width: 250, height: 250} }, false);
    html5QrcodeScanner.render(onScanSuccess);
    document.getElementById('reader').style.display = 'block';
}

function fazerOperacao(tipo) {
    let valorInput = document.getElementById('valorOperacao').value;

    if (!valorInput || valorInput <= 0) {
        alert("Por favor, digite um valor válido.");
        return;
    }

    let urlApi = `api/usuarios/${usuarioAtualId}/${tipo}?valor=${valorInput}`;

    fetch(urlApi, { method: 'POST' })
    .then(resposta => resposta.text())
    .then(textoResposta => {
        let mensagem = document.getElementById('mensagemSistema');
        mensagem.innerText = textoResposta;
        
        if (textoResposta.includes("Sucesso") || textoResposta.includes("aprovada")) {
            mensagem.style.color = "green";
            let partes = textoResposta.split("R$ ");
            if(partes.length > 1) {
                document.getElementById('saldoUsuario').innerText = "Saldo: R$ " + parseFloat(partes[1]).toFixed(2);
            }
        } else {
            mensagem.style.color = "red"; 
        }
        document.getElementById('valorOperacao').value = "";
        if(tipo == "retirar"){
            document.getElementById('btn-cobrar').style.display = 'none';
        }

    })
    .catch(erro => alert("Erro ao conectar com o Java."));
}

function proximoCliente() {
    document.getElementById('verso-container').style.display = 'none';
    document.getElementById('valorOperacao').value = "";
    document.getElementById('mensagemSistema').innerText = "";
    document.getElementById('btn-cobrar').style.display = 'inline-block';
}

function buscarManualmente(){
    let valorDigitado = document.getElementById('idParticipante').value.trim(); 
        
    if(valorDigitado != ""){
        valorDigitado = valorDigitado.replace("user_", ""); 
        valorDigitado = valorDigitado.padStart(3, '0');
        usuarioAtualId = "user_" + valorDigitado;
        console.log("TESTE DE MESA - ID enviado: [" + usuarioAtualId + "]");
        buscarUsuarioNoBackend();
        let mensagem = document.getElementById('mensagemBuscaManual');
            mensagem.innerText = "Buscando o Participante no Sitema...";
        document.getElementById('idParticipante').value='';
    }
}

function liberarAcesso(){
     let senhaDigitada = document.getElementById('senhaAcesso').value;

    if (senhaDigitada.trim() == "") {
        alert("Por favor, insira uma senha.");
        return;
    }

    let urlApi = `/api/sistema/login?senhaDigitada=${senhaDigitada.trim()}`;
    fetch(urlApi, { method: 'POST' })
    .then(resposta => resposta.text())
    .then(textoResposta => {
        if (textoResposta.includes("acesso liberado")) {
            sessionStorage.setItem('caixaAutenticado', 'true');
            document.getElementById('liberar-acesso').style.display = 'none';
            document.getElementById('image-container').style.display = 'block';
        } else {
            alert("Senha incorreta. Tente novamente.");
        }
    })
    .catch(erro => alert("Erro ao conectar com o Java para autenticação de senha."));
}

window.onload = function() {
    let acessoLiberado = sessionStorage.getItem('caixaAutenticado');
    
    if (acessoLiberado === 'true') {
        document.getElementById('liberar-acesso').style.display = 'none';
        document.getElementById('image-container').style.display = 'block';
    }
};

function sairDoSistema() {
    sessionStorage.removeItem('caixaAutenticado');
    location.reload(); 
}
