package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Production;

public interface ProductionRepo extends JpaRepository<Production, Integer> {
}
