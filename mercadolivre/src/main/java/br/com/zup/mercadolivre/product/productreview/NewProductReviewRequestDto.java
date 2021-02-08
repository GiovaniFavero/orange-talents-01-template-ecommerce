package br.com.zup.mercadolivre.product.productreview;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.user.User;
import io.jsonwebtoken.lang.Assert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewProductReviewRequestDto {

    @Min(1)
    @Max(5)
    @NotNull
    private Integer grade;
    @NotBlank
    private String title;
    @NotBlank
    @Length(max = 500)
    private String description;

    public NewProductReviewRequestDto(@Min(1) @Max(5) @NotNull Integer grade, @NotBlank String title, @NotNull @Length(max = 500) String description) {
        this.grade = grade;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "NewProductOpinionRequestDto{" +
                "grade=" + grade +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public ProductReview toModel(EntityManager entityManager, User user, Long productId) {
        Assert.notNull(productId, "[NewProductReviewRequestDto] Product ID can't be null!");
        Product product = entityManager.find(Product.class, productId);
        return new ProductReview(this.grade, this.title, this.description, product, user);
    }
}
