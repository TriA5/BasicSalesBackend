package ontap.example.ontap.service;

import java.util.UUID;

import ontap.example.ontap.entity.Order;

public interface OrderService {
    Order checkout(UUID userId, UUID addressId);
}
