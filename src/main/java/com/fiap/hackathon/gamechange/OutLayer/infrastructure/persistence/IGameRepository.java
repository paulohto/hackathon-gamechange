package com.fiap.hackathon.gamechange.OutLayer.infrastructure.persistence;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IGameRepository extends MongoRepository<Game, String> {
    List<Game> findByOwnerId(String ownerId);

    @Query("SELECT g.name FROM Game g WHERE g.id = :gameId")
    String findGameNameById(Long gameId);
}
