package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.Production;

import java.util.List;

public interface ProductionRepo extends JpaRepository<Production, Integer> {

    @Query("select p from Production p where p.branch.id = ?1 and DATE(p.createdAt) = CURRENT_DATE")
    List<Production> findAllByBranchIdToday(Integer branchId);
}
