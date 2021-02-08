package br.com.zup.mercadolivre.product.productquestion;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.user.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ProductQuestion implements Comparable<ProductQuestion>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    private LocalDateTime registrationDate;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public ProductQuestion(@NotBlank String title, @NotNull User user, @NotNull Product product) {
        Assert.hasLength(title, "[ProductQuestion] Title can't be null!");
        Assert.notNull(user, "[ProductQuestion] User can't be null!");
        Assert.notNull(product, "[ProductQuestion] Product can't be null!");

        this.title = title;
        this.registrationDate = LocalDateTime.now();
        this.user = user;
        this.product = product;
    }

    @Deprecated
    public ProductQuestion(){}

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductQuestion that = (ProductQuestion) o;
        return title.equals(that.title) && user.equals(that.user) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, user, product);
    }

    @Override
    public int compareTo(ProductQuestion o) {
        return this.title.compareTo(o.getTitle());
    }

    public Product getProduct() {
        return product;
    }
}
