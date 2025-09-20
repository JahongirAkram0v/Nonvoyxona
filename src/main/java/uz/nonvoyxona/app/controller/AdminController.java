package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.nonvoyxona.app.model.*;
import uz.nonvoyxona.app.model.dto.request.*;
import uz.nonvoyxona.app.model.state.UserRole;
import uz.nonvoyxona.app.service.*;

import java.util.Optional;

import static uz.nonvoyxona.app.model.state.UserRole.BRANCH;
import static uz.nonvoyxona.app.model.state.UserRole.COURIER;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private SendController send;

    private final BranchService branchService;
    private final UserService userService;
    private final ProductService productService;
    private final CourierService courierService;

    // TODO:CREATE
    @MessageMapping("create.branch")
    @Transactional
    public void createBranch(@Payload BranchDTO branchDTO){
        
        User user = getUser(branchDTO, BRANCH);

        Branch branch = Branch.builder()
                .name(branchDTO.getName())
                .address(branchDTO.getAddress())
                .user(user)
                .build();
        branchService.save(branch);

        send.send(branchService.getAll(), "/admin/branch");
    }

    @MessageMapping("create.courier")
    @Transactional
    public void createCourier(@Payload CourierDTO courierDTO){

        User user = getUser(courierDTO, COURIER);

        Courier courier = Courier.builder()
                .name(courierDTO.getName())
                .phoneNumber(courierDTO.getPhoneNumber())
                .user(user)
                .build();

        courierService.save(courier);

        send.send(courierService.getAll(), "admin/courier");
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

    @MessageMapping("create.bakery")
    public void createBakery(@Payload BakerDTO bakerDTO){

        Optional<Branch> optionalBranch = branchService.findById(bakerDTO.getBranchId());
        if (optionalBranch.isEmpty()){
            send.send("Branch not found", "/admin/error");
        } else {
            Branch branch = optionalBranch.get();
            Baker baker = Baker.builder()
                    .name(bakerDTO.getName())
                    .branch(branch)
                    .build();
            branch.getBakers().add(baker);
            branchService.save(branch);
            send.send(branchService.getAll(), "/admin/branch");
            send.send(branch, "/branch/" + branch.getId());
        }

    }

    @MessageMapping("create.product")
    public void createProduct(@Payload ProductDTO productDTO){

        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();

        productService.save(product);
        send.send(productService.getAll(), "admin/product");
        send.send(productService.getAll(), "/branch");
    }

    // boshlangich qiymatlarni olish uchun endpointlar yozishim kerak.

}
