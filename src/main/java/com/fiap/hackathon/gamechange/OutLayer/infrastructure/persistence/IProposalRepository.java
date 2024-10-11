package com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Proposal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProposalRepository extends MongoRepository<Proposal, String> {
}
