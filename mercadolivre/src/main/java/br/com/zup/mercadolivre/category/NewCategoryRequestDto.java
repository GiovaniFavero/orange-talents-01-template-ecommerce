package br.com.zup.mercadolivre.category;

import br.com.zup.mercadolivre.shared.validation.annotations.ExistsId;
import br.com.zup.mercadolivre.shared.validation.annotations.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class NewCategoryRequestDto {

    @NotBlank
    @UniqueValue(domainClass = Category.class, fieldName = "name")
    private String name;

    @ExistsId(domainClass = Category.class, fieldName = "id")
    private Long parentCategoryId;

    public NewCategoryRequestDto(@NotBlank String name, Long parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public Category toModel(EntityManager entityManager) {
        if (this.parentCategoryId != null) {
            Category category = entityManager.find(Category.class, this.parentCategoryId);
            Assert.notNull(category, "The category with ID " + this.parentCategoryId + " was not found!");
            return new Category(this.name, category);
        }
        return new Category(this.name, null);
    }
}
