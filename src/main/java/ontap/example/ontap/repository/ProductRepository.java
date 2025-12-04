package ontap.example.ontap.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontap.example.ontap.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
