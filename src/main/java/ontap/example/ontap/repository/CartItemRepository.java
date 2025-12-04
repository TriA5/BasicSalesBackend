package ontap.example.ontap.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontap.example.ontap.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID>{
    //Xem thử product đã được thêm vào cartItem chưa và có cart chưa
    Optional<CartItem> findByCartIdAndProductId(UUID cartId, UUID productId);
}
