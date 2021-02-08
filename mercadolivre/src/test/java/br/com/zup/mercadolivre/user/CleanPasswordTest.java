package br.com.zup.mercadolivre.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CleanPasswordTest {

    @Test
    void mustNotAllowToSetPasswordWithLessThan6Characters(){
        assertThrows(IllegalArgumentException.class, () -> {
           CleanPassword cleanPassword = new CleanPassword("12345");
        });
    }
}