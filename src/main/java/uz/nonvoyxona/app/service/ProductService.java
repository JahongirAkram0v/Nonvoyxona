package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Product;
import uz.nonvoyxona.app.repository.ProductRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public void save(Product product) {
        productRepo.save(product);
    }

    public Optional<Product> findById(Integer productId) {
        return productRepo.findById(productId);
    }
}
