package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Delivery;
import uz.nonvoyxona.app.repository.DeliveryRepo;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepo deliveryRepo;

    public void save(Delivery delivery) {
        deliveryRepo.save(delivery);
    }

    public Optional<Delivery> findById(int id) {
        return deliveryRepo.findById(id);
    }

    public List<Delivery> getAll() {
        return deliveryRepo.findAll();
    }

    public List<Delivery> findAllByBranchIdAndCourierIsNull(int branchId) {
        return deliveryRepo.findAllByBranchIdAndCourierIsNull(branchId);
    }
}
