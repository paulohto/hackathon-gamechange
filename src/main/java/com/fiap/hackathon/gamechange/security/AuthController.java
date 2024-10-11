package com.fiap.hackathon.gamechange.security;

import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;
import com.fiap.hackathon.gamechange.security.records.AuthenticationDTO;
import com.fiap.hackathon.gamechange.security.records.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth") // Prefixo para rotas de autenticação
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final IUserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, /*LoginUserUseCaseXXX loginUserUseCase,*/ IUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        logger.info("Tentando autenticar o usuário: {}", data.login());
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            // Verifica se o principal é do tipo UserDetailsDTO
            UserDetailsDTO userDetails = (UserDetailsDTO) auth.getPrincipal();
            // Gera o token usando o UserDetailsDTO
            var token = tokenService.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponseDTO(token));

        } catch (Exception e) {
            // Você pode retornar um status específico ou uma mensagem
            logger.info("Tentando autenticar o usuário: {}", data.login());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Valid UserRegisterDTO data){
//        if(this.userRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
//        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
//        User newUser = new User(data.login(), encryptedPassword, data.role());
//        this.userRepository.save(newUser);
//        return ResponseEntity.ok().build();
//    }


}
