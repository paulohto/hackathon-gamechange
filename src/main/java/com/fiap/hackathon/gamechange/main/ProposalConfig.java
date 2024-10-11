package com.fiap.hackathon.gamechange.main;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IGameGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IProposalGateway;
import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IUserGateway;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.ProposalUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.ProposalDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.GameAuxMapper;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.GameGatewayAux;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.ProposalAuxMapper;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway.ProposalGatewayAux;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IGameRepository;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IProposalRepository;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ProposalConfig {

    @Bean
    public ProposalUseCase proposalUseCase(
            IProposalGateway proposalGateway,
            IGameGateway gameGateway,
            IGameRepository gameRepository,
            IUserRepository userRepository,
            IUserGateway userGateway
    ) {
        return new ProposalUseCase(proposalGateway, gameGateway, gameRepository, userRepository, userGateway); // Passando todos os três gateways
    }

    @Bean
    public IProposalGateway proposalGateway(MongoTemplate mongoTemplate, IProposalRepository proposalRepository, ProposalAuxMapper proposalAuxMapper) {
        return new ProposalGatewayAux(mongoTemplate, proposalRepository, proposalAuxMapper);
    }

    @Bean
    public ProposalAuxMapper proposalAuxMapper() {
        return new ProposalAuxMapper(); // Certifique-se de que está correto
    }

    @Bean
    public ProposalDTOMapper proposalDTOMapper() {
        return new ProposalDTOMapper(); // Certifique-se de que está correto
    }

    @Bean(name = "proposalGameGateway") // Renomeando o bean
    public IGameGateway gameGateway(MongoTemplate mongoTemplate, IGameRepository gameRepository, GameAuxMapper gameAuxMapper) {
        return new GameGatewayAux(mongoTemplate, gameRepository, gameAuxMapper);
    }

}
