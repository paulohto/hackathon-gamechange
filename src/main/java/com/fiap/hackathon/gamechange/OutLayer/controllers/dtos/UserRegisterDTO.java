package com.fiap.hackathon.gamechange.OutLayer.controllers.dtos;

import com.fiap.hackathon.gamechange.InnerLayer.entites.Game;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.TradePreference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//@Document(collection = "users")
public class UserRegisterDTO {

    @Id
    private String id; // TESTE
    private String login;
    private String email;
    private String password; // Para registro
    private TradePreference preferences; // (presencial ou correio)
    private List<GameDTO> gameCollection; // Pode ser uma lista de IDs de jogos ou simplificações dos jogos
    private String address;

    public UserRegisterDTO(String id, String login, /*String name,*/ String email, String password, TradePreference preferences, List<GameDTO> gameCollection, String address) {
        this.id = id; // teste
        this.login = login;
        this.email = email;
        this.password = password;
        this.preferences = preferences;
      //  this.gameCollection = gameCollection /*!= null ? gameCollection : new ArrayList<>(); // Inicializa se nulo*/;
        this.gameCollection = gameCollection != null ? gameCollection : new ArrayList<>();
        this.address = address;
    }

    public String getId() { return id; }
    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public TradePreference getPreferences() {
        return preferences;
    }

    public List<GameDTO> getGameCollection() {
        return gameCollection;
    }
    public String getAddress() {
        return address;
    }

    //SET
    public void setId(String id) {  this.id = id; }
    public void setLogin(String login) {  this.login = login; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPreferences(TradePreference preferences) {
        this.preferences = preferences;
    }

    public void setGameCollection(List<GameDTO> gameCollection) {
        this.gameCollection = gameCollection;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
