package com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway;

import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserAuxMapper {
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                //user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getPreferences(),
               // user.getGameCollection(), // Se precisar converter, faça isso aqui
                user.getGameCollection()
                        .stream()
                        .map(GameDTOMapper::toDTO) // Converte cada Game para GameDTO
                        .collect(Collectors.toList()),

                user.getAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toEntity(UserRegisterDTO userRegisterDTO) {
        return new User(
                //null, // Deixe o ID nulo para ser gerado na persistência
                userRegisterDTO.getId(),
                userRegisterDTO.getLogin(),
                //userRegisterDTO.getName(),

                userRegisterDTO.getEmail(),
                userRegisterDTO.getPassword(), // Aqui lidamos com a senha
                userRegisterDTO.getPreferences(),
                new ArrayList<>(), // Inicialize a lista de jogos como vazia ou adapte se necessário
                userRegisterDTO.getAddress(),
                LocalDate.now(), // Para createdAt, use a data atual
                LocalDate.now() // Para updatedAt, use a data atual
        );
    }

}
