package br.com.zup.mercadolivre.order.beforepayment;

import br.com.zup.mercadolivre.order.Order;
import br.com.zup.mercadolivre.order.PaymentGateway;
import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.shared.config.validation.annotations.ExistsId;
import br.com.zup.mercadolivre.user.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewOrderCheckoutRequestDto {

    @NotNull
    @ExistsId(domainClass = Product.class, fieldName = "id")
    private Long productId;
    @NotNull
    @Positive
    private Integer quantity;
    @NotNull
    private PaymentGateway gateway;

    public NewOrderCheckoutRequestDto(@NotNull Long productId, @NotNull @Positive Integer quantity, @NotNull PaymentGateway gateway) {
        this.productId = productId;
        this.quantity = quantity;
        this.gateway = gateway;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public Order createOrder(EntityManager entityManager, User user) {
        Product product = this.getOrderProduct(entityManager);
        return new Order(this.gateway, product, this.quantity, user);
    }

    public Product getOrderProduct(EntityManager entityManager) {
        Assert.notNull(this.productId, "[NewOrderCheckoutRequestDto] Product ID can't be null");
        Product product = entityManager.find(Product.class, this.productId);
        Assert.notNull(product, "[NewOrderCheckoutRequestDto] Product with ID " + this.productId + " was not found!");
        return product;
    }
}
