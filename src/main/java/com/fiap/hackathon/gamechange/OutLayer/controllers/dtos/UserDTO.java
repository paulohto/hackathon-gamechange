package com.fiap.hackathon.gamechange.OutLayer.controllers.dtos;

import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.TradePreference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

// @Document(collection = "users")
public class UserDTO {

    // Atributos do DTO:
    @Id
    private String id;
    //private String name;
    private String login;
    private String email;
    private TradePreference preferences;
    private List<GameDTO> gameCollection; // Pode ser uma lista de IDs de jogos ou simplificações dos jogos
    private String address;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    // Construtor:
    public UserDTO(
            String id, /*String name,*/ String login, String email,
            TradePreference preferences, List<GameDTO> gameCollection,
            String address, LocalDate createdAt, LocalDate updatedAt
    ) {
        this.id = id;
        //this.name = name;
        this.login = login;
        this.email = email;
        this.preferences = preferences;
        this.gameCollection = gameCollection;
        this.address = address;
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

//    public String getName() {return name; }
//    public void setName(String name) {this.name = name;}


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TradePreference getPreferences() {
        return preferences;
    }

    public void setPreferences(TradePreference preferences) {
        this.preferences = preferences;
    }

    public List<GameDTO> getGameCollection() {
        return gameCollection;
    }

    public void setGameCollection(List<GameDTO> gameCollection) {
        this.gameCollection = gameCollection;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
