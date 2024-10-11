package com.fiap.hackathon.gamechange.InnerLayer.entites;

import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.AvailabilityStatus;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.GameCondition;
import java.time.LocalDate;

public class Game {

    //Atributos:
    private final String id;
    private final String title;
    private final String platform;
    private final String ownerId;
    private final GameCondition condition; // (novo/usado),
    private final AvailabilityStatus availabilityStatus; // (disponível/indisponível),
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public Game(
            String id,
            String title, String platform, String ownerId,
            GameCondition condition, AvailabilityStatus availabilityStatus,
            LocalDate createdAt, LocalDate updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.platform = platform;
        this.ownerId = ownerId;
        this.condition = condition;
        this.availabilityStatus = availabilityStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Método para criar uma nova instância com o status de disponibilidade atualizado
    public Game withAvailabilityStatus(AvailabilityStatus newStatus) {
        return new Game(this.id, this.title, this.platform, this.ownerId, this.condition,
                newStatus, this.createdAt, LocalDate.now()); // Atualiza a data de modificação
    }

    //Getter:
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getPlatform() {
        return platform;
    }
    public String getOwnerId() {
        return ownerId;
    }
    public GameCondition getCondition() {
        return condition;
    }
    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}
