package com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;

import java.time.LocalDate;

public class GameAuxMapper {

    // Converte uma entidade Game para um DTO GameDTO
    public GameDTO toDTO(Game game) {
        return new GameDTO(
                game.getId(),                        // Mapeia o ID do jogo
                game.getTitle(),                     // Mapeia o título do jogo
                game.getPlatform(),                  // Mapeia a plataforma do jogo
                game.getOwnerId(),                   // Mapeia o ID do proprietário
                game.getCondition(),                 // Mapeia a condição do jogo (novo/usado)
                game.getAvailabilityStatus(),        // Mapeia o status de disponibilidade (disponível/indisponível)
                game.getCreatedAt(),                 // Mapeia a data de criação
                game.getUpdatedAt()                  // Mapeia a data de atualização
        );
    }

    // Converte um DTO GameDTO para uma entidade Game
    public Game toEntity(GameDTO gameDTO) {
        return new Game(
                //gameDTO.getId(),                     // Mapeia o ID do DTO para a entidade Game
                null,                                // Deixe o ID nulo para ser gerado na persistência
                gameDTO.getTitle(),                  // Mapeia o título do DTO para a entidade Game
                gameDTO.getPlatform(),               // Mapeia a plataforma do DTO para a entidade Game
                gameDTO.getOwnerId(),                // Mapeia o ID do proprietário do DTO para a entidade Game
                gameDTO.getCondition(),              // Mapeia a condição do DTO para a entidade Game
                gameDTO.getAvailabilityStatus(),     // Mapeia o status de disponibilidade do DTO para a entidade Game
                LocalDate.now(),              // Mapeia a data de criação do DTO para a entidade Game
                LocalDate.now()               // Mapeia a data de atualização do DTO para a entidade Game
        );
    }
}
