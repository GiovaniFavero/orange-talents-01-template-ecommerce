package br.com.zup.mercadolivre.category;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class NewCategoryRequestDtoTest {

    @Test
    void mustNotAllowToCreateCategoryWithInvalidParentCategoryId() {
        NewCategoryRequestDto categoryRequestDto = new NewCategoryRequestDto("Food", 10L);
    }

}