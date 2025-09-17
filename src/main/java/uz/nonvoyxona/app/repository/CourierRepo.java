package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Courier;

public interface CourierRepo extends JpaRepository<Courier, Integer> {
}
