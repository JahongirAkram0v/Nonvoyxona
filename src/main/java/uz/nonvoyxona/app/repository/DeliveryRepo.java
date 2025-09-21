package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Delivery;

public interface DeliveryRepo extends JpaRepository<Delivery, Integer> {
}
