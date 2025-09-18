package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.Baker;

import java.util.List;

public interface BakerRepo extends JpaRepository<Baker, Integer> {

    @EntityGraph(attributePaths = {"productions"})
    @Query("SELECT b FROM Baker b")
    List<Baker> findAllWithProductions();

}
