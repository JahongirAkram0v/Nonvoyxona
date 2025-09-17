package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Baker;

public interface BakerRepo extends JpaRepository<Baker, Integer> {
}
