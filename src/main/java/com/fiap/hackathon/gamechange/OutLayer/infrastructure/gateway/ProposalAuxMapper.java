package com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.ProposalDTO;

import java.time.LocalDate;

public class ProposalAuxMapper {

    // Converte uma entidade Proposal para um DTO ProposalDTO
    public ProposalDTO toDTO(
            Proposal proposal,
            String gameOfferedName, String gameRequestedName, String proposerName, String recipientName
    ) {
        return new ProposalDTO(
                proposal.getId(),                        // Mapeia o ID da proposta
                proposal.getGameOfferedId(),            // Mapeia o ID do jogo oferecido
                gameOfferedName,  // Adiciona o nome do jogo oferecido
                proposal.getGameRequestedId(),           // Mapeia o ID do jogo solicitado
                gameRequestedName, // Adiciona o nome do jogo solicitado
                proposal.getProposerId(),                // Mapeia o ID do proponente
                proposerName,  // Adiciona o nome do proponente
                proposal.getRecipientId(),               // Mapeia o ID do destinatário
                recipientName,  // Adiciona o nome do destinatário
                proposal.getStatus().name(),                    // Mapeia o status da proposta
                proposal.getCreatedAt(),                 // Mapeia a data de criação
                proposal.getUpdatedAt()                  // Mapeia a data de atualização
        );
    }


    // Converte um DTO ProposalDTO para uma entidade Proposal
    public Proposal toEntity(ProposalDTO proposalDTO) {
        return new Proposal(
                // proposalDTO.getId(),                 // Mapeia o ID do DTO para a entidade Proposal
                null,                                   // Deixe o ID nulo para ser gerado na persistência
                proposalDTO.getGameOfferedId(),        // Mapeia o ID do jogo oferecido do DTO para a entidade Proposal
                proposalDTO.getGameRequestedId(),       // Mapeia o ID do jogo solicitado do DTO para a entidade Proposal
                proposalDTO.getProposerId(),            // Mapeia o ID do proponente do DTO para a entidade Proposal
                proposalDTO.getRecipientId(),           // Mapeia o ID do destinatário do DTO para a entidade Proposal

                proposalDTO.getStatus() != null ? ProposalStatus.valueOf(proposalDTO.getStatus()) : ProposalStatus.PENDENTE,                LocalDate.now(),                        // Mapeia a data de criação do DTO para a entidade Proposal
                LocalDate.now()                         // Mapeia a data de atualização do DTO para a entidade Proposal
        );
    }
}
