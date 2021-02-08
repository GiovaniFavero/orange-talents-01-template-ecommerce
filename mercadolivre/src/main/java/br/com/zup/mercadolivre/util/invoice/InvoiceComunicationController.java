package br.com.zup.mercadolivre.util.invoice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice/inform")
public class InvoiceComunicationController {

    @PostMapping
    public ResponseEntity receiveOrderInformations(@RequestBody NewOrderComunicationDto request) {
        System.out.println("INVOICE SYSTEM - RECEIVING ORDER INFORMATION");
        System.out.println("INVOICE SYSTEM - ORDER ID: " + request.getOrderId());
        System.out.println("INVOICE SYSTEM - USER ID: " + request.getUserId());
        return ResponseEntity.ok().build();
    }

}
