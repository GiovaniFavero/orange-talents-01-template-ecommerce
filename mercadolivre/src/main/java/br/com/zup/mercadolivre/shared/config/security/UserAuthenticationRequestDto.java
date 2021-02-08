package br.com.zup.mercadolivre.shared.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserAuthenticationRequestDto {

    private String email;
    private String password;

    public UserAuthenticationRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken toModel() {
        return new UsernamePasswordAuthenticationToken(this.email, this.password);
    }
}
