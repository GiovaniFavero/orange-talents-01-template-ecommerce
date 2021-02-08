package br.com.zup.mercadolivre.product;

import br.com.zup.mercadolivre.category.Category;
import br.com.zup.mercadolivre.product.productcharacteristic.ProductCharacteristic;
import br.com.zup.mercadolivre.user.CleanPassword;
import br.com.zup.mercadolivre.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    private Category category;
    private User user;
    private List<ProductCharacteristic> defaultCharacteristics;

    @BeforeEach
    public void initialize() {
        this.category = new Category("Tecnology", null);
        this.user = new User("email@email.com", new CleanPassword("123456"));
        this.defaultCharacteristics = Arrays.asList(new ProductCharacteristic("name1", "value"),
                                                    new ProductCharacteristic("name2", "value"),
                                                    new ProductCharacteristic("name3", "value"));
    }

    @DisplayName("Product characteristic list must have at least 3 items")
    @ParameterizedTest
    @MethodSource("testOne")
    void mustAllowToCreateProductWithThreeOrMoreCharacteristics(List<ProductCharacteristic> characteristics) {
        new Product("name", new BigDecimal(10), 10, characteristics, "description", this.category, this.user);
    }

    static Stream<Arguments> testOne() {
        return Stream.of(
                Arguments.of(
                        List.of(
                            new ProductCharacteristic("name1", "value"),
                            new ProductCharacteristic("name2", "value"),
                            new ProductCharacteristic("name3", "value")
                )),
                Arguments.of(
                        List.of(
                            new ProductCharacteristic("name1", "value"),
                            new ProductCharacteristic("name2", "value"),
                            new ProductCharacteristic("name3", "value"),
                            new ProductCharacteristic("name4", "value")
                )));
    }

    @DisplayName("Product characteristic list must have at least 3 items")
    @ParameterizedTest
    @MethodSource("testTwo")
    void mustNotAllowToCreateProductWithLessThanThreeCharacteristics(List<ProductCharacteristic> characteristics) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("name", new BigDecimal(10), 10, characteristics, "description", this.category, this.user);
        });
    }

    static Stream<Arguments> testTwo() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new ProductCharacteristic("name1", "value"),
                                new ProductCharacteristic("name2", "value")
                        )),
                Arguments.of(
                        List.of(
                                new ProductCharacteristic("name1", "value")
                        )));
    }

    @DisplayName("Product price must be positive")
    @ParameterizedTest
    @MethodSource("testThree")
    void mustNotAllowToCreateProductWithNonPositivePrice(BigDecimal price) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("name", price, 10, this.defaultCharacteristics, "description", this.category, this.user);
        });
    }

    static Stream<Arguments> testThree() {
        return Stream.of(
                Arguments.of(new BigDecimal(0)),
                Arguments.of(new BigDecimal(-5)));
    }

    @DisplayName("Product price must be positive")
    @ParameterizedTest
    @MethodSource("testFour")
    void mustAllowToCreateProductWithPositivePrice(BigDecimal price) {
        new Product("name", price, 10, this.defaultCharacteristics, "description", this.category, this.user);
    }

    static Stream<Arguments> testFour() {
        return Stream.of(
                Arguments.of(new BigDecimal(1)),
                Arguments.of(new BigDecimal(15)));
    }

    @DisplayName("Product quantity must be positive")
    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void mustAllowToCreateProductWithPositiveQuantity(Integer quantity) {
        new Product("name", new BigDecimal(10), quantity, this.defaultCharacteristics, "description", this.category, this.user);
    }

    @DisplayName("Product quantity must be positive")
    @ParameterizedTest
    @ValueSource(ints = {-10, 0})
    void mustNotAllowToCreateProductWithNonPositiveQuantity(Integer quantity) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("name", new BigDecimal(10), quantity, this.defaultCharacteristics, "description", this.category, this.user);
        });
    }

    @DisplayName("Description must have less than 1000 characters")
    @ParameterizedTest
    @ValueSource(strings = {"Description1", "Description2"})
    void mustAllowToCreateProductWithDescriptionLessThan1000Characters(String description) {
        new Product("name", new BigDecimal(10), 10, this.defaultCharacteristics, description, this.category, this.user);
    }

    @DisplayName("Description must have less than 1000 characters")
    @ParameterizedTest
    @ValueSource(strings = {
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. I",
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. It's a description. " +
            "It's a description. It's a description. It's a description"})
    void mustNotAllowToCreateProductWithDescriptionGreaterThan1000Characters(String description) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("name", new BigDecimal(10), 10, this.defaultCharacteristics, description, this.category, this.user);
        });
    }


}