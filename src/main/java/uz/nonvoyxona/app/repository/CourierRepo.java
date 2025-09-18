package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.Courier;
import uz.nonvoyxona.app.model.Order;

import java.util.List;

public interface CourierRepo extends JpaRepository<Courier, Integer> {

    @EntityGraph(attributePaths = {"orders"})
    @Query("SELECT b FROM Courier b")
    List<Order> findAllWithOrders();
}
