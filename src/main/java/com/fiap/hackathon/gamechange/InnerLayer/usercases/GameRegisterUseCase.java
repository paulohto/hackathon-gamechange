package com.fiap.hackathon.gamechange.InnerLayer.usercases;


import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IGameGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IUserGateway;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class GameRegisterUseCase {
    private final IGameGateway gameGateway;

    public GameRegisterUseCase(IGameGateway gameGateway) {
        this.gameGateway = gameGateway;

    }

    //CRIA UM GAME: OK
    public Game saveGame(Game game){
        return gameGateway.saveGame(game);
    }

    //BUSCA UM GAME POR ID:
    public Optional<GameDTO> getGameById(String id) {
        Optional<Game> gameOpt = gameGateway.getGameById(id);
        return gameOpt.map(GameDTOMapper::toDTO); // Converte para UserRegisterDTO se encontrado
    }

    // BUSCA LISTA COM TODOS OS GAMES: OK
    public List<GameDTO> getAllGames() {
        List<Game> games = gameGateway.getAllGames();  // Pega a lista de usuários
        // Converte cada User para UserRegisterDTO
        return games.stream()
                .map(GameDTOMapper::toDTO)  // Usa o mapper para converter cada User
                .collect(Collectors.toList());  // Retorna uma lista de UserRegisterDTO
    }

    // ATUALIZA GAME POR ID: OK
    public Game updateGame(String userId, GameDTO gameAtualizado){
        return gameGateway.updateGame(userId, gameAtualizado);
    }

    // DELETA UM GAME POR ID: OK
    public void deleteGame(String id){
        gameGateway.deleteGame(id);
    }


}
