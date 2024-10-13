package com.fiap.hackathon.gamechange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.fiap.hackathon.gamechange.InnerLayer.entites.User;
import com.fiap.hackathon.gamechange.InnerLayer.entites.enums.TradePreference;
import com.fiap.hackathon.gamechange.InnerLayer.usercases.UserRegisterUseCase;
import com.fiap.hackathon.gamechange.OutLayer.controllers.UserController;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserDTOMapper;
import com.fiap.hackathon.gamechange.OutLayer.controllers.dtos.UserRegisterDTO;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Injetando o MockMvc
    @Autowired
    private ObjectMapper objectMapper; // Para converter objetos para JSON

    @Mock
    private UserRegisterUseCase userRegisterUseCase;
    @Mock
    private UserDTOMapper userDTOMapper;
    @InjectMocks
    private UserController userController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void saveUser_ShouldReturnUserDTO() throws Exception {
        // Arrange
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                "userId",                  // id (adicionado o id)
                "Security4",               // login
                "user@test.com",            // email
                "s123",                     // password
                TradePreference.CORREIO,    // preferences (ou o valor correto que você usa)
                new ArrayList<>(),          // gameCollection, lista de jogos vazia ou preenchida
                "Rua Teste, 123"            // address

        );
       // User userEntity = userDTOMapper.toEntity(userRegisterDTO);
        User userEntity = new User(
                "userId",                  // id
                "Security4",                // login
                "user@test.com",            // email
                "s123",                     // password
                TradePreference.CORREIO,    // preferences
                new ArrayList<>(),          // gameCollection
                "Rua Teste, 123",           // address
                LocalDate.now(),            // createdAt
                LocalDate.now()             // updatedAt
        );

        // Mapeie o User para UserRegisterDTO
        UserRegisterDTO mappedUserRegisterDTO = userDTOMapper.toRegisterDTO(userEntity);

        // Mock the behavior
//        when(userDTOMapper.toEntity(userRegisterDTO)).thenReturn(userEntity);
//        when(userRegisterUseCase.saveUser(any(User.class))).thenReturn(userEntity);

        // Mock the behavior: Mock getUserById to return Optional<User>
        Optional<UserRegisterDTO> optionalUserRegisterDTO  = Optional.of(mappedUserRegisterDTO);
        when(userRegisterUseCase.saveUser(any(User.class))).thenReturn(userEntity);


        // Chame o endpoint e verifique a resposta
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRegisterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("Security4"))
                .andExpect(jsonPath("$.email").value("user@test.com"));

        // Act
        //ResponseEntity<UserRegisterDTO> response = userController.saveUser(userRegisterDTO);
        // Assert: Verifique que o método saveUser foi chamado corretamente
        verify(userRegisterUseCase, times(1)).saveUser(any(User.class));
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        String userId = "123";
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(
                "userId",                  // id (adicionado o id)
                "Security4",               // login
                "user@test.com",            // email
                "s123",                     // password
                TradePreference.CORREIO,    // preferences (ou o valor correto que você usa)
                new ArrayList<>(),          // gameCollection, lista de jogos vazia ou preenchida
                "Rua Teste, 123"            // address
        );

        when(userRegisterUseCase.getUserById(eq(userId))).thenReturn(Optional.of(userRegisterDTO));

        // Act
        ResponseEntity<UserRegisterDTO> response = userController.getUserById(userId);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userRegisterDTO, response.getBody());
    }

    @Test
    void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() {
        // Arrange
        String userId = "123";

        when(userRegisterUseCase.getUserById(eq(userId))).thenReturn(Optional.empty());
        // Act
        ResponseEntity<UserRegisterDTO> response = userController.getUserById(userId);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        List<UserRegisterDTO> users = List.of(new UserRegisterDTO(
                "userId",                  // id (adicionado o id)
                "Security4",               // login
                "user@test.com",            // email
                "s123",                     // password
                TradePreference.CORREIO,    // preferences (ou o valor correto que você usa)
                new ArrayList<>(),          // gameCollection, lista de jogos vazia ou preenchida
                "Rua Teste, 123"            // address
        ), new UserRegisterDTO(
                "userId",                  // id (adicionado o id)
                "Security5",               // login
                "user5@test.com",            // email
                "s123",                     // password
                TradePreference.CORREIO,    // preferences (ou o valor correto que você usa)
                new ArrayList<>(),          // gameCollection, lista de jogos vazia ou preenchida
                "Rua Teste, 123"            // address
        ));

        when(userRegisterUseCase.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<UserRegisterDTO>> response = userController.getAllUsers();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userRegisterUseCase, times(1)).getAllUsers();
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        // Arrange
        String userId = "123";

        doNothing().when(userRegisterUseCase).deleteUser(eq(userId));

        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userRegisterUseCase, times(1)).deleteUser(eq(userId));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        // Arrange
        String userId = "123";
        UserRegisterDTO updatedUserDTO = new UserRegisterDTO(
                "userId",                  // id (adicionado o id)
                "Security4",               // login
                "user@test.com",            // email
                "s123",                     // password
                TradePreference.CORREIO,    // preferences (ou o valor correto que você usa)
                new ArrayList<>(),          // gameCollection, lista de jogos vazia ou preenchida
                "Rua Teste, 123"            // address
        );
        User updatedUser = new User(
                "userId",                  // id
                "Security4",                // login
                "user@test.com",            // email
                "s123",                     // password
                TradePreference.CORREIO,    // preferences
                new ArrayList<>(),          // gameCollection
                "Rua Teste, 123",           // address
                LocalDate.now(),            // createdAt
                LocalDate.now()             // updatedAt
        );

        when(userRegisterUseCase.updateUser(eq(userId), any(UserRegisterDTO.class))).thenReturn(updatedUser);

        // Act
        User result = userController.updateUser(userId, updatedUserDTO);
        // Assert
        assertEquals(updatedUser, result);
        verify(userRegisterUseCase, times(1)).updateUser(eq(userId), any(UserRegisterDTO.class));
    }
}
