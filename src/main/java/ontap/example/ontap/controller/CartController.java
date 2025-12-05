package ontap.example.ontap.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ontap.example.ontap.dto.CartItemDTO;
import ontap.example.ontap.entity.Cart;
import ontap.example.ontap.entity.User;
import ontap.example.ontap.repository.UserRepository;
import ontap.example.ontap.service.CartService;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public Cart addToCart(
                @RequestParam UUID productId, 
                @RequestBody CartItemDTO cartItemDTO){
        // Lấy thông tin user từ JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Lấy Username từ authentication
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        
        // Tìm user từ username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        
        return cartService.addToCart(user.getId(), productId, cartItemDTO);
    }
}
