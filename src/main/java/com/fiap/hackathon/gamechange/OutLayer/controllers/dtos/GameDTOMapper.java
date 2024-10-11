package com.fiap.hackathon.gamechange.OutLayer.controllers.dtos;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;

import java.time.LocalDate;

public class GameDTOMapper {
    public static GameDTO toDTO(Game game) {
        return new GameDTO(
                game.getId(),
                game.getTitle(),
                game.getPlatform(),
                game.getOwnerId(),
                game.getCondition(),
                game.getAvailabilityStatus(),
                game.getCreatedAt(),
                game.getUpdatedAt()
        );
    }

    public static Game toEntity(GameDTO gameDTO) {
        return new Game(
                //gameDTO.getId(),
                null,
                gameDTO.getTitle(),
                gameDTO.getPlatform(),
                gameDTO.getOwnerId(),
                gameDTO.getCondition(),
                gameDTO.getAvailabilityStatus(),
                LocalDate.now(), // Para createdAt, usa a data atual
                LocalDate.now() // Para updatedAt, usa a data atual
        );
    }
}
