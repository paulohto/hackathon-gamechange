# GameChange: Sistema de Troca de Jogos

O **GameChange** é uma plataforma voltada para o compartilhamento e troca de jogos de videogame entre usuários. O sistema foi projetado seguindo os princípios da **Clean Architecture**, visando uma separação clara de responsabilidades e facilidade de manutenção e escalabilidade. Ao final dessa documentação será possível verificar a estrutura das camadas do projeto.

### Tecnologias Utilizadas:
- **Java 17**: para garantir a compatibilidade com recursos modernos da linguagem.
- **Spring Boot**: framework para criar aplicações Java robustas e escaláveis.
- **Maven**: ferramenta de automação de build e gerenciamento de dependências.
- **MongoDB**: banco de dados NoSQL para armazenamento escalável de dados.
- **Spring Security**: para garantir uma autenticação e autorização seguras, utilizando tokens JWT para controle de sessão.

### Principais funcionalidades incluem:
- **CRUDs**: gerenciamento completo de usuários, jogos e propostas de troca.
- **Persistência de dados**: utilizando **MongoDB**, garantindo escalabilidade e performance.
- **Autenticação segura**: login protegido via **Spring Security**, com sessões controladas por **tokens JWT** para maior segurança e flexibilidade.

Este projeto oferece uma solução para gamers que desejam trocar ou compartilhar seus jogos de forma organizada e segura.

---
### *Registrar Usuário - seção aberta*
``POST: http://localhost:8080/users``
<p>
"preferences": deve ser uma das opções (PRESENCIAL, CORREIO) 

```
    {
        "login": "TesteId-9",
        "email": "sec9@teste.com",
        "password": "s123",
        "preferences": "CORREIO",
        "address": "Casa 100, Bairro Acolá, Av A"
    }
```
<p> Retorno esperado:

```
{
    "id": "670ac132afe6c012ba8c8e25",
    "login": "TesteId-9",
    "email": "sec9@teste.com",
    "password": "$2a$10$8CqY5S74gGn8231hVu26yuv3gzechmWwsNqhmJjAu7n/C4cKrghKi",
    "preferences": "CORREIO",
    "gameCollection": [],
    "address": "Casa 100, Bairro Acolá, Av A"
}
```

### *Login - seção aberta*
``POST: http://localhost:8080/auth/login``
<br>
```
{
    "login": "TesteId-9",
    "password": "s123"
}
```
<p>
Retorno esperado (token temporário).<br>
O Token deverá ser utilizado para acessar todas as demais áreas autenticadas.

```
{
    "token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6IlRlc3RlMSIsImV4cCI6MTcxMDYwNDY5MX0.G666e1Qz43WkPTIJngxMmQrs8OmjkPqGU_3_2WnDprI"
}
```
---

### User
Acessar endpoints em seção autorizada (passando o Token no Type: Bearer Token).
<br>
```
GET: http://localhost:8080/users/get-all-users
GET: http://localhost:8080/users/{id}
PUT: http://localhost:8080/users/edita-user-id/{id}
DELETE: http://localhost:8080/users/delete-user/{id}
```
<p>
Exemplos:

```
Retorno esperado para:
GET: http://localhost:8080/users/670ac132afe6c012ba8c8e25

{
    "id": "670ac132afe6c012ba8c8e25",
    "login": "TesteId-9",
    "email": "sec9@teste.com",
    "password": "$2a$10$8CqY5S74gGn8231hVu26yuv3gzechmWwsNqhmJjAu7n/C4cKrghKi",
    "preferences": "CORREIO",
    "gameCollection": [
        {
            "id": "670ac1c645ba4a642b1c3151",
            "title": "Mortal Kombat 11",
            "platform": "Playstation",
            "ownerId": "670ac132afe6c012ba8c8e25",
            "condition": "NOVO",
            "availabilityStatus": "DISPONIVEL",
            "createdAt": "2024-10-12",
            "updatedAt": "2024-10-12"
        }
    ],
    "address": "Casa 100, Bairro Acolá, Av A"
}
```
```
Para:
PUT: http://localhost:8080/users/edita-user-id/670ac132afe6c012ba8c8e25
    
{
     "login": "TesteId-9-OK",
     "email": "sec9@teste.com",
     "password": "s123",
     "preferences": "PRESENCIAL",
     "address": "Casa 100, Bairro Acolá, Av A"
}

Retorno esperado:
{
    "id": "670ac132afe6c012ba8c8e25",
    "login": "TesteId-9-OK",
    "email": "sec9@teste.com",
    "password": "s123",
    "preferences": "PRESENCIAL",
    "gameCollection": [
        {
            "id": "670ac1c645ba4a642b1c3151",
            "title": "Mortal Kombat 11",
            "platform": "Playstation",
            "ownerId": "670ac132afe6c012ba8c8e25",
            "condition": "NOVO",
            "availabilityStatus": "DISPONIVEL",
            "createdAt": "2024-10-12",
            "updatedAt": "2024-10-12"
        }
    ],
    "address": "Casa 100, Bairro Acolá, Av A"
}
```
### Game
Acessar endpoints em seção autorizada (passando o Token no Type: Bearer Token).
<br>
```
POST: http://localhost:8080/games
GET: http://localhost:8080/games/get-all-games
GET: http://localhost:8080/games/{id}
PUT: http://localhost:8080/games/edita-game-id/{id}
DELETE: http://localhost:8080/games/delete-game/{id}
```
<p>
Exemplos:

