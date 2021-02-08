package br.com.zup.mercadolivre.product.productcharacteristic;

import br.com.zup.mercadolivre.product.productregistration.NewProductRequestDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class DoNotAllowProductCharacteristicsWithTheSameNameValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewProductRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        NewProductRequestDto request = (NewProductRequestDto) target;
        Set<String> characteristics = request.getCharacteristicWithTheSameName();
        if(!characteristics.isEmpty()) {
            characteristics.forEach(c -> {
                errors.rejectValue("productCharacteristics", null, "There is more than one characteristic with name '" + c + "'");
            });

        }
    }
}
