package com.fiap.hackathon.gamechange.InnerLayer.entites.enums;

// Enum para o status de disponibilidade do jogo
public enum AvailabilityStatus {
    DISPONIVEL,        // O jogo está disponível para troca
    INDISPONIVEL,      // O jogo não está disponível no momento
    EM_NEGOCIACAO,    // O jogo está em negociação para troca
    TROCADO            // O jogo foi trocado e não está mais disponível
}
