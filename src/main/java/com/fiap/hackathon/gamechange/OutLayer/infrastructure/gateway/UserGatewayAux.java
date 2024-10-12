package com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IGameGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IUserGateway;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class UserGatewayAux implements IUserGateway {
    private final MongoTemplate mongoTemplate;
    private final IUserRepository userRepository;
    private final UserAuxMapper userAuxMapper;
    private final IGameGateway gameGateway;

    public UserGatewayAux(MongoTemplate mongoTemplate, IUserRepository userRepository, UserAuxMapper userAuxMapper, IGameGateway gameGateway) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
        this.userAuxMapper = userAuxMapper;
        this.gameGateway = gameGateway;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user); // Salve o objeto User diretamente
    }
    @Override
    public Optional<User> getUserById(String id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Buscar todos os jogos do usuário com base no ownerId
            List<Game> games = gameGateway.getGamesByOwnerId(user.getId());
            // Popula a coleção de jogos no usuário
            user.getGameCollection().addAll(games);
        }

        return userOpt;
    }

    // LISTAGEM GERAL
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Retorna uma lista de usuários do banco de dados
    }

    @Override
    public User updateUser(String userId, UserRegisterDTO userAtualizado) {
        // Busque o usuário existente
        Optional<User> existingUserOpt = this.getUserById(userId);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            // Atualize os campos necessários
            User updatedUser = new User(
                    existingUser.getId(), // Manter o mesmo ID
                    existingUser.getLogin() != null ? userAtualizado.getLogin() : existingUser.getLogin(),
                    //userAtualizado.getName() != null ? userAtualizado.getName() : existingUser.getName(),
                    userAtualizado.getEmail() != null ? userAtualizado.getEmail() : existingUser.getEmail(),
                    userAtualizado.getPassword() != null ? userAtualizado.getPassword() : existingUser.getPassword(),
                    userAtualizado.getPreferences() != null ? userAtualizado.getPreferences() : existingUser.getPreferences(),
                    existingUser.getGameCollection(), // Mantém a coleção de jogos
                    userAtualizado.getAddress() != null ? userAtualizado.getAddress() : existingUser.getAddress(),
                    existingUser.getCreatedAt(), // Mantém a data de criação original
                    LocalDate.now() // Atualiza a data de modificação
            );
            // Salve as alterações
            return this.saveUser(updatedUser);
        } else {
            // Se o usuário não for encontrado, retorne null ou lance uma exceção
           // throw new UserNotFoundException("User with ID " + userId + " not found");
            throw new RuntimeException("User not found with id: " + userId);
        }

    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
        // Lógica para deletar o usuário
    }

    @Override
    public boolean isUserExists(String userId) {
        // Verifica se o usuário existe no banco de dados.
        return userRepository.findById(userId).isPresent(); // O método findById retorna um Optional<User>

    }

}
