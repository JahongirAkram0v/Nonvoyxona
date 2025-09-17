package uz.nonvoyxona.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nonvoyxona.app.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
