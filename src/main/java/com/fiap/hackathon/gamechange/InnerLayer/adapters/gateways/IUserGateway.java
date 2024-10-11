package com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways;

import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;

import java.util.List;
import java.util.Optional;

public interface IUserGateway {

    // Cria um User: OK
    User saveUser(User user);

    // Lê (ou busca) um user por ID: OK
    Optional<User> getUserById(String id);

    // Lista todos os usuários: OK
    List<User> getAllUsers();  // Modifica o retorno para lista de Users

    // Atualiza um User por ID: OK
    User updateUser(String userId, UserRegisterDTO userAtualizado);

    // Deleta um user por ID: OK
    void deleteUser(String id);

    boolean isUserExists(String userId);

    //List<User> findAll();
}
