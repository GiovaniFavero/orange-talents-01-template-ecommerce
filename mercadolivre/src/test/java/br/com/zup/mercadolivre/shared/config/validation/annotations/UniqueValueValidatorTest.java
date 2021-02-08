package br.com.zup.mercadolivre.shared.config.validation.annotations;

import br.com.zup.mercadolivre.user.NewUserRequestDto;
import br.com.zup.mercadolivre.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

class UniqueValueValidatorTest {

    @Test
    void mustNotAllowMoreThanAttributeWithTheSameValue() {
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        UniqueValueValidator validator = new UniqueValueValidator();

        Object target = new NewUserRequestDto("email@email.com", "password");
        Errors errors = new BeanPropertyBindingResult(target, "test");
    }

}