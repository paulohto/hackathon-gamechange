package com.fiap.hackathon.gamechange.main;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IGameGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IUserGateway;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.UserRegisterUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.UserAuxMapper;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.UserGatewayAux;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserConfig {
    private final IGameGateway gameGateway; // Certifique-se de que a variável está declarada

    public UserConfig(IGameGateway gameGateway) {
        this.gameGateway = gameGateway; // Inicialize a variável através do construtor ou injeção
    }

    @Bean
    public UserRegisterUseCase saveUser(IUserGateway userGateway, BCryptPasswordEncoder passwordEncoder){
        return new UserRegisterUseCase(userGateway, gameGateway, passwordEncoder);
    }
    ///

//    @Bean
//    public SomeBean someBean() {
//        return new SomeBean(gameGateway); // Utilize a variável corretamente
//    }

    ///

    @Bean
    IUserGateway userGateway(MongoTemplate mongoTemplate, IUserRepository userRepository, UserAuxMapper userAuxMapper){
        return new UserGatewayAux(mongoTemplate, userRepository, userAuxMapper, gameGateway);
    }

    @Bean
    UserAuxMapper userAuxMapper(){ return new UserAuxMapper(); }

    @Bean
    UserDTOMapper userDTOMapper(){ return new UserDTOMapper();}

}
