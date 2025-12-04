package ontap.example.ontap.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ontap.example.ontap.dto.CartItemDTO;
import ontap.example.ontap.entity.Cart;
import ontap.example.ontap.service.CartService;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(
                @RequestParam UUID userId, 
                @RequestParam UUID productId, 
                @RequestBody CartItemDTO cartItemDTO){
        return cartService.addToCart(userId, productId, cartItemDTO);
    }
}
