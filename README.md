#### Resumo

Projeto para simular o Backend de um processo de criação de uma nova conta em um banco digítal;

#### Configuração do ambiente;

* Necessário java 11+;
* Maven 3x+;
* PostgresSQL, caso não tiver o postgres intalado local pode utilizar o docker rodando `docker-compose run` na raiz do projeto que uma instância do banco será criada com o usuário root e senha root 
* Para executar o ambiente de dev sem o envio de emails reais abra o arquivo `start-dev.sh` e substitua as informações do seu banco nas variaveis de ambiente abaixo, após substituir as variáveis rode o comando `sh start-dev.sh` na raiz do projeto dentro do bash:
 ```bash
  export DB_HOST=localhost
  export DB_PORT=5433
  export DB_USER=root 
  export DB_PASSWORD=root
 ```
 
 * Para executar o ambiente de produção com o envio de emails reais abra o arquivo `start-prod.sh` e substitua as informações do seu banco e credenciais do seu email nas variaveis de ambiente abaixo, após substituir as variáveis rode o comando `sh start-prod.sh` na raiz do projeto dentro do bash:
 ```bash
  export DB_HOST=localhost
  export DB_PORT=5433
  export DB_USER=root 
  export DB_PASSWORD=root
  
  export EMAIL_LOGIN=se-email@gmail.com
  export EMAIL_PASSWORD=sua-senha
 ```

* Caso queira rodar o projeto pela IDE, certifique-se que ela tem o plugin do lombok instalado e substitua as informações nos arquivos application.properties de acordo comas informações do seu banco e email caso rode o profile prod; 


#### Testando a aplicação

* Para criar uma nova proposta de conta faça uma requisição `POST` para `http://localhost:8080/proposta-conta` passando o json abaixo no body. Lembre de usar um cpf e email válido, você pode suar o https://www.4devs.com.br/gerador_de_cpf para gerar cpfs válidos:
``` json
{
    "nome": "Gigelda",
    "sobrenome": "da silva",
    "email": "gigelda@hotmail.com",
    "cpf": "759.134.150-27",
    "dataNascimento": "2001-11-30"
}
```

* Para cadastrar um endereço para a porposta faça uma requisição `POST` para `http://localhost:8080/proposta-conta/{id-porposta}/endereco` passando o json abaixo no body, lembre-se de usar um cep válido.
``` json
{
    "cep": "98000-000",
    "rua": "Urussanga",
    "bairro": "Bucarein",
    "complemento": "Bloco B, apto 411",
    "cidade": "Joinville",
    "estado": "SC"
}
```

* Para cadastrar uma foto de cpf para a porposta faça uma requisição `POST` para `http://localhost:8080/proposta-conta/{id-porposta}/cpf` passando o json abaixo no body.
``` json
{
    "frente": "https://foto/frente",
    "verso":"https://foto/verso"
}
```

* Para finalizar a porposta faça uma requisição `POST` para `http://localhost:8080/proposta-conta/{id-porposta}/finalizar` passando o json abaixo no body.
``` json
{
    "propostaAceita": true
}
```

* Para realizar o primeiro acesso faça um `POST` para `http://localhost:8080/primeiro-acesso` caso as informações estejam corretas um token gerá gerado nos logs e um email será disparado com o token;
``` json
{
    "email": "b@hotmail.com",
    "cpf": "759.134.150-27"
}
```

* Para validar o token da conta faça um `POST` para `http://localhost:8080/conta/{id-conta}/token/{valor-token}`;

* Para criar uma senha para a conta faça um `POST` para `http://localhost:8080/conta/ad2e10f7-a4f6-4306-aa4d-57060424f211/senha` passando o json abaixo no body, a senha deve ter 8 digitos com letras maiusculas, minúsculas, números e caracteres especiáis
``` json
{
    "senha": "SEnh@123"
}
```

* Para realizar transferência faça um `POST` para `http://localhost:8080/conta/transferencia` passando o json abaixo no body;
``` json
[
    {
        "codigoTransferencia": "cccc",
        "valorTransferencia": 10.50,
        "dataTransferencia": "2020-10-12 21:34:55",
        "documentoOrigem": "759.134.150-27",
        "bancoOrigem": "124",
        "contaOrigem": "87546321",
        "agenciaOrigem": "9999",
        "contaDestino": "47685772",
        "agenciaDestino": "0318"
    }
]
```
