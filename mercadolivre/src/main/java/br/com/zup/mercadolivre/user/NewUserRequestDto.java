package br.com.zup.mercadolivre.user;

import br.com.zup.mercadolivre.shared.validation.annotations.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewUserRequestDto {

    @NotBlank
    @Email
    @UniqueValue(domainClass = User.class, fieldName = "email")
    private String email;

    @NotBlank
    @Length(min = 6)
    private String password;

    public NewUserRequestDto(@NotBlank @Email String email, @NotBlank @Length(min = 6) String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User toModel() {
        return new User(this.email, new CleanPassword(this.password));
    }
}
