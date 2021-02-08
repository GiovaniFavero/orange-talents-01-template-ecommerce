package br.com.zup.mercadolivre.order.afterpayment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommunicationExternalSystemService {

    @Value("${order.system.invoice.url}")
    private String urlInvoiceSystem;
    @Value("${order.system.invoice.token}")
    private String tokenInvoiceSystem;
    @Value("${order.system.seller.url}")
    private String urlSellersSystem;
    @Value("${order.system.seller.token}")
    private String tokenSellersSystem;

    public void communicate(String system, Map<String, Object> body) {
        String url = "INVOICE".equals(system) ? urlInvoiceSystem : urlSellersSystem;
        String token = "INVOICE".equals(system) ? tokenInvoiceSystem : tokenSellersSystem;

        /* Comunicate invoice system */
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<FinishOrderController> responseEntity = restTemplate.postForEntity(url, entity, FinishOrderController.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println(system + " system informed with success!");
        } else {
            System.out.println("Something went wrong when trying to inform " + system + " System about the order!");
        }
    }



}
