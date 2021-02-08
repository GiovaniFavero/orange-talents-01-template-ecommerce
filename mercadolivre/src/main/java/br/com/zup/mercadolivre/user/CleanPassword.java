package br.com.zup.mercadolivre.user;

import org.springframework.util.Assert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class represents a clean password.
 */
public class CleanPassword {

    private String password;

    public CleanPassword(String password) {
        Assert.isTrue(password.length() >= 6, "The password must have at least 6 characters");
        this.password = password;
    }

    public String hash(){
        return new BCryptPasswordEncoder().encode(this.password);
    }
}
