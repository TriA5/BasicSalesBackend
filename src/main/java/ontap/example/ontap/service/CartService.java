package ontap.example.ontap.service;

import java.util.UUID;

import ontap.example.ontap.dto.CartItemDTO;
import ontap.example.ontap.entity.Cart;

public interface CartService {
    public Cart addToCart(UUID userId, UUID productId, CartItemDTO cartItemDTO);
}
