package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
