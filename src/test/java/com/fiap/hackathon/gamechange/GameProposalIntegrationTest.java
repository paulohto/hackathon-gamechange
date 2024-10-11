package com.fiap.hackathon.gamechange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.AvailabilityStatus;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.GameCondition;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.GameRegisterUseCase;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.ProposalUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.GameController;
import com.fiap.hackathon.gamechange.OutLayer.controllers.ProposalController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameProposalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private GameController gameController;

    @InjectMocks
    private ProposalController proposalController;

    @Mock
    private GameRegisterUseCase gameRegisterUseCase;

    @Mock
    private ProposalUseCase proposalUseCase;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper(); // Inicializa o ObjectMapper
    }

    @Test
    public void testGameAndProposalIntegration() throws Exception {
        // Cria um objeto Game
        Game game = new Game(
                "1",                                // id
                "Game One",                          // title
                "Platform A",                        // platform
                "owner1",                           // ownerId
                GameCondition.NOVO,                 // condition
                AvailabilityStatus.DISPONIVEL,      // availabilityStatus
                LocalDate.now(),                     // createdAt
                LocalDate.now()                      // updatedAt
        );

        // Cria um objeto Proposal
        Proposal proposal = new Proposal(
                "1",                      // id
                "1",                      // gameOfferedId
                "2",                      // gameRequestedId
                "user1",                 // proposerId
                "user2",                 // recipientId
                ProposalStatus.PENDENTE, // status
                LocalDate.now(),         // createdAt
                LocalDate.now()          // updatedAt
        );

        // Simular o comportamento do use case para salvar o game
        when(gameRegisterUseCase.saveGame(any(Game.class))).thenReturn(game);

        // Simular o comportamento do use case para salvar a proposta
       // when(proposalUseCase.saveProposal(any(Proposal.class))).thenReturn(proposal);

        // Enviar requisição para criar o jogo
        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(game)))
                .andExpect(status().isCreated());

        // Enviar requisição para criar a proposta
        mockMvc.perform(post("/proposals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proposal)))
                .andExpect(status().isCreated());
    }
}