package br.com.zup.mercadolivre.product.productimage;

import br.com.zup.mercadolivre.product.Product;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    @Valid
    private Product product;
    @URL
    @NotBlank
    private String link;

    public ProductImage(@NotNull @Valid Product product, @NotBlank @URL String link) {
        Assert.notNull(product, "[ProductImage] Product can't be null!");
        Assert.hasLength(link, "[ProductImage] Link can't be blank!");

        this.product = product;
        this.link = link;
    }

    @Deprecated
    public ProductImage(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return product.equals(that.product) && link.equals(that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, link);
    }

    public String getLink() {
        return link;
    }
}
