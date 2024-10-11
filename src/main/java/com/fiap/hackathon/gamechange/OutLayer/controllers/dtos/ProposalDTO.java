package com.fiap.hackathon.gamechange.OutLayer.controllers.dtos;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class ProposalDTO {

    // Atributos
    @Id
    private String id;
    private String gameOfferedId; // O ID do jogo que o proponente está oferecendo

    private String gameOfferedTitle;  // Novo campo

    private String gameRequestedId; // O ID do jogo que o proponente deseja receber

    private String gameRequestedTitle; // Novo campo

    private String proposerId; // O ID do usuário que está fazendo a proposta

    private String proposerName; // Novo campo

    private String recipientId; // O ID do usuário que está recebendo a proposta

    private String recipientName; // Novo campo

    private String status; // Status da proposta (pendente, aceita, recusada, etc.)
    private LocalDate createdAt;
    private LocalDate updatedAt;

    // Construtor com todos os parâmetros
    public ProposalDTO(
        String id,
        String gameOfferedId, String gameOfferedTitle,
        String gameRequestedId, String gameRequestedTitle,
        String proposerId, String proposerName,
        String recipientId, String recipientName,
        String status, LocalDate createdAt, LocalDate updatedAt
    ) {
        this.id = id;
        this.gameOfferedId = gameOfferedId;
        this.gameOfferedTitle = gameOfferedTitle; // NOVO
        this.gameRequestedId = gameRequestedId;
        this.gameRequestedTitle = gameRequestedTitle; // NOVO
        this.proposerId = proposerId;
        this.proposerName = proposerName; // NOVO
        this.recipientId = recipientId;
        this.recipientName = recipientName; // NOVO
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameOfferedId() {
        return gameOfferedId;
    }

    public void setGameOfferedId(String gameOfferedId) {
        this.gameOfferedId = gameOfferedId;
    }

    public String getGameRequestedId() {
        return gameRequestedId;
    }

    public void setGameRequestedId(String gameRequestedId) {
        this.gameRequestedId = gameRequestedId;
    }

    public String getProposerId() {
        return proposerId;
    }

    public void setProposerId(String proposerId) {
        this.proposerId = proposerId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    // NOVOS
    public String getGameOfferedTitle() { return gameOfferedTitle; }
    public void setGameOfferedName(String gameOfferedTitle) { this.gameOfferedTitle = gameOfferedTitle; }

    public String getGameRequestedTitle() { return gameRequestedTitle; }
    public void setGameRequestedName(String gameRequestedTitle) { this.gameRequestedTitle = gameRequestedTitle; }

    public String getProposerName() { return proposerName; }
    public void setProposerName(String proposerName) { this.proposerName = proposerName; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

}
