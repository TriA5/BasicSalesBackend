package ontap.example.ontap.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ontap.example.ontap.entity.Order;
import ontap.example.ontap.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public Order checkout(@RequestParam UUID userId, @RequestParam UUID addressId) {
        return orderService.checkout(userId, addressId);
    }
}
