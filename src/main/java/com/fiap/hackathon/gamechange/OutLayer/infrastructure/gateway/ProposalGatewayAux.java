package com.fiap.hackathon.gamechange.OutLayer.infrastructure.gateway;

import com.fiap.hackathon.gamechange.InnerLayer.adapters.gateways.IProposalGateway;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.ProposalStatus;
import com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence.IProposalRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProposalGatewayAux implements IProposalGateway {
    private final MongoTemplate mongoTemplate;
    private final IProposalRepository proposalRepository;
    private final ProposalAuxMapper proposalAuxMapper;

    public ProposalGatewayAux(MongoTemplate mongoTemplate, IProposalRepository proposalRepository, ProposalAuxMapper proposalAuxMapper) {
        this.mongoTemplate = mongoTemplate;
        this.proposalRepository = proposalRepository;
        this.proposalAuxMapper = proposalAuxMapper;
    }

    @Override
    public Proposal saveProposal(Proposal proposal) {
        return proposalRepository.save(proposal);
    }

    @Override
    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    //@Override
    //public Optional<Proposal> getProposalById(String id) {        return Optional.empty();    }

    @Override
    public Optional<Proposal> getProposalById(String id) {
        Optional<Proposal> proposalOpt = proposalRepository.findById(id);
        return proposalOpt;
    }
    ///


    @Override
    public Proposal updateStatus(String proposalId, ProposalStatus status) {
        return null;
    }

    @Override
    public List<Proposal> findByProposerId(String proposerId) {
        return null;
    }

    @Override
    public List<Proposal> findByRecipientId(String recipientId) {
        return null;
    }

    @Override
    public void deleteProposal(String id) {
        proposalRepository.deleteById(id);
    }


}
