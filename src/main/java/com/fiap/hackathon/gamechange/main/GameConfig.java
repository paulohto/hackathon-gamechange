package com.fiap.hackathon.gamechange.main;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IGameGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IUserGateway;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.GameRegisterUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.GameAuxMapper;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.GameGatewayAux;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IGameRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class GameConfig {
//    private final IUserGateway userGateway; // Certifique-se de que a variável está declarada
//
//    public GameConfig(IUserGateway userGateway) {
//        this.userGateway = userGateway;
//    }

    @Bean
    public GameRegisterUseCase saveGame(IGameGateway gameGateway){
        return new GameRegisterUseCase(gameGateway);
    }

    @Bean
    IGameGateway gameGateway(MongoTemplate mongoTemplate, IGameRepository gameRepository, GameAuxMapper gameAuxMapper){
        return new GameGatewayAux(mongoTemplate, gameRepository, gameAuxMapper);
    }

    @Bean
    GameAuxMapper gameAuxMapper(){ return new GameAuxMapper(); }

    @Bean
    GameDTOMapper gameDTOMapper(){ return new GameDTOMapper();}
}
