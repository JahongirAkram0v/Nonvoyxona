package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Order;
import uz.nonvoyxona.app.repository.OrderRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    public void save(Order order) {
        orderRepo.save(order);
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }
}
