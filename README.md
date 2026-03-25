# 💳 IluminarCard - Sistema Cashless para Eventos

> Um sistema web completo (Full-Stack) desenvolvido como projeto de estudo prático para gerenciar pagamentos, depósitos e leitura de QR Codes em tempo real durante eventos.

## 📖 Sobre o Projeto

O **IluminarCard** nasceu da necessidade de aplicar conceitos de desenvolvimento backend e frontend em um cenário do mundo real: a substituição de fichas de papel por um sistema de caixa digital (cashless). 

O sistema permite que o operador do caixa faça a leitura do crachá do participante (via Câmera/QR Code ou busca manual), visualize o saldo atual e realize operações de crédito (depósito) e débito (compra), tudo integrado a um banco de dados relacional e com auditoria de log permanente.

## 🚀 Funcionalidades Principais

* **Leitura de QR Code:** Integração com a câmera do dispositivo via `html5-qrcode` para busca ágil de participantes.
* **Busca Manual:** Fallback para digitação do ID caso o crachá esteja danificado.
* **Gestão de Saldo:** Operações financeiras de Depósito e Cobrança com validação de regras de negócio (ex: impedimento de saldo negativo para clientes padrão).
* **Auditoria e Logs (Caixa Preta):** Registro automático de todas as transações em um arquivo `.txt` persistente (`log_operacoes_iluminarcard.txt`), garantindo que o histórico financeiro não seja perdido caso o servidor seja reiniciado.
* **Interface Responsiva:** UI que simula o cartão físico da organização, com layout fluido usando Flexbox e Media Queries para se adaptar perfeitamente a telas de computadores, tablets e celulares.

## 💻 Tecnologias e Ferramentas

**Backend:**
* **Java 17**
* **Spring Boot (v4.0.4)**
* **Spring Web** (Criação de APIs RESTful)
* **Spring Data JPA / Hibernate** (Mapeamento Objeto-Relacional e persistência)
* **MySQL** (Banco de dados relacional oficial do sistema)
* **Maven** (Gerenciamento de dependências e build)

**Frontend:**
* **HTML5 / CSS3** (Layout semântico e responsivo)
* **JavaScript (Vanilla)** (Consumo da API Rest e manipulação do DOM)
* **HTML5-QRCode** (Biblioteca open-source para scanner via câmera)

## 🧠 Conceitos Aplicados (Aprendizados)

Este projeto foi fundamental para consolidar os seguintes conceitos de Engenharia de Software:
* **Arquitetura em Camadas:** Separação clara entre `Controller` (Rotas HTTP), `Service` (Regras de negócio e Log) e `Repository` (Banco de dados).
* **Orientação a Objetos e Encapsulamento:** As regras de validação de saldo ficam blindadas dentro da entidade `Usuario.java` (Domain Driven Design em escala reduzida).
* **Injeção de Dependências:** Uso nativo do Spring Framework para injetar instâncias do banco de dados na camada de serviços.
* **Tratamento de Exceções:** Respostas tratadas no backend caso um ID inexistente seja enviado na requisição HTTP, por exemplo.
* **Persistência de Arquivos (I/O):** Uso de `FileWriter` em modo *append* para gravar logs de forma segura e não obstrutiva.

## ⚙️ Como executar o projeto na sua máquina

### Pré-requisitos
* Java Development Kit (JDK) 17 ou superior.
* Servidor MySQL rodando localmente (porta padrão 3306).
* Maven.

### Passo a Passo

1. **Clone este repositório:**
   
     git clone https://github.com/Andre-Louzada/IluminarCard.git

2. **Configure o Banco de Dados:**
   
   Abra o arquivo src/main/resources/application.properties e insira as credenciais do seu MySQL local:
         spring.datasource.username=seu_usuario
         spring.datasource.password=sua_senha

   Nota: Para utilizar o sistema, é necessário que o banco de dados já esteja populado. 

4. **Inicie a Aplicação**

   Abra o cmd na raiz do projeto e insira:
          mvn spring-boot:run
            ou
          mvnw spring-boot:run
   
6. **Defina a senha de acesso para web**

   No próprio terminal, defina a senha para acessar na web.

7. **Acesse o Sistema:**
   
  Abra o seu navegador, insira a senha e acesse o sistema:
          👉 http://localhost:8080/index.html
    
