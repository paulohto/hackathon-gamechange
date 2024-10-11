package com.fiap.hackathon.gamechange.InnerLayer.usercases;

import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.CustomUserDetailsService;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginUserUseCase {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository; // Repository para buscar usuários

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUser;

//    public String login(String username, String password) {
//
//    }

//        // Tenta autenticar o usuário
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//
//        // Se a autenticação for bem-sucedida, armazena o contexto de segurança
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Você pode gerar um token JWT aqui se precisar
//        return "Login bem-sucedido"; // Retorne uma mensagem ou token apropriado


        //--------------------------------------------------------------//

        // Buscar o usuário pelo nome diretamente
       //Optional<User> userOpt = customUser.findUserByUsername(username);

//        Optional<User> userOpt = customUser.findUserByUsername(username);
//
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//
//            // Verificar se a senha fornecida corresponde à senha criptografada no banco
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                return "Login bem-sucedido!";
//            } else {
//                return "Senha incorreta!";
//            }
//        } else {
//            return "Usuário não encontrado!";
//        }
//    }
}
