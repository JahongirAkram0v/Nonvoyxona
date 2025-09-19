package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nonvoyxona.app.model.*;
import uz.nonvoyxona.app.model.dto.OrderDTO;
import uz.nonvoyxona.app.model.dto.ProductionDTO;
import uz.nonvoyxona.app.service.BakerService;
import uz.nonvoyxona.app.service.BranchService;
import uz.nonvoyxona.app.service.ProductService;
import uz.nonvoyxona.app.service.ProductionService;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;
    private final BakerService bakerService;
    private final ProductService productService;
    private final ProductionService productionService;

    @PostMapping("/create/production")
    public ResponseEntity<Void> createProduction(@RequestParam Integer branchId, @RequestBody ProductionDTO productionDTO) {

        Optional<Branch> optionalBranch = branchService.findById(branchId);
        if (optionalBranch.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Branch branch = optionalBranch.get();

        Optional<Baker> optionalBaker = branch.getBakers().stream()
                .filter(baker -> baker.getId() == productionDTO.getBakerId())
                .findAny();

        Optional<Product> optionalProduct = productService.findById(productionDTO.getProductId());
        if (optionalBaker.isEmpty() || optionalProduct.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Baker baker = optionalBaker.get();
        Product product = optionalProduct.get();


        Production production = Production.builder()
                .product(optionalProduct.get())
                .baker(baker)
                .productionDateTime(LocalDateTime.now())
                .quantity(productionDTO.getQuantity())
                .build();
        baker.getProductions().add(production);
        bakerService.save(baker);

        Optional<BranchProduct> optionalBranchProduct = branch.getBranchProducts().stream()
                .filter(branchProduct -> branchProduct.getProduct().getId() == product.getId())
                .findAny();
        BranchProduct branchProduct = optionalBranchProduct.orElseGet(BranchProduct::new);
        branchProduct.setBranch(branch);
        branchProduct.setProduct(product);
        branchProduct.setQuantity(branchProduct.getQuantity() + productionDTO.getQuantity());
        branch.getBranchProducts().add(branchProduct);
        branchService.save(branch);
        //adminga ham xabar berish kerak
        //branchga ham
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/order")
    public ResponseEntity<Void> crateOrder(@RequestParam Integer branchId, @RequestBody OrderDTO orderDTO){
        Optional<Branch> optionalBranch = branchService.findById(branchId);
        if (optionalBranch.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Branch branch = optionalBranch.get();



        return ResponseEntity.ok().build();
    }


}
