package com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IUserGateway;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService /*implements UserDetailsService*/ {

//    private final IUserRepository userRepository; // Certifique-se de ter um repositório de usuários
//    private final IUserGateway userGateway;
//
//    public CustomUserDetailsService(IUserRepository userRepository, IUserGateway userGateway) {
//        this.userRepository = userRepository;
//        this.userGateway = userGateway;
//    }
//
//
//
//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        // Carregue o usuário do banco de dados
//        System.out.println("Buscando usuário: " + login);
//
//        // Usando o método findByUsername e map
//        return userRepository.findByUsername(login) // Altere aqui para 'login'
//                .map(user -> new org.springframework.security.core.userdetails.User(
//                        //user.getName(), // Mantenha ou altere conforme necessário
//                        user.getLogin(), // Altere para 'login'
//                        user.getPassword(),
//                        new ArrayList<>()
//                ))
//                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
//    }
//
//    // Método adicional para retornar User diretamente
//    public Optional<User> findUserByUsername(String login) { // Altere aqui para 'login'
//        return userRepository.findByUsername(login); // Altere aqui para 'login'
//    }

}
