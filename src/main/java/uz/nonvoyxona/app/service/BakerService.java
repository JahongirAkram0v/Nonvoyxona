package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Baker;
import uz.nonvoyxona.app.repository.BakerRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BakerService {

    private final BakerRepo bakerRepo;

    public void save(Baker baker) {
        bakerRepo.save(baker);
    }

    public List<Baker> findAllByBranchId(int branchId) {
        return bakerRepo.findAllByBranchId(branchId);
    }

}
