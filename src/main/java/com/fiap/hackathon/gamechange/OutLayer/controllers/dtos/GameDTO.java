package com.fiap.hackathon.gamechange.OutLayer.controllers.dtos;

import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.AvailabilityStatus;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.GameCondition;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "games")
public class GameDTO {

    // Atributos do DTO:
    @Id
    private String id;
    private String title;
    private String platform;
    private String ownerId;
    private GameCondition condition; // (novo/usado)
    private AvailabilityStatus availabilityStatus; // (disponível/indisponível)
    private LocalDate createdAt;
    private LocalDate updatedAt;

    // Construtor:
    public GameDTO(
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

    // Getters e Setters:
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public GameCondition getCondition() {
        return condition;
    }

    public void setCondition(GameCondition condition) {
        this.condition = condition;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
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


}
