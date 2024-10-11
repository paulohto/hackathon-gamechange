package com.fiap.hackathon.gamechange.InnerLayer.entites;

import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    //Atributos: id, userId1, userId2, tradeProposalId, messages[], createdAt

    private final String id;
    private final String userId1;
    private final String userId2;
    private final String tradeProposalId; // O ID da proposta de troca associada ao chat.
    private final List<String> messages; // Usando List para melhor gerenciamento
    private final LocalDate createdAt;

    public Chat(
            String id, String userId1, String userId2,
            String tradeProposalId, List<String> messages,
            LocalDate createdAt
    ) {
        this.id = id;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.tradeProposalId = tradeProposalId;
        this.messages = new ArrayList<>(); // Inicializa a lista de mensagens
        this.createdAt = createdAt;
    }

    // Métodos para adicionar mensagens e getters...
    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    //Outros Getter:
    public String getId() {
        return id;
    }

    public String getUserId1() {
        return userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public String getTradeProposalId() {
        return tradeProposalId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
