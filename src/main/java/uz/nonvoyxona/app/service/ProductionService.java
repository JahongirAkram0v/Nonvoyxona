package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Production;
import uz.nonvoyxona.app.repository.ProductRepo;
import uz.nonvoyxona.app.repository.ProductionRepo;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductionRepo productionRepo;


    public void save(Production production) {
        productionRepo.save(production);
    }
}
