package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nonvoyxona.app.model.Branch;
import uz.nonvoyxona.app.service.BranchService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/branchw")
@RequiredArgsConstructor
public class BranchControllerw {

    private final BranchService branchService;

    //ADMIN ROLE
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Branch branch) {
        branchService.save(branch);
        return ResponseEntity.ok().build();
    }

    //BAKERY ROLE
    @GetMapping("/{id}")
    public ResponseEntity<Branch> findById(@PathVariable Integer id) {
        Optional<Branch> optionalBranch = branchService.findById(id);
        return optionalBranch
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //ADMIN ROLE
    @GetMapping
    public List<Branch> findAll() {
        return branchService.findAll();
    }


}
