package br.com.zup.mercadolivre.order.afterpayment;

import br.com.zup.mercadolivre.order.Order;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class OrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    private Order order;
    @NotNull
    private Long gatewayOrderId;
    @NotBlank
    private String status;
    @NotNull
    private LocalDateTime registrationDate;

    public OrderPayment(@NotNull Order order, @NotNull Long gatewayOrderId, @NotBlank String status) {
        Assert.notNull(order, "[OrderPayment] Order can't be null!");
        Assert.notNull(gatewayOrderId, "[OrderPayment] GatewayOrderId can't be null!");
        Assert.hasLength(status, "[OrderPayment] Status can't be blank!");

        this.order = order;
        this.gatewayOrderId = gatewayOrderId;
        this.status = status;
        this.registrationDate = LocalDateTime.now();
    }

    @Deprecated
    public OrderPayment() {
    }

    public Order getOrder() {
        return order;
    }
}
