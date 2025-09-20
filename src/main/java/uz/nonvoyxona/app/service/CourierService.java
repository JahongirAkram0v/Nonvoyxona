package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Courier;
import uz.nonvoyxona.app.repository.CourierRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepo courierRepo;

    public void save(Courier courier) {
        courierRepo.save(courier);
    }

    public List<Courier> getAll() {
        return courierRepo.findAll();
    }
}
