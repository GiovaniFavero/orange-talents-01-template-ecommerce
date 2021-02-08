package br.com.zup.mercadolivre.product.productcharacteristic;

import br.com.zup.mercadolivre.product.Product;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ProductCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String value;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public ProductCharacteristic(@NotBlank String name, @NotBlank String value) {
        Assert.hasLength(name, "[ProductCharacteristic] Name can't be blank");
        Assert.hasLength(value, "[ProductCharacteristic] Value can't be blank");

        this.name = name;
        this.value = value;
    }

    @Deprecated
    public ProductCharacteristic(){}

    @Override
    public String toString() {
        return "ProductCharacteristic{" +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", product=" + product +
                '}';
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
