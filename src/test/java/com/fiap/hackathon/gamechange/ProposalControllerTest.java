package com.fiap.hackathon.gamechange;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.ProposalUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.ProposalController;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.ProposalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProposalControllerTest {

    @InjectMocks
    private ProposalController proposalController;

    @Mock
    private ProposalUseCase proposalUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProposal() {
        ProposalDTO proposalDTO = new ProposalDTO(
                "1",
                "game1",
                "Game One",
                "game2",
                "Game Two",
                "user1",
                "User One",
                "user2",
                "User Two",
                ProposalStatus.PENDENTE.name(),
                LocalDate.now(),
                LocalDate.now()
        );

        when(proposalUseCase.saveProposal(any(ProposalDTO.class))).thenReturn(proposalDTO);

        ResponseEntity<ProposalDTO> response = proposalController.saveProposal(proposalDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getId());
    }

    @Test
    public void testGetAllProposals() {
        ProposalDTO proposalDTO = new ProposalDTO(
                "1",
                "game1",
                "Game One",
                "game2",
                "Game Two",
                "user1",
                "User One",
                "user2",
                "User Two",
                ProposalStatus.PENDENTE.name(),
                LocalDate.now(),
                LocalDate.now()
        );

        when(proposalUseCase.getAllProposal()).thenReturn(Collections.singletonList(proposalDTO));

        ResponseEntity<List<ProposalDTO>> response = proposalController.getAllProposals();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetProposalById() {
        ProposalDTO proposalDTO = new ProposalDTO(
                "1",
                "game1",
                "Game One",
                "game2",
                "Game Two",
                "user1",
                "User One",
                "user2",
                "User Two",
                ProposalStatus.PENDENTE.name(),
                LocalDate.now(),
                LocalDate.now()
        );

        when(proposalUseCase.getProposalById("1")).thenReturn(Optional.of(proposalDTO));

        ResponseEntity<ProposalDTO> response = proposalController.getProposalById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getId());
    }

    @Test
    public void testGetProposalById_NotFound() {
        when(proposalUseCase.getProposalById("2")).thenReturn(Optional.empty());

        ResponseEntity<ProposalDTO> response = proposalController.getProposalById("2");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProposal() {
        doNothing().when(proposalUseCase).deleteProposal("1");

        ResponseEntity<Void> response = proposalController.deleteProposal("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testUpdateProposalStatus() {
        ProposalDTO proposalDTO = new ProposalDTO(
                "1",
                "game1",
                "Game One",
                "game2",
                "Game Two",
                "user1",
                "User One",
                "user2",
                "User Two",
                ProposalStatus.ACEITA.name(),
                LocalDate.now(),
                LocalDate.now()
        );

        when(proposalUseCase.updateProposalStatus("user2", "1", ProposalStatus.ACEITA)).thenReturn(Optional.of(proposalDTO));

        ResponseEntity<ProposalDTO> response = proposalController.updateProposalStatus("user2", "1", ProposalStatus.ACEITA);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ACEITA", response.getBody().getStatus());
    }

    @Test
    public void testUpdateProposalStatus_NotFound() {
        when(proposalUseCase.updateProposalStatus("user2", "2", ProposalStatus.ACEITA)).thenReturn(Optional.empty());

        ResponseEntity<ProposalDTO> response = proposalController.updateProposalStatus("user2", "2", ProposalStatus.ACEITA);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}