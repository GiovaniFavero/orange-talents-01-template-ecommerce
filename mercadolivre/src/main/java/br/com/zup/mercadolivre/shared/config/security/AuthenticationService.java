package br.com.zup.mercadolivre.shared.config.security;

import br.com.zup.mercadolivre.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    @Value("${security.username-query}")
    private String query;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<?> objects = entityManager.createQuery(this.query)
                .setParameter("username", username).getResultList();
        Assert.isTrue(objects.size() <= 1, "[BUG] more than one user with same username");

        if(objects.isEmpty()) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }

        return (User) objects.get(0);
    }
}
