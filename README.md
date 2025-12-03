
# MOTHES

## Sobre o projeto
MOTHES é um timer Pomodoro e um Pet Virtual  em JavaFX que busca te incentivar nos estudos! Ele foi criado como uma atividade para a conclusão da matéria de Programação Orientada a Objetos do curso de TADS do Instituto Federal do Paraná.

O projeto foi resultado da atividade final do curso onde o objetivo era criar um sistema que utilizasse o padrão de projeto DAO e todos as operações básicas do CRUD. Aproveitei para tentar aprender um pouco sobre o JavaFX, por conta disso você pode encontrar diversas entradas em FXML e Controllers que são minimamente questionáveis.
## Dependências
O projeto possui algumas dependências que não estão presentes nativamente no java, sendo elas:
* mysql_connector
* google_code_gson
* JavaFX (Dependendo da IDE ela não possui os pacotes necessários para inicializar um projeto desse tipo)
## Instalação
Para o funcionamento correto do projeto é necessário ter uma estrutura de banco de dados adequada, sendo possível acessá-la no arquivo **mothes.sql** na pasta **db** no root do projeto.

Também é preciso possui a IDE previamente configurada com JavaFX, é possível encontrar tutorias para essas configurações na [documentação oficial do JavaFX](https://openjfx.io/openjfx-docs/).

É recomendável utilizar um SDK Java 24 ou superior para alterar/rodar o projeto.

## Estrutura do Projeto
O projeto foi dividido em diversos pacotes além da estrutura DAO, abaixo haverá uma breve explicação e o que é possível encontrar dentro deles.
### Conexao
Aqui está a classe que lida com a ligação entre o programa e o banco de dados através do mysql_connector com as funções **getConexao()** e **fecharConexao**.
### Controllers
São as classes que auxiliam na manipulação da interface do usuário, ligando o back-end java com o front-end FXML. Muitas delas apenas foram utilizadas para manipular textos e ativar botões.
### Model
Aqui está grande parte do back-end com os pacotes BEAN e DAO, sendo BEAN para criação de classes importantes e manipulação de dados e o DAO para consultas no banco de dados ligadas a essas classes.
### Util
Por fim, esse pacote se trata de um conjunto de classes com funções que podem ser utilizadas pelo código inteiro como o conversores de tempo, validadores de formulário e a função de temporizador usado pelo timer pomodoro.

