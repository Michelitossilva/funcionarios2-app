Projeto de Gestão de Funcionários (Aplicação Java EE)
Visão Geral
Este projeto é uma aplicação web completa para a gestão de funcionários, desenvolvida de ponta a ponta utilizando tecnologias modernas do ecossistema Jakarta EE. O objetivo principal é demonstrar a construção de uma aplicação empresarial robusta, desde a configuração da infraestrutura (servidor de aplicação e banco de dados) até o desenvolvimento de uma interface web funcional.

A aplicação implementa todas as operações CRUD (Create, Read, Update, Delete) para a entidade "Funcionário", servindo como um exemplo prático e um excelente ponto de partida para projetos Java EE mais complexos.

Tecnologias Utilizadas
A pilha de tecnologias escolhida para este projeto reflete um ambiente de desenvolvimento empresarial moderno e alinhado com as melhores práticas do mercado.

Linguagem: Java 17

Plataforma: Jakarta EE 10

Servidor de Aplicação: WildFly 37

Banco de Dados: PostgreSQL 17

Ferramenta de Build: Apache Maven

Frameworks e APIs Principais
Jakarta Server Faces (JSF) 4.0: Para a construção da interface do utilizador (frontend).

Jakarta Persistence (JPA) 3.1: Para o mapeamento objeto-relacional (ORM) e a persistência de dados, utilizando o Hibernate como provedor.

Enterprise JavaBeans (EJB) 4.0: Para a implementação da lógica de negócio de forma transacional e segura.

Contexts and Dependency Injection (CDI) 4.0: Para a gestão do ciclo de vida dos beans e a injeção de dependências.

Arquitetura do Projeto
O projeto foi estruturado como um projeto Maven multi-módulo, uma abordagem que promove a organização e a reutilização de código.

pom.xml (Pai): O ficheiro principal que gere as dependências e os plugins para todos os módulos.

ejbs: Módulo do tipo EJB-JAR, contém as entidades JPA (Funcionario.java) e os serviços de negócio (FuncionarioService.java).

servlets: Módulo do tipo WAR, contém a camada de apresentação, incluindo as páginas JSF (index.xhtml), os managed beans (FuncionarioBean.java) e os ficheiros de configuração web (web.xml).

ear: Módulo do tipo EAR, responsável por empacotar todos os outros módulos (.jar e .war) num único ficheiro final que é implantado no servidor WildFly.

logging / primary-source: Módulos utilitários do tipo JAR.

Como Executar o Projeto
Para configurar e executar este projeto no seu ambiente local, siga os passos abaixo.

Pré-requisitos
JDK 17

Apache Maven 3.6+

PostgreSQL 17

WildFly 37

1. Configuração do Banco de Dados
   Aceda ao seu servidor PostgreSQL via psql ou pgAdmin.

Crie um novo banco de dados com o nome funcionarios:

CREATE DATABASE funcionarios;

2. Configuração do WildFly
   Instale o Driver JDBC: Crie o módulo do driver do PostgreSQL na pasta modules do WildFly, conforme a documentação oficial.

Crie o Datasource: No console de administração do WildFly, crie um novo datasource com as seguintes especificações:

Nome: PostgresDS

Nome JNDI: java:/PostgresDS

URL de Conexão: jdbc:postgresql://localhost:5432/funcionarios

Utilizador/Senha: As suas credenciais do PostgreSQL.

3. Build e Deploy
   Clone este repositório para a sua máquina local.

Navegue até à pasta raiz do projeto e execute o comando Maven para compilar e empacotar a aplicação:

mvn clean install

Após o build bem-sucedido, localize o ficheiro final na pasta ear/target/ear-1.0.ear.

Copie o ficheiro ear-1.0.ear para a pasta de deployments do seu servidor WildFly:
[WILDFLY_HOME]/standalone/deployments/

O WildFly fará o "hot deploy" da aplicação.

4. Aceder à Aplicação
   Abra o seu navegador e aceda ao seguinte URL:

http://localhost:8080/servlets/