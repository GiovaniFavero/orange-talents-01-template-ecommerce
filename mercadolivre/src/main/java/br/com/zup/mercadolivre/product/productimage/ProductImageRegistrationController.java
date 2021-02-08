package br.com.zup.mercadolivre.product.productimage;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductImageRegistrationController {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private Uploader uploaderFake;

    @PostMapping("/{id}/images")
    @Transactional
    public ResponseEntity addImage (@PathVariable Long id, @AuthenticationPrincipal User user, @Valid NewProductImageRequestDto request) {
        Product product = entityManager.find(Product.class, id);
        if(!product.belongsTo(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Set<String> links = uploaderFake.send(request.getImages());
        product.linkImages(links);

        entityManager.merge(product);

        return ResponseEntity.ok().build();

    }

}
