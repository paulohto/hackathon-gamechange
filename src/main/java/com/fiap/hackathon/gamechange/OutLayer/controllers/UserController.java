package com.fiap.hackathon.gamechange.OutLayer.controllers;

import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.UserRegisterUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserRegisterUseCase userRegisterUseCase;
    private UserDTOMapper userDTOMapper;

    public UserController(UserRegisterUseCase userRegisterUseCase, UserDTOMapper userDTOMapper) {
        this.userRegisterUseCase = userRegisterUseCase;
        this.userDTOMapper = userDTOMapper;
    }

    // SALVA/CRIA UM USER: OK
    @PostMapping
    public ResponseEntity<UserRegisterDTO> saveUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        // Mapeia o DTO para a entidade
        var userEntity = userDTOMapper.toEntity(userRegisterDTO);

        // Registra o usuário e obtém o usuário salvo com o ID gerado
        User savedUser = userRegisterUseCase.saveUser(userEntity);
        // Mapeia o usuário salvo de volta para o UserRegisterDTO
        UserRegisterDTO savedUserRegisterDTO = userDTOMapper.toRegisterDTO(savedUser);

        return ResponseEntity.ok(savedUserRegisterDTO);
    }

    // BUSCA USER POR ID: OK
    @GetMapping("/{id}")
   public ResponseEntity<UserRegisterDTO> getUserById(@PathVariable String id){
        Optional<UserRegisterDTO> user = userRegisterUseCase.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Endpoint para retornar todos os usuários com seus jogos
//    @GetMapping("/users/get-all-users-com-games")
//    public List<UserRegisterDTO> getAllUsersWithGames() {
//        return userRegisterUseCase.getAllUsersWithGames(); // Chamar o caso de uso
//    }


    // BUSCA LISTA GERAL DE USER: OK
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserRegisterDTO>> getAllUsers() {
        List<UserRegisterDTO> users = userRegisterUseCase.getAllUsers();  // Chama o método do UseCase
        return ResponseEntity.ok(users);  // Retorna a lista de usuários
    }

    // ATUALIZA USER POR ID: OK
    @PutMapping("/edita-user-id/{id}")
    public User updateUser(@PathVariable String id, @RequestBody UserRegisterDTO userAtualizado){
//        User updatedUser = this.userRegisterUseCase.updateUser(id, userAtualizado);
//        UserRegisterDTO updatedUserDTO = UserDTOMapper.toRegisterDTO(updatedUser); // Mapeie de volta para o DTO
//        return ResponseEntity.ok(updatedUserDTO); // Retorne o DTO atualizado
        return  this.userRegisterUseCase.updateUser(id, userAtualizado);
    }

    // DELETA UM USER POR ID: OK
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userRegisterUseCase.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Retorna 204 No Content após a exclusão
    }

}
