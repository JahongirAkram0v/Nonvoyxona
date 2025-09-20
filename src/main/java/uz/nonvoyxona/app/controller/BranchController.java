package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import uz.nonvoyxona.app.model.*;
import uz.nonvoyxona.app.model.dto.ProductionDTO;
import uz.nonvoyxona.app.service.BakerService;
import uz.nonvoyxona.app.service.BranchService;
import uz.nonvoyxona.app.service.ProductService;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final SendController send;

    private final BranchService branchService;
    private final BakerService bakerService;
    private final ProductService productService;

    @MessageMapping("create.production")
    public void createProduction(@Header Integer branchId, @Payload ProductionDTO productionDTO) {

        Optional<Branch> optionalBranch = branchService.findById(branchId);
        if (optionalBranch.isEmpty()) {
            send.send("Branch not found", "/branch/" + branchId + "error/");
            return;
        }
        Branch branch = optionalBranch.get();

        Optional<Baker> optionalBaker = branch.getBakers().stream()
                .filter(baker -> baker.getId().equals(productionDTO.getBakerId()))
                .findAny();

        Optional<Product> optionalProduct = productService.findById(productionDTO.getProductId());

        if (optionalBaker.isEmpty() || optionalProduct.isEmpty()) {
            send.send("Baker or Product not found", "/branch/" + branchId + "error/");
            return;
        }

        Baker baker = optionalBaker.get();
        Product product = optionalProduct.get();

        Production production = Production.builder()
                .baker(baker)
                .name(product.getName())
                .price(product.getPrice())
                .quantity(productionDTO.getQuantity())
                .productionDateTime(LocalDateTime.now())
                .build();
        baker.getProductions().add(production);
        bakerService.save(baker);

        /// ///

        BranchProduct branchProduct = branch.getBranchProducts().stream()
                .filter(bp -> bp.getProduct().getId().equals(product.getId()))
                .findAny()
                .orElseGet(() -> {
                    BranchProduct newBranchProduct = new BranchProduct();
                    newBranchProduct.setBranch(branch);
                    newBranchProduct.setProduct(product);
                    branch.getBranchProducts().add(newBranchProduct);
                    return newBranchProduct;
                });
        branchProduct.setQuantity(branchProduct.getQuantity() + productionDTO.getQuantity());

        branch.getBranchProducts().add(branchProduct);
        branchService.save(branch);
        send.send(baker.getProductions(), "/admin/branch");
        send.send(baker.getProductions(), "/branch/" + branchId);
    }

}
