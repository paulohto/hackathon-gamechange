package com.fiap.hackathon.gamechange.security;

import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository; // Mudando para IUserRepository

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username); // Busca o usuário pelo login
        if (user == null) {
            throw new UsernameNotFoundException("User not found"); // Lança exceção se o usuário não for encontrado
        }

        // Retorna UserDetailsDTO mapeado
        return new UserDetailsDTO(
                user.getLogin(),
                user.getPassword(),
                true, // Supondo que o usuário está ativo
                true,
                true,
                true,
                null // ou as authorities, se necessário
        );
    }

}