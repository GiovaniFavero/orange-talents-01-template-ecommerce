package br.com.zup.mercadolivre.product.productregistration;

import br.com.zup.mercadolivre.category.Category;
import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.product.productcharacteristic.NewProductCharacteristicRequestDto;
import br.com.zup.mercadolivre.product.productcharacteristic.ProductCharacteristic;
import br.com.zup.mercadolivre.shared.config.validation.annotations.ExistsId;
import br.com.zup.mercadolivre.user.User;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NewProductRequestDto {

    @NotBlank
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @Positive
    private Integer quantity;
    @Size(min = 3)
    private List<NewProductCharacteristicRequestDto> productCharacteristics;
    @NotBlank
    @Size(max = 1000)
    private String description;
    @NotNull
    @ExistsId(domainClass = Category.class, fieldName = "id")
    private Long categoryId;

    public NewProductRequestDto(@NotBlank String name, @NotNull @Positive BigDecimal price, @NotNull @Positive Integer quantity,
                                @Size(min = 3) List<NewProductCharacteristicRequestDto> productCharacteristics,
                                @NotBlank @Size(max = 1000) String description, @NotNull Long categoryId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productCharacteristics = productCharacteristics;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public List<NewProductCharacteristicRequestDto> getProductCharacteristics() {
        return productCharacteristics;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Product toModel(EntityManager entityManager, User owner) {
        Assert.notNull(this.categoryId, "[NewProductRequestDto] CategoryId can't be null!");
        Category category = entityManager.find(Category.class, this.categoryId);
        Assert.notNull(category, "[NewProductRequestDto] The category with ID " + this.categoryId + " was not found!");
        return new Product(this.name, this.price, this.quantity, this.toModelProductCharacteristics(), this.description, category, owner);
    }

    private List<ProductCharacteristic> toModelProductCharacteristics(){
        return this.productCharacteristics.stream().map(obj -> {
            return new ProductCharacteristic(obj.getName(), obj.getValue());
        }).collect(Collectors.toList());
    }

    public Set<String> getCharacteristicWithTheSameName() {
        HashSet<String> sameNames = new HashSet<>();
        HashSet<String> result = new HashSet<>();
        for (NewProductCharacteristicRequestDto characteristic : this.productCharacteristics) {
            String name = characteristic.getName();
            if(!sameNames.add(name)) {
                result.add(name);
            }
        }
        return result;
    }
}
