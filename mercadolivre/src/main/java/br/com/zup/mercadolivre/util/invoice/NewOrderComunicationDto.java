package br.com.zup.mercadolivre.util.invoice;

import javax.validation.constraints.NotNull;

public class NewOrderComunicationDto {

    @NotNull
    private Long orderId;
    @NotNull
    private Long userId;

    public NewOrderComunicationDto(@NotNull Long orderId, @NotNull Long userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getUserId() {
        return userId;
    }
}
