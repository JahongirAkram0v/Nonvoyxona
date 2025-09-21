package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import uz.nonvoyxona.app.model.*;
import uz.nonvoyxona.app.model.dto.request.*;
import uz.nonvoyxona.app.model.state.UserRole;
import uz.nonvoyxona.app.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.nonvoyxona.app.model.state.UserRole.BRANCH;
import static uz.nonvoyxona.app.model.state.UserRole.COURIER;

@Controller
@MessageMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    //topic/{role} - userlar uchun
    //queue/{id} - bitta user uchun
    //app/ - userdan keladigan

    private final SendController send;

    private final BranchService branchService;
    private final UserService userService;
    private final ProductService productService;
    private final CourierService courierService;

    // TODO:CREATE
    @MessageMapping("create/branch")
    @Transactional
    public void createBranch(@Payload BranchDTO branchDTO){
        
        User user = getUser(branchDTO, BRANCH);

        Branch branch = Branch.builder()
                .name(branchDTO.getName())
                .address(branchDTO.getAddress())
                .user(user)
                .build();
        branchService.save(branch);
        //branchni adminga qanday yuboras ekan?
        send.send(branchService.getAll(), "/topic/admin/branch");
    }

    @MessageMapping("create/courier")
    @Transactional
    public void createCourier(@Payload CourierDTO courierDTO){

        User user = getUser(courierDTO, COURIER);

        Courier courier = Courier.builder()
                .name(courierDTO.getName())
                .phoneNumber(courierDTO.getPhoneNumber())
                .user(user)
                .build();

        courierService.save(courier);
        //courierni adminga bugungi malumotlar bilan yuboraman
        send.send(courierService.getAll(), "/topic/admin/courier");
    }

    private <T extends UserDTO> User getUser(T dto, UserRole userRole) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .userRole(userRole)
                .build();
        userService.save(user);
        return user;
    }

    @MessageMapping("create/bakery")
    public void createBakery(@Payload BakerDTO bakerDTO){

        Optional<Branch> optionalBranch = branchService.findById(bakerDTO.getBranchId());
        if (optionalBranch.isEmpty()){
            send.send("Branch not found", "/topic/admin/error");
        } else {
            Branch branch = optionalBranch.get();
            Baker baker = Baker.builder()
                    .name(bakerDTO.getName())
                    .branch(branch)
                    .build();
            branch.getBakers().add(baker);
            branchService.save(branch);
            send.send(branchService.getAll(), "/topic/admin/branch");
            send.send(branch, "queue/" + branch.getId() + "/bakery");
        }

    }

    @MessageMapping("create/product")
    public void createProduct(@Payload ProductDTO productDTO){

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();

        productService.save(product);
        send.send(productService.getAll(), "/topic/admin/product");
        send.send(productService.getAll(), "/topic/branch/product");
    }

    // boshlangich qiymatlarni olish uchun endpointlar yozishim kerak.

    @MessageMapping("create/delivery")
    public void createDelivery(@Payload DeliveryDTO deliveryDTO){

        Optional<Branch> optionalBranch = branchService.findById(deliveryDTO.getBranchId());
        if (optionalBranch.isEmpty()) {
            send.send("Branch not found", "/topic/admin/error/");
            return;
        }
        Branch branch = optionalBranch.get();

        List<DeliveryItem> deliveryItemList = new ArrayList<>();
        boolean hasError = false;
        long calculatedTotalPrice = 0L;

        // errorlarni tekshirish
        for (ItemDTO item: deliveryDTO.getItemDTOList()) {
            for (BranchProduct bp : branch.getBranchProducts()) {
                if (bp.getProduct().getId() != item.getProductId() || bp.getQuantity() < item.getQuantity()) {
                    hasError = true;
                    break;
                }
            }
        }

        if (hasError) {
            send.send("Some products are not available or insufficient quantity",
                    "/queue/" + deliveryDTO.getBranchId() + "/error/");
            return;
        }

        for (ItemDTO item: deliveryDTO.getItemDTOList()) {
            for (BranchProduct bp : branch.getBranchProducts()) {
                DeliveryItem deliveryItem = DeliveryItem.builder()
                        .name(bp.getProduct().getName())
                        .price(bp.getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build();
                deliveryItemList.add(deliveryItem);

                bp.setQuantity(bp.getQuantity() - item.getQuantity());
                branch.getBranchProducts().add(bp);
                calculatedTotalPrice += (long) bp.getProduct().getPrice() * item.getQuantity();
            }
        }

        if (calculatedTotalPrice != deliveryDTO.getTotalPrice()) {
            send.send("Total price is: " + calculatedTotalPrice,
                    "/queue/" + deliveryDTO.getBranchId() + "/error/");
        }

        Delivery delivery = Delivery.builder()
                .branch(branch)
                .deliveryItems(deliveryItemList)
                .clientName(deliveryDTO.getClientName())
                .clientPhoneNumber(deliveryDTO.getClientPhoneNumber())
                .clientAddress(deliveryDTO.getGetClientAddress())
                .totalPrice(calculatedTotalPrice)
                .orderDateTime(LocalDateTime.now())
                .build();
        branch.getDeliveries().add(delivery);
        branchService.save(branch);

        //adminga ozgartirib yuborishim kerak
        send.send(branch.getInStores(), "/topic/admin/delivery");

        send.send(branch.getInStores(), "/topic/courier");
        send.send(branch.getInStores(), "/queue/" + deliveryDTO.getBranchId() + "/delivery");
    }
}
