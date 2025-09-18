package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.BranchProduct;
import uz.nonvoyxona.app.repository.BranchProductRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchProductService {

    private final BranchProductRepo branchProductRepo;

    public void create()  {
        // Implementation for creating a BranchProduct can be added here
    }

    public Optional<BranchProduct> findById(Integer id) {
        return branchProductRepo.findById(id);
    }

    public List<BranchProduct> findAll() {
        return branchProductRepo.findAll();
    }

    public List<BranchProduct> findAllWithOrderItems() {
        return branchProductRepo.findAllWithOrderItems();
    }
}
