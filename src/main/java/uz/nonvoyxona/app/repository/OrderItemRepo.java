package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
}
