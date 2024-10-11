package com.fiap.hackathon.gamechange.OutLayer.controllers;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.ProposalUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.GameDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.ProposalDTO;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.ProposalDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proposals")
public class ProposalController {
    private final ProposalUseCase proposalUseCase;

    public ProposalController(ProposalUseCase proposalUseCase) {
        this.proposalUseCase = proposalUseCase;
    }

    // SALVA/CRIA UM PROPOSAL: OK
    @PostMapping
    public ResponseEntity<ProposalDTO> saveProposal(@RequestBody ProposalDTO proposalDTO) {
        try {
            // Chama o use case para salvar a proposta
            ProposalDTO savedProposalDTO = proposalUseCase.saveProposal(proposalDTO);
            return ResponseEntity.ok(savedProposalDTO); // Retorna a proposta salva com status 200
        } catch (IllegalArgumentException e) {
            // Retorna um erro 400 em caso de argumentos inválidos
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            // Retorna um erro genérico 500 em caso de outras exceções
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/get-all-proposals")
    public ResponseEntity<List<ProposalDTO>> getAllProposals() {
        List<ProposalDTO> proposals = proposalUseCase.getAllProposal();
        return ResponseEntity.ok(proposals);
    }

    // BUSCA PROPOSAL POR ID: OK
    @GetMapping("/{id}")
    public ResponseEntity<ProposalDTO> getProposalById(@PathVariable String id){
        Optional<ProposalDTO> proposalDTO = proposalUseCase.getProposalById(id);
        return proposalDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // DELETA UM GAME POR ID: OK
    @DeleteMapping("/delete-proposal/{id}")
    public ResponseEntity<Void> deleteProposal(@PathVariable String id){
        proposalUseCase.deleteProposal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Retorna 204 No Content após a exclusão
    }

    //
    @PatchMapping("/accept-status")
    public ResponseEntity<ProposalDTO> updateProposalStatus(
            @RequestParam String recipientId,
            @RequestParam String proposalId,
            @RequestParam ProposalStatus status
    ) {
        Optional<ProposalDTO> updatedProposalOpt = proposalUseCase.updateProposalStatus(recipientId, proposalId, status);
        return updatedProposalOpt
                .map(ResponseEntity::ok) // Retorna 200 OK com a proposta atualizada
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Retorna 404 se não encontrado
    }

}
