package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Branch;
import uz.nonvoyxona.app.repository.BranchRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepo branchRepo;

    public void create(Branch branch) {
        branchRepo.save(branch);
    }

    public List<Branch> findAll(){
        return branchRepo.findAll();
    }

    public Optional<Branch> findById(Integer id) {
        return branchRepo.findById(id);
    }
}
