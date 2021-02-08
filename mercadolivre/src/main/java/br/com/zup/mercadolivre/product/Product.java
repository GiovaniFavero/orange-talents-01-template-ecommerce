package br.com.zup.mercadolivre.product;

import br.com.zup.mercadolivre.category.Category;
import br.com.zup.mercadolivre.product.productcharacteristic.ProductCharacteristic;
import br.com.zup.mercadolivre.product.productimage.ProductImage;
import br.com.zup.mercadolivre.product.productquestion.ProductQuestion;
import br.com.zup.mercadolivre.product.productreview.ProductReview;
import br.com.zup.mercadolivre.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    private Integer quantity;
    @Size(min = 3)
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductCharacteristic> productCharacteristics;
    @NotBlank
    @Size(max = 1000)
    private String description;
    @NotNull @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    private Category category;
    @NotNull
    private LocalDateTime registrationDate;
    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerUserId")
    private User ownerUser;
    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ProductImage> images = new HashSet<>();
    @OneToMany(mappedBy = "product")
    private List<ProductReview> reviews;
    @OneToMany(mappedBy = "product")
    private List<ProductQuestion> questions;

    @Deprecated
    public Product(){}

    public Product(@NotBlank String name, @NotNull @Positive BigDecimal price, @NotNull @Positive Integer quantity,
                   @Size(min = 3) List<ProductCharacteristic> productCharacteristics,
                   @NotBlank @Size(max = 1000) String description, @NotNull @Valid Category category,
                   @NotNull @Valid User owner) {
        Assert.hasLength(name, "[Product] Name can't be blank");
        Assert.notNull(price, "[Product] Price can't be null");
        Assert.isTrue(price.intValue() > 0, "[Product] Price must be positive");
        Assert.notNull(quantity, "[Product] Quantity can't be null");
        Assert.isTrue(quantity > 0, "[Product] Quantity must be positive");
        Assert.notNull(productCharacteristics, "[Product] Product characteristic list can't be null");
        Assert.isTrue(productCharacteristics.size() >= 3, "[Product] Product characteristic list must have at least 3 items");
        Assert.hasLength(description, "[Product] Description can't be blank");
        Assert.isTrue(description.length() <= 1000, "[Product] Description must have less than 1000 characters");
        Assert.notNull(category, "[Product] Category can't be null");

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productCharacteristics = productCharacteristics;
        this.description = description;
        this.category = category;
        this.registrationDate = LocalDateTime.now();
        this.ownerUser = owner;
        this.productCharacteristics.forEach(c -> {
            c.setProduct(this);
        });
    }

    public void linkImages(Set<String> links) {
        Set<ProductImage> images = links.stream().map(link -> new ProductImage(this,link)).collect(Collectors.toSet());
        this.images.addAll(images);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean belongsTo(User user) {
        Assert.notNull(user, "[Product] User can't be null!");
        return user.getEmail().equals(this.ownerUser.getEmail());
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

    public List<ProductCharacteristic> getProductCharacteristics() {
        return productCharacteristics;
    }

    public String getDescription() {
        return description;
    }

    public Set<ProductImage> getImages() {
        return images;
    }

    public List<ProductReview> getReviews() {
        return reviews;
    }

    public List<ProductQuestion> getQuestions() {
        return questions;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public Double getGradeAverage() {
        IntStream mapToInt = this.reviews.stream().mapToInt(grade -> grade.getGrade());
        OptionalDouble average = mapToInt.average();
        if(average.isPresent()) {
            return average.getAsDouble();
        }
        return 0.0;
    }

    public <T> Set<T> mapCharacteristics(Function<ProductCharacteristic, T> function) {
        return this.productCharacteristics.stream().map(function).collect(Collectors.toSet());
    }

    public <T> Set<T> mapReviews(Function<ProductReview, T> function) {
        return this.reviews.stream().map(function).collect(Collectors.toSet());
    }

    public boolean consumeStock(@Positive Integer quantity) {
        Assert.isTrue(quantity > 0,"[Product] Quantity has to be positive to consume Stock!");
        if(quantity <= this.quantity) {
            this.quantity -= quantity;
            return true;
        }
        return false;

    }
}
