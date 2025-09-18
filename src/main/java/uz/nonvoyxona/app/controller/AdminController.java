package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nonvoyxona.app.model.*;
import uz.nonvoyxona.app.model.dto.BakerDTO;
import uz.nonvoyxona.app.service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BranchService branchService;
    private final ProductService productService;
    private final CourierService courierService;
    private final OrderService orderService;

    // TODO:CREATE
    @PostMapping("/create/branch")
    public ResponseEntity<Void> createBranch(Branch branch){
        branchService.save(branch);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/bakery")
    public ResponseEntity<Void> createBakery(BakerDTO bakerDTO){
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
    public ResponseEntity<Void> createProduct(Product product){
        productService.save(product);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/courier")
    public ResponseEntity<Void> createCourier(Courier courier){
        courierService.save(courier);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/order")
    public ResponseEntity<Void> createOrder(Order order){
        orderService.save(order);
        return ResponseEntity.ok().build();
    }

    //TODO: Getlar
    @GetMapping("/get/branch")
    public ResponseEntity<List<Integer>> getAllBranchIds() {
        return ResponseEntity.ok(branchService.findAllIds());
    }

    @GetMapping("/get/branch/{id}")
    public ResponseEntity<Branch> getBranchById(Integer id){
        Optional<Branch> optionalBranch = branchService.findById(id);
        return optionalBranch
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/courier")
    public ResponseEntity<List<Courier>> getAllCouriers(){
        return ResponseEntity.ok(courierService.findAll());
    }

    @GetMapping("/get/order")
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(orderService.findAll());
    }


}
