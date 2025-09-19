package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Users;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, String> {

    Optional<Users> findByLogin(String login);
}
