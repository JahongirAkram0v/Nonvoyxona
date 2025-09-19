package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Order1;
import uz.nonvoyxona.app.repository.OrderRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    public void save(Order1 order1) {
        orderRepo.save(order1);
    }

    public List<Order1> findAll() {
        return orderRepo.findAll();
    }
}
