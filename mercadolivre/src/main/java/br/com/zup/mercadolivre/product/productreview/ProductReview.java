package br.com.zup.mercadolivre.product.productreview;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.user.User;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer grade;
    @NotBlank
    private String title;
    @NotBlank
    @Length(max = 500)
    private String description;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public ProductReview(@NotNull @Min(1) @Max(5) Integer grade, @NotBlank String title, @NotBlank @Length(max = 500) String description, @NotNull Product product, @NotNull User user) {
        Assert.notNull(grade, "[ProductReview] Grade can't be null!");
        Assert.isTrue(grade >= 1 && grade <= 5, "[ProductReview] Grade must be between 1 and 5!");
        Assert.notNull(title, "[ProductReview] Title can't be blank!");
        Assert.notNull(description, "[ProductReview] Description can't be blank!");
        Assert.isTrue(description.length() <= 500, "[ProductReview] Description can't bigger than 500 characters!");
        Assert.notNull(product, "[ProductReview] Product can't be null!");
        Assert.notNull(user, "[ProductReview] User can't be null!");

        this.grade = grade;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    @Deprecated
    public ProductReview(){}

    public Integer getGrade() {
        return grade;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductReview that = (ProductReview) o;
        return title.equals(that.title) && product.equals(that.product) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, product, user);
    }
}
