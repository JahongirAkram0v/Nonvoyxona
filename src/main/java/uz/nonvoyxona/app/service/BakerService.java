package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Baker;
import uz.nonvoyxona.app.model.Branch;
import uz.nonvoyxona.app.model.dto.BakerDTO;
import uz.nonvoyxona.app.repository.BakerRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BakerService {

    private final BakerRepo bakerRepo;

    public void create(BakerDTO bakerDTO, Branch branch)  {
        Baker savedBaker = Baker.builder()
                .name(bakerDTO.getName())
                .branch(branch)
                .build();
        bakerRepo.save(savedBaker);
    }

    public List<Baker> findAll() {
        return bakerRepo.findAll();
    }

    public Optional<Baker> findById(Integer id) {
        return bakerRepo.findById(id);
    }
}
