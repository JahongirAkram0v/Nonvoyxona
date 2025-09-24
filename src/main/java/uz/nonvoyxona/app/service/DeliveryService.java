package uz.nonvoyxona.app.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.Delivery;
import uz.nonvoyxona.app.model.dto.response.DeliveryDto;
import uz.nonvoyxona.app.model.dto.response.ItemDto;
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

    public List<DeliveryDto> findAllByBranchIdAndCourierIsNullDto(int branchId) {
        return findAllByBranchIdAndCourierIsNull(branchId)
                .stream()
                .map(d -> {
                    List<ItemDto> itemDtoList = d.getDeliveryItems()
                            .stream()
                            .map(i -> {
                                return new ItemDto(i.getId(), i.getName(), i.getPrice(), i.getQuantity());
                            })
                            .toList();
                    DeliveryDto dto = new DeliveryDto();
                    dto.setId(d.getId());
                    dto.setClientName(d.getClientName());
                    dto.setClientPhoneNumber(d.getClientPhoneNumber());
                    dto.setClientAddress(d.getClientAddress());
                    dto.setInStoreItems(itemDtoList);
                    dto.setTotalPrice(d.getTotalPrice());
                    return dto;
                })
                .toList();
    }
}
