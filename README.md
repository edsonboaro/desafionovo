Projeto de Automação de API - Desafio Sicredi
Este projeto apresenta a automação de testes para a API DummyJSON, desenvolvida como parte do desafio técnico para a posição de Senior QA Automation. A solução foca na validação de fluxos críticos de negócio, garantia de integridade de contrato e identificação de discrepâncias entre a documentação e a implementação real, não foram feitos todos os fluxos que eu faria com todas as validações possiveis para garantir a qualidade, por conta do tempo, então tem 1 fluxo de sucesso e 1 alternativo.


Tecnologias Utilizadas
    Java 21: Utilização da versão estável mais recente do JDK para suporte a recursos modernos.
    RestAssured: DSL para testes de API REST com foco em legibilidade e fluidez.
    JUnit 5: Framework para orquestração, organização e execução da suíte de testes.
    Hamcrest: Biblioteca de matchers para asserções robustas e validação de tipos de dados.
    Maven: Gerenciamento de dependências, build e ciclo de vida do projeto.
    Allure Report: Framework multi-linguagem para geração de relatórios detalhados e dashboards de execução.
    GitLab CI/CD: Automação do ciclo de execução em ambiente isolado.


Estratégia de Teste
    Para este desafio, foquei em validar os caminhos principais da API, garantindo que o básico funciona bem e que o código seja fácil de entender e manter:
    Teste de Disponibilidade: Um teste simples para garantir que a API está online e respondendo.
    Fluxo de Login: Validação do acesso e da geração do token de segurança para as próximas chamadas.
    Ações de Produtos: Testes selecionados de listagem e cadastro, cobrindo tanto o uso correto quanto situações onde a API deveria barrar um erro (como um cadastro vazio).


Organização do Projeto
    O código foi estruturado de forma organizada para que, caso o projeto cresça no futuro, seja fácil adicionar novos testes sem bagunça:
    Separação de Funções: Deixei a parte técnica da API separada dos testes em si.
    Configurações Centrais: Informações como a URL da API ficam em um único lugar, facilitando qualquer mudança rápida.
    Logs Detalhados: Configurei para que cada envio e cada resposta da API apareça no console, o que ajuda muito a encontrar onde está o erro quando um teste falha.


Relatório de Defeitos (Bug Report)
    Durante o desenvolvimento, foram identificadas inconsistências críticas entre a documentação oficial e o comportamento da API. Estes pontos foram mantidos como falhas na automação para evidenciar os bugs:

    Defeito 1: Inconsistência de Status Code (Login)
        Cenário: POST /auth/login com credenciais válidas.
        Esperado: Status Code 201 Created.
        Atual: Status Code 200 OK.

    Defeito 2: Ausência de Validação de Campos Obrigatórios
        Cenário: POST /products/add com body vazio {}.
        Esperado: Status Code 400 Bad Request.
        Atual: Status Code 201 Created permitindo a persistência de objetos nulos.

    Defeito 3: Divergência de Contrato (Chave do Token)
        Cenário: Retorno do objeto de autenticação.
        Esperado: Chave nomeada como token.
        Atual: Chave nomeada como accessToken.


Pipeline de CI/CD (GitLab)
    O projeto conta com uma pipeline automatizada via GitLab CI/CD (configurada no arquivo .gitlab-ci.yml):
    Execução Automática: Os testes são disparados a cada push na branch main.
    Ambiente Isolado: Utilização de imagem Docker maven:3.9.6-eclipse-temurin-21.
    Artefatos: Geração e armazenamento dos resultados do Allure e relatórios de cobertura JaCoCo após cada execução.

Como Executar os Testes
    Certifique-se de ter o Maven instalado e o JDK 21 configurado.

Executar a suíte completa:
    mvn clean test
    
Gerar e visualizar o Relatório Allure:
    mvn allure:serve