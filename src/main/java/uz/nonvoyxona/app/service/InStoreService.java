package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.InStore;
import uz.nonvoyxona.app.repository.InStoreRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InStoreService {

    private final InStoreRepo inStoreRepo;


    public List<InStore> findAllByBranchIdToday(int branchId) {
        return inStoreRepo.findAllByBranchIdToday(branchId);
    }
}