```
Para:
POST: http://localhost:8080/games

"condition": deve ser uma das opções (NOVO, USADO)
"availabilityStatus": deve ser uma das opções (DISPONIVEL, INDISPONIVEL, EM_NEGOCIACAO, TROCADO)
Será trocado conforme situação das negociações que acontecerão. Por Default DISPONIVEL. 

{
  "title": "The Last Of Us",
  "platform": "Playstation",
  "ownerId": "670ac132afe6c012ba8c8e25",  
  "condition": "NOVO", 
  "availabilityStatus": "DISPONIVEL"
}

Retorno esperado:
{
    "id": "670bd53db196b63203a5218e",
    "title": "The Last Of Us",
    "platform": "Playstation",
    "ownerId": "670ac132afe6c012ba8c8e25",
    "condition": "NOVO",
    "availabilityStatus": "DISPONIVEL",
    "createdAt": "2024-10-13",
    "updatedAt": "2024-10-13"
}

```
### Proposal
Acessar endpoints em seção autorizada (passando o Token no Type: Bearer Token).
<p>

```
POST: http://localhost:8080/proposals
GET: http://localhost:8080/proposals/get-all-proposals
GET: http://localhost:8080/proposals/{id}
DELETE: http://localhost:8080/proposals/delete-proposal/{id}
PATCH: http://localhost:8080/proposals/accept-status?recipientId={id}&proposalId={id}&status=NEGOCIANDO
```
<p>
Exemplos:

```
Para:
POST: http://localhost:8080/proposals

- gameOfferedId: Id do jogo oferecido na troca
- gameRequestedId: Id do jogo requisitado na troca
- proposerId: Id do usuário que propós a troca
- recipientId: Usuário que recebeu a proposta da troca
- proposalId: Id da proposta
- status: Aqui será informado (PENDENTE, ACEITA, RECUSADA, NEGOCIANDO).
PENDENTE é o status default.

{
    "gameOfferedId": "6705efac5eff895fb514ae2c", 
    "gameRequestedId": "6704a50ca61cba28feacc8d6", 
    "proposerId": "6705ef565eff895fb514ae2b", 
    "recipientId": "6704a461a61cba28feacc8d5", 
    "status": "PENDENTE"
}

Retorno esperado:
{
    "id": "6705f0995eff895fb514ae2d",
    "gameOfferedId": "6705efac5eff895fb514ae2c",
    "gameOfferedTitle": "Mortal Kombat 11",
    "gameRequestedId": "6704a50ca61cba28feacc8d6",
    "gameRequestedTitle": "Far Cry 5",
    "proposerId": "6705ef565eff895fb514ae2b",
    "proposerName": "Security8",
    "recipientId": "6704a461a61cba28feacc8d5",
    "recipientName": "Security6",
    "status": "PENDENTE",
    "createdAt": "2024-10-08",
    "updatedAt": "2024-10-08"
}

```

```
Para:
GET: http://localhost:8080/proposals/6705f0995eff895fb514ae2d

Retorno esperado:
{
    "id": "6705f0995eff895fb514ae2d",
    "gameOfferedId": "6705efac5eff895fb514ae2c",
    "gameOfferedTitle": "Mortal Kombat 11",
    "gameRequestedId": "6704a50ca61cba28feacc8d6",
    "gameRequestedTitle": "Far Cry 5",
    "proposerId": "6705ef565eff895fb514ae2b",
    "proposerName": "Security8",
    "recipientId": "6704a461a61cba28feacc8d5",
    "recipientName": "Security6",
    "status": "NEGOCIANDO",
    "createdAt": "2024-10-08",
    "updatedAt": "2024-10-08"
}

```

