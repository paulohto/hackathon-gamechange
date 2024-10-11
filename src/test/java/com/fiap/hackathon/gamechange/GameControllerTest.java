package com.fiap.hackathon.gamechange;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.AvailabilityStatus;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.GameCondition;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.GameRegisterUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.GameController;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameRegisterUseCase gameRegisterUseCase;

    private GameDTO gameDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um GameDTO para os testes
        gameDTO = new GameDTO(
                "1",
                "The Legend of Zelda",
                "Nintendo Switch",
                "ownerId123",
                GameCondition.NOVO,
                AvailabilityStatus.DISPONIVEL,
                LocalDate.now(),
                LocalDate.now()
        );
    }

    @Test
    void testSaveGame() {
        // Arrange
        Game gameEntity = GameDTOMapper.toEntity(gameDTO);
        when(gameRegisterUseCase.saveGame(any(Game.class))).thenReturn(gameEntity);

        // Act
        ResponseEntity<GameDTO> response = gameController.saveGame(gameDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(gameDTO.getId(), response.getBody().getId());
        verify(gameRegisterUseCase, times(1)).saveGame(any(Game.class));
    }

    @Test
    void testGetGameByIdFound() {
        // Arrange
        when(gameRegisterUseCase.getGameById("1")).thenReturn(Optional.of(gameDTO));

        // Act
        ResponseEntity<GameDTO> response = gameController.getGameById("1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(gameDTO.getId(), response.getBody().getId());
    }

    @Test
    void testGetGameByIdNotFound() {
        // Arrange
        when(gameRegisterUseCase.getGameById("2")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<GameDTO> response = gameController.getGameById("2");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllGames() {
        // Arrange
        List<GameDTO> games = new ArrayList<>();
        games.add(gameDTO);
        when(gameRegisterUseCase.getAllGames()).thenReturn(games);

        // Act
        ResponseEntity<List<GameDTO>> response = gameController.getAllGames();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(gameDTO.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testUpdateGame() {
        // Arrange
        GameDTO updatedGameDTO = new GameDTO(
                "1",
                "The Legend of Zelda - Updated",
                "Nintendo Switch",
                "ownerId123",
                GameCondition.NOVO,
                AvailabilityStatus.DISPONIVEL,
                LocalDate.now(),
                LocalDate.now()
        );
        when(gameRegisterUseCase.updateGame(any(String.class), any(GameDTO.class))).thenReturn(GameDTOMapper.toEntity(updatedGameDTO));

        // Act
        Game response = gameController.updateGame("1", updatedGameDTO);

        // Assert
        assertEquals(updatedGameDTO.getTitle(), response.getTitle());
        verify(gameRegisterUseCase, times(1)).updateGame(any(String.class), any(GameDTO.class));
    }

    @Test
    void testDeleteGame() {
        // Act
        ResponseEntity<Void> response = gameController.deleteGame("1");

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(gameRegisterUseCase, times(1)).deleteGame("1");
    }
}