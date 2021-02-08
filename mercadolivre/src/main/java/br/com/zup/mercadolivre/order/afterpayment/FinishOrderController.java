package br.com.zup.mercadolivre.order.afterpayment;

import br.com.zup.mercadolivre.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders/finish")
public class FinishOrderController {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private CommunicationExternalSystemService communicationService;

    @PostMapping
    @Transactional
    public String finishOrder(@AuthenticationPrincipal User user, @RequestBody @Valid NewFinishOrderRequestDto request) {
        OrderPayment payment = request.toModel(entityManager);
        entityManager.persist(payment);

        Map<String, Object> mapInvoiceSystem = new HashMap<>();
        mapInvoiceSystem.put("orderId", payment.getOrder().getId());
        mapInvoiceSystem.put("userId", payment.getOrder().getUser().getId());

        communicationService.communicate("INVOICE", mapInvoiceSystem);

        Map<String, Object> mapSellersSystem = new HashMap<>();
        mapSellersSystem.put("orderId", payment.getOrder().getId());
        mapSellersSystem.put("sellerId", payment.getOrder().getProduct().getOwnerUser().getId());

        communicationService.communicate("SELLERS", mapSellersSystem);

        return "";
    }

}
