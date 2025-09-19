package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.Branch;

import java.util.List;

public interface BranchRepo extends JpaRepository<Branch, Integer> {

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
