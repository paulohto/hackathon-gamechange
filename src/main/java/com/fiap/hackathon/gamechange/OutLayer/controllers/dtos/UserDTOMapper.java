package com.fiap.hackathon.gamechange.OutLayer.controllers.dtos;

import com.fiap.hackathon.gamechange.InnerLayer.entites.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTOMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                //user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getPreferences(),
                //user.getGameCollection(), // Converter para List<GameDTO> se necessário
                user.getGameCollection()
                        .stream()
                        .map(GameDTOMapper::toDTO) // Converte cada Game para GameDTO
                        .collect(Collectors.toList()),

                user.getAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // Converte de User para UserRegisterDTO (inclui senha)
    public static UserRegisterDTO toRegisterDTO(User user) {
        return new UserRegisterDTO(
                //user.getName(),
                user.getId(),
                user.getEmail(),
                user.getPassword(), // Inclui o campo de senha
                user.getPreferences(),
//               null,
                // Converte a lista de Game para GameDTO
                user.getGameCollection()
                        .stream()
                        .map(GameDTOMapper::toDTO)
                        .collect(Collectors.toList()),

                user.getAddress()
        );
    }

//    // Converter uma lista de User para UserRegisterDTO
//    public static List<UserRegisterDTO> toRegisterDTOList(List<User> users) {
//        return users.stream().map(UserDTOMapper::toRegisterDTO).collect(Collectors.toList());
//    }


    // Converte de UserRegisterDTO para User
    public static User toEntity(UserRegisterDTO  userRegisterDTO) {
        if (userRegisterDTO == null) {
            throw new IllegalArgumentException("UserRegisterDTO cannot be null");
        }
        return new User(
                //userRegisterDTO.getId(),
                null,
                //userRegisterDTO.getName(),
                userRegisterDTO.getLogin(),
                userRegisterDTO.getEmail(),
                userRegisterDTO.getPassword(), // Adicione o campo password aqui
                userRegisterDTO.getPreferences(),
                //new ArrayList<>(), // Inicializa com uma lista vazia
                //userRegisterDTO.getGameCollection(), // Converter para List<Game> se necessário
//                userRegisterDTO.getGameCollection()
//                        .stream()
//                        .map(GameDTOMapper::toEntity) // Converte cada GameDTO para Game
//                        .collect(Collectors.toList()),
                userRegisterDTO.getGameCollection() != null ?
                        userRegisterDTO.getGameCollection().stream()
                                .map(GameDTOMapper::toEntity)
                                .collect(Collectors.toList())
                        : new ArrayList<>(),  // Se for null, inicializa como lista vazia

                userRegisterDTO.getAddress(),
                LocalDate.now(), // Para createdAt, usa a data atual
                LocalDate.now() // Para updatedAt, usa a data atual

        );
    }
}