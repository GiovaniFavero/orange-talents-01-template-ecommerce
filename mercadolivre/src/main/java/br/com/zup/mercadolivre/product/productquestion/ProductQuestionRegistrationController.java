package br.com.zup.mercadolivre.product.productquestion;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.shared.email.EmailService;
import br.com.zup.mercadolivre.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/products/{id}/questions")
public class ProductQuestionRegistrationController {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EmailService emailService;

    @PostMapping
    @Transactional
    public ResponseEntity create (@PathVariable Long id, @AuthenticationPrincipal User user, @RequestBody @Valid NewProductQuestionRequestDto request) {
        ProductQuestion productQuestion = request.toModel(entityManager, user, id);
        entityManager.persist(productQuestion);

        Product product = productQuestion.getProduct();
        String email = product.getOwnerUser().getEmail();
        emailService.send(email, "New question - Product: " + product.getName(), "* User: " + productQuestion.getUser().getEmail() + "\n - Question: " + productQuestion.getTitle());
        return ResponseEntity.ok().build();
    }
}
