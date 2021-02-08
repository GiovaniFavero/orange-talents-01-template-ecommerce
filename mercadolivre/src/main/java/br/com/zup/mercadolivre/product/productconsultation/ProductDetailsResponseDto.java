package br.com.zup.mercadolivre.product.productconsultation;

import br.com.zup.mercadolivre.product.Product;
import br.com.zup.mercadolivre.product.productimage.ProductImage;
import br.com.zup.mercadolivre.product.productquestion.ProductQuestion;
import br.com.zup.mercadolivre.product.productreview.ProductReview;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDetailsResponseDto {

    private String name;
    private BigDecimal price;
    private String description;
    private Set<String> images;
    private Set<ProductCharacteristicResponseDto> characteristics;
    private Double gradeAverage;
    private Integer gradeQuantity;
    private Set<Map<String, String>> reviews;
    private List<String> questions;

    public ProductDetailsResponseDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.images = this.convertProductImages(product.getImages());
        this.reviews = product.mapReviews(review -> {
           return Map.of("title", review.getTitle(), "description", review.getDescription());
        });
        this.questions = this.convertProductQuestions(product.getQuestions());
        this.gradeAverage = product.getGradeAverage();
        this.gradeQuantity = product.getReviews().size();
        this.characteristics = product.mapCharacteristics(ProductCharacteristicResponseDto :: new);
    }

    public List<ProductReviewResponseDto> convertProductReviews(List<ProductReview> reviews) {
        return reviews.stream().map(review -> {
            return new ProductReviewResponseDto(review.getGrade(), review.getTitle(), review.getDescription(), review.getUser().getUsername());
        }).collect(Collectors.toList());
    }

    public List<String> convertProductQuestions(List<ProductQuestion> questions) {
        return questions.stream().map(question -> {
            return question.getTitle();
        }).collect(Collectors.toList());
    }

    public Set<String> convertProductImages(Set<ProductImage> images) {
        return images.stream().map(image -> {
            return image.getLink();
        }).collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getImages() {
        return images;
    }

    public Double getGradeAverage() {
        return gradeAverage;
    }

    public Integer getGradeQuantity() {
        return gradeQuantity;
    }

    public Set<Map<String, String>> getReviews() {
        return reviews;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public Set<ProductCharacteristicResponseDto> getCharacteristics() {
        return characteristics;
    }
}
