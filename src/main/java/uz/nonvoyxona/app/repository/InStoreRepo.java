package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.InStore;

import java.util.List;

public interface InStoreRepo extends JpaRepository<InStore, Integer> {

    @Query("select i from InStore i where i.branch.id = ?1 and FUNCTION('DATE', i.createdAt) = CURRENT_DATE")
    List<InStore> findAllByBranchIdToday(int branchId);
}
