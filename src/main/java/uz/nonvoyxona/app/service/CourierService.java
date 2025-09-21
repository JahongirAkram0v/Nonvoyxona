package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Courier;
import uz.nonvoyxona.app.repository.CourierRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepo courierRepo;

    public void save(Courier courier) {
        courierRepo.save(courier);
    }

    public Optional<Courier> findById(int id) {
        return courierRepo.findById(id);
    }

    public List<Courier> getAll() {
        return courierRepo.findAll();
    }
}
