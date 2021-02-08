package br.com.zup.mercadolivre.product.productregistration;

import br.com.zup.mercadolivre.product.productcharacteristic.NewProductCharacteristicRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NewProductRequestDtoTest {

    @DisplayName("Must not allow to receive more than one characteristic with the same name")
    @ParameterizedTest
    @MethodSource("testOne")
    void mustNotAllowToReceiveMoreThanOneProductCharacteristicWithTheSameName (int expectedValue, List<NewProductCharacteristicRequestDto> characteristics){
        NewProductRequestDto productRequestDto = new NewProductRequestDto("name", characteristics);
        assertEquals(expectedValue, productRequestDto.getCharacteristicWithTheSameName().size());
    }

    static Stream<Arguments> testOne() {
        return Stream.of(
                Arguments.of(0, List.of()),
                Arguments.of(0, List.of(new NewProductCharacteristicRequestDto("name", "value"))),
                Arguments.of(0, List.of(new NewProductCharacteristicRequestDto("name", "value"), new NewProductCharacteristicRequestDto("name2", "value2"))),
                Arguments.of(1, List.of(new NewProductCharacteristicRequestDto("name", "value"), new NewProductCharacteristicRequestDto("name", "value")))
        );
    }

}