package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nonvoyxona.app.model.*;
import uz.nonvoyxona.app.model.dto.BakerDTO;
import uz.nonvoyxona.app.repository.UserRepo;
import uz.nonvoyxona.app.service.*;

import java.util.List;
import java.util.Optional;

import static uz.nonvoyxona.app.model.state.UserRole.ADMIN;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BranchService branchService;
    private final ProductService productService;
    private final CourierService courierService;
    private final OrderService orderService;
    private final UserRepo userRepo;

    // buni alohida qilaman
    @GetMapping("/get/key")
    public ResponseEntity<String> getKey(Users user) {

        Optional<Users> optionalUsers = userRepo.findByLogin(user.getLogin());
        if (optionalUsers.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            Users users = optionalUsers.get();
            if (!users.getPasswordHash().equals(user.getPasswordHash())) {
                return ResponseEntity.badRequest().body("Password is incorrect!");
            } else {
                if (!users.getUserRole().equals(ADMIN)) {
                    return ResponseEntity.badRequest().build();
                } else {
                    return ResponseEntity.ok(users.getId());
                }
            }
        }
    }

    // TODO:CREATE
    @PostMapping("/create/branch")
    public ResponseEntity<Void> createBranch(@RequestBody Branch branch){
        branchService.save(branch);
        return ResponseEntity.ok().build();
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
        return ResponseEntity.ok(branchService.findAll());
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
