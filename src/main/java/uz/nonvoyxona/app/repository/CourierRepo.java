package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Courier;

import java.util.List;

public interface CourierRepo extends JpaRepository<Courier, Integer> {

}
