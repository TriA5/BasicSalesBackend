package ontap.example.ontap.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontap.example.ontap.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{
}
