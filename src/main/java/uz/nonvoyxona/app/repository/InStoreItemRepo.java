package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.InStoreItem;

public interface InStoreItemRepo extends JpaRepository<InStoreItem, Long> {
}
