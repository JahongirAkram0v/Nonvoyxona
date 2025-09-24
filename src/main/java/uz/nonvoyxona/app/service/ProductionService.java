package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Production;
import uz.nonvoyxona.app.model.dto.response.ProductionDto;
import uz.nonvoyxona.app.repository.ProductionRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductionRepo productionRepo;


    public List<Production> findAllByBranchIdToday(int branchId) {
        return productionRepo.findAllByBranchIdToday(branchId);
    }

    public List<ProductionDto> findAllByBranchIdTodayDto(int branchId) {
        return findAllByBranchIdToday(branchId)
                .stream()
                .map(p -> {
                    return ProductionDto.builder()
                            .id(p.getId())
                            .productName(p.getName())
                            .bakerName(p.getBaker().getName())
                            .quantity(p.getQuantity())
                            .productionDateTime(p.getProductionDateTime())
                            .build();
                })
                .toList();
    }
}
