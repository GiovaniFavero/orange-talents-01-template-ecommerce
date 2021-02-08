package br.com.zup.mercadolivre.order.beforepayment;

import br.com.zup.mercadolivre.product.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

public class DoNotAllowOrderToProductWithoutEnoughStock implements Validator {

    private EntityManager entityManager;

    public DoNotAllowOrderToProductWithoutEnoughStock(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewOrderCheckoutRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) {
            return;
        }
        NewOrderCheckoutRequestDto request = (NewOrderCheckoutRequestDto) target;
        Product product = request.getOrderProduct(entityManager);
        if(product.getQuantity() < request.getQuantity()) {
            errors.rejectValue("quantity", null, "Product with ID " + request.getProductId() + " has no enough stock! Current stock: " + product.getQuantity());
        }
    }
}
