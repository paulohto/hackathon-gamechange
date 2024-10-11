package com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;

import java.util.List;
import java.util.Optional;

public interface IProposalGateway {

    // Método para criar uma nova proposta de troca
    Proposal saveProposal(Proposal proposal);

    // Método para buscar uma proposta pelo ID
    Optional<Proposal> getProposalById(String id);

    // Método para atualizar o status de uma proposta
    Proposal updateStatus(String proposalId, ProposalStatus status);

    // Método para listar todas as propostas de um usuário (propostas criadas por ele)
    List<Proposal> findByProposerId(String proposerId);

    // Lista todos os Proposals: OK
    List<Proposal> getAllProposals();

    // Método para listar todas as propostas recebidas por um usuário
    List<Proposal> findByRecipientId(String recipientId);

    // Método para excluir uma proposta (cancelar uma proposta de troca)
    void deleteProposal(String id);

}
