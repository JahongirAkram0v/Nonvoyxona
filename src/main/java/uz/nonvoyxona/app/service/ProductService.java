package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Product;
import uz.nonvoyxona.app.model.dto.response.NameDto;
import uz.nonvoyxona.app.repository.ProductRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public void save(Product product) {
        productRepo.save(product);
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    public Optional<Product> findById(Integer productId) {
        return productRepo.findById(productId);
    }

    public List<NameDto> getAllDto() {
        return getAll().stream()
                .map(p -> {
                    return new NameDto(p.getId(), p.getName());
                })
                .toList();
    }

}
