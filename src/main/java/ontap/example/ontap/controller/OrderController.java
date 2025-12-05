package ontap.example.ontap.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import ontap.example.ontap.entity.Order;
import ontap.example.ontap.entity.User;
import ontap.example.ontap.repository.UserRepository;
import ontap.example.ontap.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/checkout")
    public Order checkout(@RequestParam UUID addressId) {
        // Lấy thông tin user từ JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Lấy Username từ authentication
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        
        // Tìm user từ username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
                
        return orderService.checkout(user.getId(), addressId);
    }
}
