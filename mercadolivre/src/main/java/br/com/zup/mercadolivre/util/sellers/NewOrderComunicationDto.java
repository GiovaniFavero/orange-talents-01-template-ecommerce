package br.com.zup.mercadolivre.util.sellers;

import javax.validation.constraints.NotNull;

public class NewOrderComunicationDto {

    @NotNull
    private Long orderId;
    @NotNull
    private Long sellerId;

    public NewOrderComunicationDto(@NotNull Long orderId, @NotNull Long sellerId) {
        this.orderId = orderId;
        this.sellerId = sellerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getSellerId() {
        return sellerId;
    }
}
