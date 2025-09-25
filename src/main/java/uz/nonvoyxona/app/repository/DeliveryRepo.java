package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepo extends JpaRepository<Delivery, Integer> {

    @Query("select d from Delivery d where d.branch.id = ?1 and d.deliveryStatus not in ('IN_DELIVERY', 'DELIVERED')")
    List<Delivery> findAllByBranchIdForBranch(int branchId);

    @Query("select d from Delivery d where d.branch.id = ?1 and d.courier is null")
    List<Delivery> findAllByBranchIdForCourier(int branchId);

    @Query("select d from Delivery d where d.branch.id = ?1 and d.deliveryStatus = 'DELIVERED' and DATE(d.createdAt) = CURRENT_DATE")
    List<Delivery> findAllByBranchIdForMyCourierDtoToday(int branchId);
}
