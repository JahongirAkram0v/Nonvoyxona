package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.BranchProduct;
import uz.nonvoyxona.app.model.dto.response.BranchProductDto;
import uz.nonvoyxona.app.repository.BranchProductRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchProductService {

    private final BranchProductRepo branchProductRepo;


    public List<BranchProduct> findAllByBranchId(int branchId) {
        return branchProductRepo.findAllByBranchId(branchId);
    }

    public List<BranchProductDto> findAllByBranchIdDto(int branchId) {
        return findAllByBranchId(branchId)
                .stream()
                .map(bp -> {
                    return new BranchProductDto(bp.getId(), bp.getProduct().getName(), bp.getQuantity());
                })
                .toList();
    }

}
