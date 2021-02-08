package br.com.zup.mercadolivre.category;

import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private Category parentCategory;

    public Category(@NotBlank String name, Category parentCategory) {
        Assert.hasLength(name, "[Category] Field 'name' can't be blank!");

        this.name = name;
        this.parentCategory = parentCategory;
    }

    @Deprecated
    public Category(){}
}
