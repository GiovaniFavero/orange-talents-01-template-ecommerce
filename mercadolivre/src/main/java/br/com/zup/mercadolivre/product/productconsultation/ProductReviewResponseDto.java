package br.com.zup.mercadolivre.product.productconsultation;

public class ProductReviewResponseDto {

    private Integer grade;
    private String title;
    private String description;
    private String username;

    public ProductReviewResponseDto(Integer grade, String title, String description, String username) {
        this.grade = grade;
        this.title = title;
        this.description = description;
        this.username = username;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }
}
