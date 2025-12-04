package ontap.example.ontap.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontap.example.ontap.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID>{
    //xem thử người dùng đã có cart chưa
    Optional<Cart> findByUserId(UUID userId);
}
