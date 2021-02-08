package br.com.zup.mercadolivre.product.productconsultation;

import br.com.zup.mercadolivre.product.productcharacteristic.ProductCharacteristic;

public class ProductCharacteristicResponseDto {

    private String name;
    private String value;

    public ProductCharacteristicResponseDto(ProductCharacteristic characteristic) {
        this.name = characteristic.getName();
        this.value = characteristic.getValue();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
