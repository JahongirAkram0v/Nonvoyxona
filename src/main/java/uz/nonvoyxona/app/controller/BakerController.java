package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nonvoyxona.app.model.Baker;
import uz.nonvoyxona.app.model.Branch;
import uz.nonvoyxona.app.model.dto.BakerDTO;
import uz.nonvoyxona.app.service.BakerService;
import uz.nonvoyxona.app.service.BranchService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/baker")
@RequiredArgsConstructor
public class BakerController {

    private final BakerService bakerService;
    private final BranchService branchService;

    //ADMIN ROLE
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody BakerDTO bakerDTO) {

        Optional<Branch> optionalBranch = branchService.findById(bakerDTO.getBranchId());
        if (optionalBranch.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        bakerService.create(bakerDTO, optionalBranch.get());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Baker> findById(@PathVariable Integer id) {
        Optional<Baker> optionalBaker = bakerService.findById(id);
        return optionalBaker
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Baker> findAll() {
        return bakerService.findAll();
    }
}
