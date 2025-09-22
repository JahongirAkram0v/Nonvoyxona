package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.BranchProduct;

import java.util.List;

public interface BranchProductRepo extends JpaRepository<BranchProduct, Integer> {

    @Query("select bp from BranchProduct bp where bp.branch.id = ?1")
    List<BranchProduct> findAllByBranchId(int branchId);
}
