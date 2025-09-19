package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Order1;

public interface OrderRepo extends JpaRepository<Order1, Integer> {
}
