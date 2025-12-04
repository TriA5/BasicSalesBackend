package ontap.example.ontap.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontap.example.ontap.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,UUID>{
}
