package com.fiap.hackathon.gamechange.OutLayer.controllers;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.GameRegisterUseCase;

import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {
    private GameRegisterUseCase gameRegisterUseCase;
    private GameDTOMapper gameDTOMapper;

    public GameController(GameRegisterUseCase gameRegisterUseCase, GameDTOMapper gameDTOMapper) {
        this.gameRegisterUseCase = gameRegisterUseCase;
        this.gameDTOMapper = gameDTOMapper;
    }

    // SALVA/CRIA UM GAME: OK
    @PostMapping
    public ResponseEntity<GameDTO> saveGame(@RequestBody GameDTO gameDTO) {
        // Mapeia o DTO para a entidade
        var gameEntity = gameDTOMapper.toEntity(gameDTO);
        // Registra o game
        gameRegisterUseCase.saveGame(gameEntity);
        // Retorna um status 201 (Created) após o registro bem-sucedido
        // return ResponseEntity.status(201).build();
        return ResponseEntity.ok(gameDTO);
    }

    // BUSCA GAME POR ID: OK
    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable String id){
        Optional<GameDTO> game = gameRegisterUseCase.getGameById(id);
        return game.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // BUSCA LISTA GERAL DE GAME: OK
    @GetMapping("/get-all-games")
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<GameDTO> games = gameRegisterUseCase.getAllGames();  // Chama o método do UseCase
        return ResponseEntity.ok(games);  // Retorna a lista de usuários
    }

    // ATUALIZA GAME POR ID:
    @PutMapping("/edita-game-id/{id}")
    public Game updateGame(@PathVariable String id, @RequestBody GameDTO gameAtualizado){
//        User updatedUser = this.userRegisterUseCase.updateUser(id, userAtualizado);
//        UserRegisterDTO updatedUserDTO = UserDTOMapper.toRegisterDTO(updatedUser); // Mapeie de volta para o DTO
//        return ResponseEntity.ok(updatedUserDTO); // Retorne o DTO atualizado
        return  this.gameRegisterUseCase.updateGame(id, gameAtualizado);
    }

    // DELETA UM GAME POR ID: OK
    @DeleteMapping("/delete-game/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id){
        gameRegisterUseCase.deleteGame(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Retorna 204 No Content após a exclusão
    }
}
