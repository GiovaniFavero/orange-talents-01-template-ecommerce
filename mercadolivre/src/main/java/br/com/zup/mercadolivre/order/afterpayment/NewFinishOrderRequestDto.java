package br.com.zup.mercadolivre.order.afterpayment;

import br.com.zup.mercadolivre.order.Order;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewFinishOrderRequestDto {

    @NotNull
    private Long orderId;
    @NotNull
    private Long gatewayOrderId;
    @NotBlank
    private String status;

    public NewFinishOrderRequestDto(@NotNull Long orderId, @NotNull Long gatewayOrderId, @NotBlank String status) {
        this.orderId = orderId;
        this.gatewayOrderId = gatewayOrderId;
        this.status = status;
    }

    public OrderPayment toModel(EntityManager entityManager) {
        Assert.notNull(this.orderId, "[NewFinishOrderRequestDto] OrderId can't be null!");
        Order order = entityManager.find(Order.class, this.orderId);
        Assert.notNull(order, "[NewFinishOrderRequestDto] Order with ID " + this.orderId + " was not found!");
        return new OrderPayment(order, this.gatewayOrderId, this.status);
    }
}
