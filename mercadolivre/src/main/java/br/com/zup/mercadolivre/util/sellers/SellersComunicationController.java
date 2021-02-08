package br.com.zup.mercadolivre.util.sellers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sellers/inform")
public class SellersComunicationController {

    @PostMapping
    public ResponseEntity receiveOrderInformations(@RequestBody @Valid NewOrderComunicationDto request) {
        System.out.println("SELLERS SYSTEM - RECEIVING ORDER INFORMATION");
        System.out.println("SELLERS SYSTEM - ORDER ID: " + request.getOrderId());
        System.out.println("SELLERS SYSTEM - SELLER ID: " + request.getSellerId());
        return ResponseEntity.ok().build();
    }

}
