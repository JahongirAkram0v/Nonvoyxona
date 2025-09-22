package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Production;
import uz.nonvoyxona.app.repository.ProductionRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductionRepo productionRepo;


    public List<Production> findAllByBranchIdToday(int branchId) {
        return productionRepo.findAllByBranchIdToday(branchId);
    }
}
