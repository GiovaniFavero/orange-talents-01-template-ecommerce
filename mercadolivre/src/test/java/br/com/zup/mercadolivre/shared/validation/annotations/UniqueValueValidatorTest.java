package br.com.zup.mercadolivre.shared.validation.annotations;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
class UniqueValueValidatorTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void mustNotAllowMoreThanAttributeWithTheSameValue() {
        /*User user = new User("email@email.com", new CleanPassword("123456"));
        entityManager.persist(user);

        User newUser = new User("email@email.com", new CleanPassword("654321"));

        UniqueValueValidator validator = new UniqueValueValidator();
        validator.initializeTest("email", User.class);*/


    }

}