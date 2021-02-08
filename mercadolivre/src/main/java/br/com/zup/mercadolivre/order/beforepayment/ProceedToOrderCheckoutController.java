package br.com.zup.mercadolivre.order.beforepayment;

import br.com.zup.mercadolivre.order.Order;
import br.com.zup.mercadolivre.order.PaymentGateway;
import br.com.zup.mercadolivre.shared.email.EmailService;
import br.com.zup.mercadolivre.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/orders/proceed-to-checkout")
public class ProceedToOrderCheckoutController {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EmailService emailService;

    @InitBinder
    public void init (WebDataBinder binder) {
        binder.addValidators(new DoNotAllowOrderToProductWithoutEnoughStock(entityManager));
    }

    @PostMapping
    @Transactional
    public void proceedToCheckout(HttpServletResponse response, @AuthenticationPrincipal User user, UriComponentsBuilder uriBuilder, @RequestBody @Valid NewOrderCheckoutRequestDto request) throws IOException {
        Order order = request.createOrder(entityManager, user);
        boolean stockConsumption = order.getProduct().consumeStock(order.getQuantity());
        if(stockConsumption) {
            entityManager.persist(order);

            if(order.getGateway().equals(PaymentGateway.PAGSEGURO)) {
                String pagSeguroUrl = uriBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(order.getId()).toString();
                response.sendRedirect("pagseguro.com?returnId="+ order.getId()  + "&redirectUrl=" + pagSeguroUrl);
            } else {
                String paypalUrl = uriBuilder.path("/retorno-paypal/{id}").buildAndExpand(order.getId()).toString();
                response.sendRedirect("paypal.com/"+ order.getId()  + "?redirectUrl=" + paypalUrl);
            }

            emailService.send(order.getProduct().getOwnerUser().getEmail(), "Proceed to checkout of product: " + order.getProduct().getName(), "The user " + order.getUser().getEmail()
            + " is proceeding to checkout of your product " + order.getProduct().getName());
        }
    }

}
