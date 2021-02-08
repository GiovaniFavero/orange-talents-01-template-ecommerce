package br.com.zup.mercadolivre.product.productcharacteristic;

import javax.validation.constraints.NotBlank;

public class NewProductCharacteristicRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String value;

    public NewProductCharacteristicRequestDto(@NotBlank String name, @NotBlank String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
