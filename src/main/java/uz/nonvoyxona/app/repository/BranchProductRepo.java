package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.BranchProduct;

public interface BranchProductRepo extends JpaRepository<BranchProduct, Integer> {
}
