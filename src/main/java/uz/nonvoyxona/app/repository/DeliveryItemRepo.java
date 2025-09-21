package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.DeliveryItem;

public interface DeliveryItemRepo extends JpaRepository<DeliveryItem, Long> {
}
