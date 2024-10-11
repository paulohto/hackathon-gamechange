package com.fiap.hackathon.gamechange.OutLayer.controllers.dtos;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;

import java.time.LocalDate;

public class ProposalDTOMapper {

    // Converte a entidade Proposal para ProposalDTO
    public static ProposalDTO toDTO(
            Proposal proposal,
            String gameOfferedTitle, String gameRequestedTitle, String proposerName, String recipientName
    ) {
        return new ProposalDTO(
                proposal.getId(),
                proposal.getGameOfferedId(),
                gameOfferedTitle,  // Adiciona o nome do jogo oferecido
                proposal.getGameRequestedId(),
                gameRequestedTitle, // Adiciona o nome do jogo solicitado
                proposal.getProposerId(),
                proposerName,  // Adiciona o nome do proponente
                proposal.getRecipientId(),
                recipientName,  // Adiciona o nome do destinatário
                proposal.getStatus().name(), // Converte o enum para String
                proposal.getCreatedAt(),
                proposal.getUpdatedAt()
        );
    }

    // Converte o ProposalDTO para a entidade Proposal
    public static Proposal toEntity(ProposalDTO proposalDTO) {
        return new Proposal(
                // Atribui null ao ID, pois será gerado pelo sistema
                null,
                proposalDTO.getGameOfferedId(),
                proposalDTO.getGameRequestedId(),
                proposalDTO.getProposerId(),
                proposalDTO.getRecipientId(),

                //ProposalStatus.valueOf(proposalDTO.getStatus()),

                // Converte o String do status para o enum ProposalStatus
                proposalDTO.getStatus() != null ? ProposalStatus.valueOf(proposalDTO.getStatus()) : ProposalStatus.PENDENTE,

                LocalDate.now(), // createdAt como a data atual
                LocalDate.now()  // updatedAt como a data atual
        );
    }

}
