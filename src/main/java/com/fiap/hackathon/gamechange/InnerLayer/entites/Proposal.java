package com.fiap.hackathon.gamechange.InnerLayer.entites;

import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Proposal {
    //Atributos:
    private final String id;
    private final String gameOfferedId; // O ID ou título do jogo que o proponente está oferecendo na troca.
    private final String gameRequestedId; // O ID ou título do jogo que o proponente deseja receber em troca.
    private final String proposerId; // O ID do usuário que está fazendo a proposta.
    private final String recipientId; // O ID do usuário que está recebendo a proposta.
    private final ProposalStatus status;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public Proposal(
            String id, String gameOfferedId, String
            gameRequestedId, String proposerId, String recipientId,
            ProposalStatus status, LocalDate createdAt, LocalDate updatedAt
    ) {
        this.id = id;
        this.gameOfferedId = gameOfferedId;
        this.gameRequestedId = gameRequestedId;
        this.proposerId = proposerId;
        this.recipientId = recipientId;
        this.status = status != null ? status : ProposalStatus.PENDENTE;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Método para criar uma nova instância com o novo status
    public Proposal withStatus(ProposalStatus newStatus) {
        return new Proposal(
                this.id,
                this.gameOfferedId,
                this.gameRequestedId,
                this.proposerId,
                this.recipientId,
                newStatus,  // Atualiza o status
                this.createdAt,
                LocalDate.now() // Atualiza o campo updatedAt para refletir a modificação
        );
    }

    //Getter:
    public String getId() {
        return id;
    }

    public String getGameOfferedId() {
        return gameOfferedId;
    }

    public String getGameRequestedId() {
        return gameRequestedId;
    }

    public String getProposerId() {
        return proposerId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }


}
