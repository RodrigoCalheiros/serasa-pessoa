# serasa-pessoa

Um serviço do tipo API REST para cadastro de pessoas com score e suas regiões de afinidades.

## EndPoints

Todos os end points podem ser acessados utilizando a collection disponível no diretório `/resources/serasa-pessoa.postman_collection.json`

Para acesso a API é necessário no primeiro momento a geração token. Em um cenário real, o token deve ser obtido por um
serviço apartado dedicado a gestão de autenticação.

É possível obter um token válido seguindo os seguintes parâmetros:

    POST /api/token
    Authorization
    Type: Basic Auth
    User: serasa
    Password: 1234

Após a geração do token, é possível acessar os end points listados:

    Authorization
    Type: Bearer Token
    Token: {Passar o token gerado no end point /api/token}

    Pessoa
    GET /api/Pessoa
    GET /api/Pessoa/{id}
    POST /api/Pessoa

    Afinidade
    POST /api/afinidade

    Score
    POST /api/score

Para um melhor entendimento e obtenção dos contratos de cada endPoint, é possível acessar o swagger disponível em `/swagger-ui.html`.

A base de dados pode ser acessada utilizando os seguintes parâmetros:

    End Point: /h2
    Driver Class: jdbc:h2:mem:banco
    Username: user
    Password: 1234

## Configuração de Ambiente

    Java 11
    Docker
    Maven

## Criação da imagem docker

Compilar a aplicação para gerar o jar: `mvn clean install`

Criar a imagem: `docker build -t serasa-pessoa:latest .`

Subir o container: `docker run -p 8080:8080 --rm serasa-pessoa`

## Geração dos arquivos private_key.der e public_key.der

Para a geração e validação do token de autenticação é necesário as chaves privateKey e publicKey. Apesar de entender que isso não recomendado em um caso real, foi disponibilizado no repositório os arquivos.

<pre>
<code>
openssl genrsa -out private_key.pem 2048

openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt

openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der
</code>
</pre>
