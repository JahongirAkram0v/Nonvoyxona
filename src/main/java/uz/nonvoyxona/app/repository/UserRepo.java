package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nonvoyxona.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u.id FROM User u WHERE u.role = 'ADMIN'")
    List<String> getAllAdminIds();
}
