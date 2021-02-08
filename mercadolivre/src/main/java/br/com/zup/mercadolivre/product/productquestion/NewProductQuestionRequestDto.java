package br.com.zup.mercadolivre.product.productquestion;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.user.User;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class NewProductQuestionRequestDto {

    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public ProductQuestion toModel(EntityManager entityManager, User user, Long productId) {
        Product product = entityManager.find(Product.class, productId);
        return new ProductQuestion(this.title, user, product);
    }
}
