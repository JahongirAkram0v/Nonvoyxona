package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.Baker;
import uz.nonvoyxona.app.model.Branch;

import java.util.List;
import java.util.Optional;

public interface BranchRepo extends JpaRepository<Branch, Integer> {

    @Query("SELECT b FROM Branch br JOIN br.bakers b WHERE br.id = :branchId AND b.id = :bakerId")
    Optional<Baker> findBakerById(Integer branchId, Integer bakerId);

    @EntityGraph(attributePaths = {"branchProducts"})
    @Query("SELECT b FROM Branch b")
    List<Branch> findAllWithBranchProducts();

    @EntityGraph(attributePaths = {"bakers"})
    @Query("SELECT b FROM Branch b")
    List<Branch> findAllWithBakers();

    @EntityGraph(attributePaths = {"branchProducts", "bakers"})
    @Query("SELECT b FROM Branch b")
    List<Branch> findAllWithBranchProductsAndBakers();


}
