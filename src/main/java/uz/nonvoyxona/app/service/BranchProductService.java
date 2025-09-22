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


    public List<BranchProduct> findAllByBranchId(int branchId) {
        return branchProductRepo.findAllByBranchId(branchId);
    }
}
