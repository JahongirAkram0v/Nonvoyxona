package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.Delivery;

import java.util.List;

public interface DeliveryRepo extends JpaRepository<Delivery, Integer> {

    @Query("select d from Delivery d where d.branch.id = ?1 and d.courier is null")
    List<Delivery> findAllByBranchIdAndCourierIsNull(int branchId);
}