```
Para:
PATCH: http://localhost:8080/proposals/accept-status?recipientId=6704a461a61cba28feacc8d5&proposalId=6705f0995eff895fb514ae2d&status=NEGOCIANDO

Retorno esperado:
{
    "id": "6705f0995eff895fb514ae2d",
    "gameOfferedId": "6705efac5eff895fb514ae2c",
    "gameOfferedTitle": "Mortal Kombat 11",
    "gameRequestedId": "6704a50ca61cba28feacc8d6",
    "gameRequestedTitle": "Far Cry 5",
    "proposerId": "6705ef565eff895fb514ae2b",
    "proposerName": "Security8",
    "recipientId": "6704a461a61cba28feacc8d5",
    "recipientName": "Security6",
    "status": "NEGOCIANDO",
    "createdAt": "2024-10-08",
    "updatedAt": "2024-10-08"
}

OBS:
"availabilityStatus" de GAME será modificado conforme o status da Proposta. 
(DISPONIVEL, INDISPONIVEL, EM_NEGOCIACAO, TROCADO) 

```
---
## Estrutura do Projeto: GameChange

Este projeto foi desenvolvido seguindo os princípios de **Clean Architecture**. A estrutura do sistema foi organizada em camadas, separando as responsabilidades de cada parte do código para manter a independência entre domínios, lógica de negócios e infraestrutura. A arquitetura promove a fácil manutenção, expansão e adaptação do sistema.

### *Camadas Internas:*
- **Entities**:
    - Game
    - Proposal
    - User
    - **Enums**:
        - AvailabilityStatus
        - GameCondition
        - ProposalStatus
        - TradePreference
- **UseCases**:
    - GameRegisterUseCase
    - LoginUserUseCase
    - ProposalUseCase
    - UserRegisterUseCase
- **Adapters**:
    - **Gateways**:
        - IGameGateway
        - IProposalGateway
        - IUserGateway

### *Camadas Externas:*
- **Infrastructure**:
    - **Persistence**:
        - IGameRepository
        - IProposalRepository
        - IUserRepository
    - **Gateway**:
        - GameAuxMapper
        - GameGatewayAux
        - ProposalAuxMapper
        - ProposalGatewayAux
        - UserGatewayAux
        - UserAuxMapper
- **Controllers**:
    - GameController
    - ProposalController
    - UserController
    - **DTOs**:
        - GameDTO
        - GameDTOMapper
        - ProposalDTO
        - ProposalDTOMapper
        - UserDTO
        - UserDTOMapper
        - UserRegisterDTO
### *Main:*
- GameConfig
- ProposalConfig
- UserConfig

### *Security:*
- AuthController
- AuthorizationService
- SecurityConfig
- SecurityFilter
- TokenService
- UserDetailsDTO
- **Records**:
    - AuthenticationDTO
    - LoginResponseDTO
    - UserAuthRequest
    - UserRequest
    - UserResponse


## Explicando Camadas Internas:

### Entities:
Representam as regras de negócios centrais do domínio. São classes puras do Java e não dependem de frameworks externos ou de detalhes de infraestrutura.

Exemplo: User,  Game, Proposal

### Use Cases:
Contêm as regras específicas de aplicação, representando casos de uso do sistema. São responsáveis por coordenar as interações entre as entidades, garantindo que as regras de negócio sejam respeitadas.

Exemplo: RegisterUser, ProposeGameExchange, ListAvailableGames

### Interface Adapters:
Camada responsável por adaptar dados de entrada e saída. Aqui estão os controladores, conversores DTOs, mapeadores e gateways que facilitam a comunicação entre a camada de casos de uso e o mundo externo.

Exemplo: UserController, GameDTO, ProposalMapper

### Frameworks & Drivers:
É a camada mais externa e contém os frameworks e ferramentas que suportam a infraestrutura do sistema, como banco de dados, bibliotecas externas e serviços de autenticação.

Exemplo: MongoDBRepository, Spring Security, JWT

### Fluxo de Dados na Clean Architecture:
1. As requisições de entrada chegam pelos controladores na camada de Interface Adapters, que por sua vez invocam os casos de uso.
2. Os casos de uso manipulam as entidades e coordenam as operações, retornando dados ou acionando processos de saída (por exemplo, persistência).
3. As respostas são transformadas em DTOs e retornadas para a camada externa, mantendo as entidades e casos de uso isolados das dependências de infraestrutura.


