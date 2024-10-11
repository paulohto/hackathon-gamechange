package com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IGameGateway;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.AvailabilityStatus;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IGameRepository;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class GameGatewayAux implements IGameGateway {
    private final MongoTemplate mongoTemplate;
    private final IGameRepository gameRepository;
    private final GameAuxMapper gameAuxMapper;

    public GameGatewayAux(MongoTemplate mongoTemplate, IGameRepository gameRepository, GameAuxMapper gameAuxMapper) {
        this.mongoTemplate = mongoTemplate;
        this.gameRepository = gameRepository;
        this.gameAuxMapper = gameAuxMapper;
    }

    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public List<Game> getGamesByOwnerId(String ownerId) {
        // Implementar a lógica para buscar jogos pelo ownerId
        return gameRepository.findByOwnerId(ownerId);
    }

    // BUSCA GAME POR ID:
    @Override
    public Optional<Game> getGameById(String id) {
        Optional<Game> gameOpt = gameRepository.findById(id);
//        if (userOpt.isPresent()) {
//            Game game = userOpt.get();
//            // Buscar todos os jogos do usuário com base no ownerId
//           // List<User> games = userGateway.getGamesByOwnerId(game.getId());
//            // Popula a coleção de jogos no usuário
//            game.getGameCollection().addAll(games);
//        }
        return gameOpt;
    }

    // LISTAGEM GERAL : OK
    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();  // Retorna uma lista de games do banco de dados
    }

//    @Override
//    public List<Game> findGamesByOwnerId(String ownerId) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("ownerId").is(ownerId));
//        return mongoTemplate.find(query, Game.class); // Busca os jogos por ownerId
//    }

    @Override
    public Game updateGame(String id, GameDTO gameAtualizado) {
        // Busque o usuário existente
        Optional<Game> existingGameOpt = this.getGameById(id);

        if (existingGameOpt.isPresent()) {
            Game existingGame = existingGameOpt.get();
            // Atualize os campos necessários
            Game updatedGame = new Game(
                    existingGame.getId(), // Manter o mesmo ID
                    gameAtualizado.getTitle() != null ? gameAtualizado.getTitle() : existingGame.getTitle(),
                    gameAtualizado.getPlatform() != null ? gameAtualizado.getPlatform() : existingGame.getPlatform(),
                    gameAtualizado.getOwnerId() != null ? gameAtualizado.getOwnerId() : existingGame.getOwnerId(),
                    gameAtualizado.getCondition() != null ? gameAtualizado.getCondition() : existingGame.getCondition(),
                    gameAtualizado.getAvailabilityStatus() != null ? gameAtualizado.getAvailabilityStatus() : existingGame.getAvailabilityStatus(),
                    existingGame.getCreatedAt(),
                    LocalDate.now() // Atualiza a data de modificação
            );
            // Salve as alterações
            return this.saveGame(updatedGame);
        } else {
            // Se o usuário não for encontrado, retorne null ou lance uma exceção
            // throw new UserNotFoundException("User with ID " + userId + " not found");
            throw new RuntimeException("Game not found with id: " + id);
        }
    }

    @Override
    public void deleteGame(String id) {
        gameRepository.deleteById(id);
    }

    @Override
    public boolean isGameExists(String gameId) {
        // Verifica se o jogo existe no banco de dados.
        return gameRepository.findById(gameId).isPresent(); // O método findById retorna um Optional<Game>
    }

    @Override
    public void updateGameAvailabilityStatus(String gameId, AvailabilityStatus newStatus) {
        Optional<Game> gameOpt = this.getGameById(gameId);
        if (gameOpt.isPresent()) {
            Game game = gameOpt.get();
            Game updatedGame = game.withAvailabilityStatus(newStatus);  // Cria uma nova instância com o novo status
            saveGame(updatedGame);  // Salva as alterações
        } else {
            throw new RuntimeException("Game not found with ID: " + gameId);
        }
    }

}
