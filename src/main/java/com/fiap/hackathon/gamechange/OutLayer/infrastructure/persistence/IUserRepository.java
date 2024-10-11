package com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence;

import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.security.UserDetailsDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {

    //@Query("SELECT u.name FROM User u WHERE u.id = :userId")
    //Optional<String> findUserNameById(Long userId);

    // Usando @Query para encontrar usuário pelo nome de usuário
    //@Query("SELECT u FROM User u WHERE u.name = :name")

//    @Query("{ 'login': ?0 }")
//    Optional<User> findByUsername(String login);

    //@Query("{ 'password': ?0 }")
    //Optional<User> findByPassword(String password);

    //UserDetails findByLogin(String login);
    User findByLogin(String login); // funciona // Altera o retorno para User

}
