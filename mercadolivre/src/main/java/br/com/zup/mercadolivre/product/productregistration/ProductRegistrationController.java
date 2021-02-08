package br.com.zup.mercadolivre.product.productregistration;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.product.productcharacteristic.DoNotAllowProductCharacteristicsWithTheSameNameValidator;
import br.com.zup.mercadolivre.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductRegistrationController {

    @PersistenceContext
    private EntityManager entityManager;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new DoNotAllowProductCharacteristicsWithTheSameNameValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity create (@AuthenticationPrincipal User user, @RequestBody @Valid NewProductRequestDto request) {
        Product product = request.toModel(entityManager, user);
        System.out.println(product.toString());
        entityManager.persist(product);
        return ResponseEntity.ok().build();
    }
}
