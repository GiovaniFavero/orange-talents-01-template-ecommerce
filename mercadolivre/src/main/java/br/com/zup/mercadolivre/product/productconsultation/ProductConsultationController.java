package br.com.zup.mercadolivre.product.productconsultation;

import br.com.zup.mercadolivre.product.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/products")
public class ProductConsultationController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsResponseDto> getProductDetails(@PathVariable Long id) {
        Product product = entityManager.find(Product.class, id);
        return ResponseEntity.ok(new ProductDetailsResponseDto(product));
    }
}
