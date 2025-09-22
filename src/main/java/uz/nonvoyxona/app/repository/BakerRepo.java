package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.Baker;

import java.util.List;

public interface BakerRepo extends JpaRepository<Baker, Integer> {

    @Query("select b from Baker b where b.branch.id = ?1")
    List<Baker> findAllByBranchId(Integer branchId);

}
