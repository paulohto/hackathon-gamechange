package com.fiap.hackathon.gamechange.InnerLayer.usercases;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IGameGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IUserGateway;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserRegisterUseCase {
    private final IUserGateway userGateway;
    private final IGameGateway gameGateway;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserRegisterUseCase(IUserGateway userGateway, IGameGateway gameGateway, BCryptPasswordEncoder passwordEncoder) {
        this.userGateway = userGateway;
        this.gameGateway = gameGateway;
        this.passwordEncoder = passwordEncoder;
    }

    //CRIA UM USER: OK
    public User saveUser(User user){

        // Criptografa a senha
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        // Cria um novo objeto User com a senha criptografada
        User userWithEncodedPassword = new User(

                user.getId(),
                user.getLogin(), // TESTE
                //user.getName(),
                user.getEmail(),
                encodedPassword,            // Criptografar a senha
                user.getPreferences(),      // Preferências de troca (presencial ou correio)
                user.getGameCollection(),   // Coleção de jogos do usuário
                user.getAddress(),          // Endereço
                user.getCreatedAt(),        // Data de criação
                user.getUpdatedAt()         // Data de atualização
        );
        return userGateway.saveUser(userWithEncodedPassword);

        //return userGateway.saveUser(user);
    }

    //BUSCA UM USER POR ID E CONVERTE PARA USERREGISTERDTO: OK
    public Optional<UserRegisterDTO> getUserById(String userId) {
        Optional<User> userOpt = userGateway.getUserById(userId);
        return userOpt.map(UserDTOMapper::toRegisterDTO); // Converte para UserRegisterDTO se encontrado
    }

    // BUSCA LISTA COM TODOS OS USUARIOS: OK
    public List<UserRegisterDTO> getAllUsers() {
        List<User> users = userGateway.getAllUsers();  // Pega a lista de usuários
        // Converte cada User para UserRegisterDTO
        return users.stream()
                .map(UserDTOMapper::toRegisterDTO)  // Usa o mapper para converter cada User
                .collect(Collectors.toList());  // Retorna uma lista de UserRegisterDTO
    }


    // ATUALIZA USER POR ID: OK
//    public User updateUser(String userId, UserRegisterDTO userAtualizado){
//        return userGateway.updateUser(userId, userAtualizado);
//    }

    // ATUALIZA USER POR ID: OK
    public User updateUser(String userId, UserRegisterDTO userAtualizado) {
        // Busque o usuário atual no banco de dados
        Optional<User> existingUserOpt = userGateway.getUserById(userId);

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


    // DELETA UM USER POR ID: OK
    public void deleteUser(String userId){
        userGateway.deleteUser(userId);
    }

    ///

}
