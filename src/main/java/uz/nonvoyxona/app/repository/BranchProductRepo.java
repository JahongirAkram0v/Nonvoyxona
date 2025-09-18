package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.BranchProduct;

import java.util.List;

public interface BranchProductRepo extends JpaRepository<BranchProduct, Integer> {

    @EntityGraph(attributePaths = {"orderItems"})
    @Query("SELECT b FROM BranchProduct b")
    List<BranchProduct> findAllWithOrderItems();
}
