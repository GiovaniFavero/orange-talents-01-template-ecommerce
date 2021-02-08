package br.com.zup.mercadolivre.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMv;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    @DisplayName("Must create a user with login and password!")
    void mustCreateAUserWithLoginAndPassword() throws Exception{
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto("user@email.com", "123456");

        this.mockMv.perform(post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(newUserRequestDto)))
                    .andExpect(status().isOk());

        User user = (User) entityManager.createQuery("select u from User u where u.login = :login")
                .setParameter("login", "user@gmail.com").getSingleResult();

        assertAll(() -> assertNotNull(user),
                  () -> assertEquals(user.getEmail(), "user@gmail.com"));
    }

    @Test
    void mustNotCreateAUserWithBlankLogin() throws Exception{
        NewUserRequestDto newUserRequestDto = new NewUserRequestDto("", "123456");

        this.mockMv.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(newUserRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
              //  .andExpect(jsonPath("").value());
    }

}