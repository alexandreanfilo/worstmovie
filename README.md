# README

## Para rodar o projeto:

Versão JAVA: 11.

Foi utilizado framework Spring Boot, pela facilidade de configuração, haja vista seu objetivo como framework, e com isso, início rápido de desenvolvimento. Além da ótima compatibilidade com banco de dados embarcados (na proposta, H2) com JPA, bibliotecas de manipulação de arquivos (na proposta, CSV) e testes de integração.

Para rodar o projeto em modo de distribuição, pode ser utilizado o arquivo .jar que está no diretório target. Nesse diretório também está o arquivo movielist.csv, utilizado como fonte para importação e criação do banco de dados.

### Endpoints (na porta ```8080```):

-  Consulta de filmes: ```/movies/all```
-  Consulta de estúdios: ```/studios/all```
-  Consulta de produtores: ```/producers/all```
-  Consulta de intervalos entre dois prêmios consecutivos: ```/producers/consecutive-awards```

O projeto também pode ser executado em modo de desenvolvimento, através da classe principal WorstmovieApplication; Para build e gestão de dependências, foi utilizada a ferramenta MAVEN: ```mvn clean package```

## Para rodar os testes:

Foram desenvolvidos 1 teste de integração para cada endpoint. Os 3 primeiros são referentes a consulta de todos os filmes, estúdios e produtores, onde valida a quantidade total de dados retornados. E o último é referente a consulta dos prêmios consecutivos, onde valida se existe valores retornados de mínimo e máximo.

A ferramenta utilizada para desenvolvimento dos testes foi a JUnit 5, no VS CODE, e os testes foram executados utilizando o plugin "Test Runner for Java", o qual mostra a suite com todos os testes no menu lateral "Testing".

Os tests também podem ser executados pelo Maven: ```mvn tests```

## Obrigado pela oportunidade,
Alexandre Hauer Anfilo.
