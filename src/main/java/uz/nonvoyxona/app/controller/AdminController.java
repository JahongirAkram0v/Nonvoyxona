package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.nonvoyxona.app.model.*;
import uz.nonvoyxona.app.model.dto.BakerDTO;
import uz.nonvoyxona.app.model.dto.BranchDTO;
import uz.nonvoyxona.app.model.dto.UserDTO;
import uz.nonvoyxona.app.model.state.UserRole;
import uz.nonvoyxona.app.service.*;

import java.util.List;
import java.util.Optional;

import static uz.nonvoyxona.app.model.state.UserRole.BRANCH;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SimpMessagingTemplate messagingTemplate;

    private final BranchService branchService;
    private final UserService userService;
    private final ProductService productService;
    private final CourierService courierService;
    private final OrderService orderService;

    // TODO:CREATE
    @MessageMapping("create.branch")
    @Transactional
    public void createBranch(@Payload BranchDTO branchDTO){
        /*
        *
        if (headerAccessor.getSessionAttributes() == null || headerAccessor.getSessionAttributes().isEmpty()) {
            return ResponseEntity.status(403).build();
        }
        UserRole userRole = (UserRole) headerAccessor.getSessionAttributes().get("userRole");
        if (userRole != ADMIN) {
            return ResponseEntity.status(403).build();
        }
        */
        User user = getUser(branchDTO, BRANCH);

        Branch branch = Branch.builder()
                .name(branchDTO.getName())
                .address(branchDTO.getAddress())
                .user(user)
                .build();
        branchService.save(branch);

        userService.getAllAdminIds().forEach(
                id -> messagingTemplate.convertAndSend(
                        "/topic/admin/" + id,
                        branchService.getAll()
                )
        );
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

    @PostMapping("/create/bakery")
    public ResponseEntity<Void> createBakery(@RequestBody BakerDTO bakerDTO){
        Optional<Branch> optionalBranch = branchService.findById(bakerDTO.getBranchId());
        if (optionalBranch.isEmpty()){
            return ResponseEntity.badRequest().build();
        } else {
            Branch branch = optionalBranch.get();
            Baker savedBaker = Baker.builder()
                    .name(bakerDTO.getName())
                    .branch(branch)
                    .build();
            branch.getBakers().add(savedBaker);
            branchService.save(branch);
            return ResponseEntity.ok().build();
        }

    }

    @PostMapping("/create/product")
    public ResponseEntity<Void> createProduct(@RequestBody Product product){
        productService.save(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/courier")
    public ResponseEntity<Void> createCourier(@RequestBody Courier courier){
        courierService.save(courier);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/order")
    public ResponseEntity<Void> createOrder(@RequestBody Order1 order1){
        orderService.save(order1);
        return ResponseEntity.ok().build();
    }

    //TODO: Getlar
    @GetMapping("/get/branch")
    public ResponseEntity<List<Branch>> getAllBranchIds() {
        return ResponseEntity.ok(branchService.getAll());
    }

    @GetMapping("/get/courier")
    public ResponseEntity<List<Courier>> getAllCouriers(){
        return ResponseEntity.ok(courierService.findAll());
    }

    @GetMapping("/get/order")
    public ResponseEntity<List<Order1>> getAllOrders(){
        return ResponseEntity.ok(orderService.findAll());
    }


}
