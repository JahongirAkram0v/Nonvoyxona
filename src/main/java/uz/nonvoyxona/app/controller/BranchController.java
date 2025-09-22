package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import uz.nonvoyxona.app.model.*;
import uz.nonvoyxona.app.model.dto.request.DeliveryUpdateDTO;
import uz.nonvoyxona.app.model.dto.request.InStoreDTO;
import uz.nonvoyxona.app.model.dto.request.ItemDTO;
import uz.nonvoyxona.app.model.dto.request.ProductionDTO;
import uz.nonvoyxona.app.model.state.DeliveryStatus;
import uz.nonvoyxona.app.service.BakerService;
import uz.nonvoyxona.app.service.BranchService;
import uz.nonvoyxona.app.service.ProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.nonvoyxona.app.model.state.DeliveryStatus.*;

@RestController
@MessageMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final SendController send;

    private final BranchService branchService;
    private final BakerService bakerService;
    private final ProductService productService;

    @MessageMapping("create/production")
    public void createProduction(@Payload ProductionDTO productionDTO) {

        Optional<Branch> optionalBranch = branchService.findById(productionDTO.getBranchId());
        if (optionalBranch.isEmpty()) {
            send.send("Branch not found: " + productionDTO.getBranchId(), "/topic/admin/error/");
            return;
        }
        Branch branch = optionalBranch.get();

        Optional<Baker> optionalBaker = branch.getBakers().stream()
                .filter(baker -> baker.getId().equals(productionDTO.getBakerId()))
                .findAny();

        Optional<Product> optionalProduct = productService.findById(productionDTO.getProductId());

        if (optionalBaker.isEmpty() || optionalProduct.isEmpty()) {
            send.send("Baker or Product not found", "/queue/" + productionDTO.getBranchId() + "/error/");
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
        send.send(baker.getProductions(), "/topic/admin/production");
        send.send(baker.getProductions(), "/queue/" + productionDTO.getBranchId()+"/production");
    }

    @MessageMapping("create/inStore")
    public void createInStore(@Payload InStoreDTO inStoreDTO) {

        Optional<Branch> optionalBranch = branchService.findById(inStoreDTO.getBranchId());
        if (optionalBranch.isEmpty()) {
            send.send("Branch not found: " + inStoreDTO.getBranchId(), "/topic/admin/error/");
            return;
        }
        Branch branch = optionalBranch.get();

        List<InStoreItem> inStoreItemList = new ArrayList<>();
        boolean hasError = false;
        long calculatedTotalPrice = 0L;

        // errorlarni tekshirish
        for (ItemDTO item: inStoreDTO.getItemDTOList()) {
            for (BranchProduct bp : branch.getBranchProducts()) {
                if (bp.getProduct().getId() != item.getProductId() || bp.getQuantity() < item.getQuantity()) {
                    hasError = true;
                    break;
                }
            }
        }

        if (hasError) {
            send.send("Some products are not available or insufficient quantity",
                    "/queue/" + inStoreDTO.getBranchId() + "/error/");
            return;
        }

        for (ItemDTO item: inStoreDTO.getItemDTOList()) {
            for (BranchProduct bp : branch.getBranchProducts()) {
                InStoreItem inStoreItem = InStoreItem.builder()
                        .name(bp.getProduct().getName())
                        .price(bp.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build();
                inStoreItemList.add(inStoreItem);

                bp.setQuantity(bp.getQuantity() - item.getQuantity());
                branch.getBranchProducts().add(bp);
                calculatedTotalPrice += (long) bp.getProduct().getPrice() * item.getQuantity();
            }
        }

        if (calculatedTotalPrice != inStoreDTO.getTotalPrice()) {
            send.send("Total price is: " + calculatedTotalPrice,
                    "/queue/" + inStoreDTO.getBranchId() + "/error/");
        }

        InStore inStore = InStore.builder()
                .branch(branch)
                .orderDateTime(LocalDateTime.now())
                .inStoreItems(inStoreItemList)
                .totalPrice(calculatedTotalPrice)
                .build();
        branch.getInStores().add(inStore);
        branchService.save(branch);

        //adminga ozgartirib yuborishim kerak
        send.send(branch.getInStores(), "/topic/admin/branch");

        send.send(branch.getInStores(), "/queue/" + inStoreDTO.getBranchId()+"/inStore");
    }

    //update delivery qilishim kerak
    @MessageMapping("update/delivery")
    public void updateDelivery(@Payload DeliveryUpdateDTO deliveryUpdateDTO) {
        Optional<Branch> optionalBranch = branchService.findById(deliveryUpdateDTO.getId());
        if (optionalBranch.isEmpty()) {
            send.send("Branch not found: " + deliveryUpdateDTO.getId(), "/topic/admin/error/");
            return;
        }

        Branch branch = optionalBranch.get();
        Optional<Delivery> optionalDelivery = branch.getDeliveries().stream()
                .filter(delivery -> delivery.getId() == deliveryUpdateDTO.getDeliveryId())
                .findAny();

        if (optionalDelivery.isEmpty()) {
            send.send("Delivery not found: " + deliveryUpdateDTO.getDeliveryId(),
                    "/queue/" + deliveryUpdateDTO.getId() + "/error/");
            return;
        }
        Delivery delivery = optionalDelivery.get();
        DeliveryStatus currentStatus = delivery.getDeliveryStatus();

        if (currentStatus == CREATED) {
            delivery.setDeliveryStatus(PREPARED);
        } else {
            delivery.setDeliveryStatus(READY);
        }

        branch.getDeliveries().add(delivery);
        branchService.save(branch);

        send.send(branch.getDeliveries(),    "/topic/admin/delivery");
        send.send(branch.getDeliveries(), "/queue/" + deliveryUpdateDTO.getId() + "/delivery");
    }

}
