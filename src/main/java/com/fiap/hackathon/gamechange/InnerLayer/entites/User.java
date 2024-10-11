package com.fiap.hackathon.gamechange.InnerLayer.entites;

import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.TradePreference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class User  {

    //Atributos:
    private final String id;
    private final String login;  // Deve haver esse campo, ou outro nome, para mapear na consulta
    //private final String name;
    private final String email;
    private final String password;
    private final TradePreference preferences; // (presencial ou correio)
    private final List<Game> gameCollection; // Coleção de jogos do usuário
    private final String address;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;

    public User(
            String id, String login,
            /*String name,*/ String email,
            String password, TradePreference preferences,
            List<Game> gameCollection, String address,
            LocalDate createdAt, LocalDate updatedAt
    ) {
        this.id = id;
        this.login = login;
        //this.name = name;
        this.email = email;
        this.password = password;
        this.preferences = preferences;
        this.gameCollection = gameCollection;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters:
    public String getId() {
        return id;
    }
    public String getLogin() { return login; }

    public String getUsername() {
        return login; // Mapeia o login para o método getUsername
    }

    //public String getName() { return name; }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public TradePreference getPreferences() {
        return preferences;
    }

    public List<Game> getGameCollection() {
        return gameCollection;
    }
    public String getAddress() {
        return address;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }


}
