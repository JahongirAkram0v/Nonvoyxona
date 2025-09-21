package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.InStore;

public interface InStoreRepo extends JpaRepository<InStore, Long> {
}
