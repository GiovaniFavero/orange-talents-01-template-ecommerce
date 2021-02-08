package br.com.zup.mercadolivre.order;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.user.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentGateway gateway;
    @ManyToOne
    @NotNull
    private Product product;
    @NotNull
    private Integer quantity;
    @NotNull
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(PaymentGateway gateway, @NotNull Product product, @NotNull Integer quantity, @NotNull User user) {
        Assert.notNull(quantity, "[OrderOperations] Quantity can't be null");
        Assert.isTrue(quantity > 0, "[OrderOperations] Quantity must be greater than zero!");
        Assert.isTrue(product.getQuantity() >= quantity, "[OrderOperations] Product has no enough stock!");

        this.gateway = gateway;
        this.product = product;
        this.quantity = quantity;
        this.user = user;
        this.status = OrderStatus.STARTED;
    }

    @Deprecated
    public Order() {
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
