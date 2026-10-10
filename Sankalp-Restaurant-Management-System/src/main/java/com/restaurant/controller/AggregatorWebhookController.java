package com.restaurant.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.AggregatorOrderDTO;
import com.restaurant.service.AggregatorIntegrationService;

@RestController
@RequestMapping({"/integrations/aggregator", "/api/integrations/aggregator"})
@CrossOrigin(origins = "*")
public class AggregatorWebhookController {

    private final AggregatorIntegrationService service;

    public AggregatorWebhookController(AggregatorIntegrationService service) {
        this.service = service;
    }

    @PostMapping("/webhook/urbanpiper")
    public ResponseEntity<?> receiveUrbanpiperWebhook(@RequestBody AggregatorOrderDTO dto) {
        dto.setChannel("URBANPIPER");
        int orderId = service.processIncomingAggregatorOrder(dto);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "UrbanPiper Order Received & Processing",
                "orderId", orderId,
                "externalOrderId", dto.getExternalOrderId()
        ));
    }

    @PostMapping("/webhook/swiggy")
    public ResponseEntity<?> receiveSwiggyWebhook(@RequestBody AggregatorOrderDTO dto) {
        dto.setChannel("SWIGGY");
        int orderId = service.processIncomingAggregatorOrder(dto);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Swiggy Order Ingested",
                "orderId", orderId,
                "externalOrderId", dto.getExternalOrderId()
        ));
    }

    @PostMapping("/webhook/zomato")
    public ResponseEntity<?> receiveZomatoWebhook(@RequestBody AggregatorOrderDTO dto) {
        dto.setChannel("ZOMATO");
        int orderId = service.processIncomingAggregatorOrder(dto);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Zomato Order Ingested",
                "orderId", orderId,
                "externalOrderId", dto.getExternalOrderId()
        ));
    }
}
