package br.com.zup.mercadolivre.product.productreview;

import br.com.zup.mercadolivre.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/products/{id}/reviews")
public class ProductReviewRegistrationController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity create (@PathVariable Long id, @AuthenticationPrincipal User user, @RequestBody @Valid NewProductReviewRequestDto request) {
        ProductReview productReview = request.toModel(entityManager, user, id);
        entityManager.persist(productReview);
        return ResponseEntity.ok().build();
    }

}
