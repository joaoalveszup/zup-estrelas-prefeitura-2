# Sistema de prefeitura (zup-estrelas-prefeitura)

#### FUNÇÃO

- `Gerenciador de secretaria, funcionários e projetos` tem como função controlar o manejo organizacional de uma secretaria com seus respectivos funcionários e projetos contemplando:

1. Controle de secretaria:
    - Cadastro de nova secretaria;
    - Consulta de secretaria pelo ID;
    - Listagem de todas secretarias presentes;
    - Remoção de secretaria pelo ID.
    
2. Controle de funcionario:
    - Cadastro de novo funcionário;
    - Consulta de funcionário pelo ID;
    - Listagem de todos funcionário presentes;
    - Remoção de funcionário pelo ID.
    
3. Controle de projeto:
    - Cadastro de novo projeto;
    - Consulta de projeto pelo ID;
    - Listagem de todos projetos presentes;
    - Conclusão de projeto.
    
#### REQUISIÇÕES
* [Postman](https://documenter.getpostman.com/view/13173271/TVep7mv2)

#### RELACIONAMENTOS

![Relacionamento_Sistema_Prefeitura](https://user-images.githubusercontent.com/71403141/99076649-39cf2480-259a-11eb-8cda-e91cf28bb0d5.png)

#### TESTES REALIZADOS

1. Métodos testados em secretaria:
    - Cadastro de nova secretaria;
    - Remoção de secretaria.
    
2. Métodos testados em projeto:
	- Conclusão de projeto.

#### PRINCIPAIS BIBLIOTECAS UTILIZADAS PARA EXECUÇÃO DO PROJETO:

* [Lombok](https://projectlombok.org/setup/eclipse)
* [MySQL Connector/J](https://mvnrepository.com/artifact/mysql/mysql-connector-java)
* [Spring Web](https://mvnrepository.com/artifact/org.springframework/spring-web)
* [Spring Boot Starter Data JPA](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
* [Spring Boot DevTools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
* [JUnit](https://mvnrepository.com/artifact/junit/junit)

#### PROGRAMAS UTILIZADOS PARA EXECUÇÃO DO PROJETO:

* [IDE Eclipse](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2020-06/R/eclipse-inst-win64.exe)
* [MySQL](https://dev.mysql.com/downloads/installer/)

#### TAREFAS EM ABERTO

- Refatorar método alterarFuncionario para melhor visualização do mesmo;
- Refatorar nome das constatntes.

#### AUTOR

- [Elias Borges](https://www.linkedin.com/in/eliasborges)