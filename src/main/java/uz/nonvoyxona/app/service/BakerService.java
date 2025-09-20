package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Baker;
import uz.nonvoyxona.app.repository.BakerRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BakerService {

    private final BakerRepo bakerRepo;

    public void save(Baker baker) {
        bakerRepo.save(baker);
    }

    public boolean existsById(Integer id) {
        return bakerRepo.existsById(id);
    }

    public Optional<Baker> findById(Integer id) {
        return bakerRepo.findById(id);
    }

    public List<Baker> findAll() {
        return bakerRepo.findAll();
    }

    public List<Baker> findAllWithProductions() {
        System.out.println(bakerRepo.findAllWithProductions());
        return bakerRepo.findAllWithProductions();
    }

}
