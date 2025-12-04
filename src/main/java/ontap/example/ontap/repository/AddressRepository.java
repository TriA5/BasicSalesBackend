package ontap.example.ontap.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ontap.example.ontap.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID>{
    Optional<Address> findByUserIdAndIsDefaultTrue(UUID userId);
}
