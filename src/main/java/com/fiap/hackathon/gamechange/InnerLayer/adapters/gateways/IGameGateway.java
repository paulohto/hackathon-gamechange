package com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.AvailabilityStatus;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTO;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IGameRepository;

import java.util.List;
import java.util.Optional;

public interface IGameGateway {

    // Cria um Game: OK
    Game saveGame(Game game);
    List<Game> getGamesByOwnerId(String ownerId); // Adiciona o método para buscar jogos pelo ownerId
//    List<Game> findGamesByOwnerId(String ownerId); // Método para buscar jogos de um usuário

    // Lista todos os Games: OK
    List<Game> getAllGames();  // Modifica o retorno para lista de Games

    // Lê (ou busca) um Game por ID: OK
    Optional<Game> getGameById(String id);

    // Deleta um Game por ID: OK
    void deleteGame(String id);

    // Atualiza um Game por ID:
    Game updateGame(String userId, GameDTO gameAtualizado);

    boolean isGameExists(String gameId);

    // Adiciona o método para atualizar o status de disponibilidade do jogo
    void updateGameAvailabilityStatus(String gameId, AvailabilityStatus newStatus);
}
