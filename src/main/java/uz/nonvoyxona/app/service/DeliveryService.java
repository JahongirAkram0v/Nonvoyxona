package uz.nonvoyxona.app.service;

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

    public List<Delivery> findAllByBranchIdForBranch(int branchId) {
        return deliveryRepo.findAllByBranchIdForBranch(branchId);
    }

    public List<Delivery> findAllByBranchIdForCourier(int branchId) {
        return deliveryRepo.findAllByBranchIdForCourier(branchId);
    }

    public List<DeliveryDto> findAllByBranchIdForBranchDto(int branchId) {
        return findAllByBranchIdForBranch(branchId)
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
                    dto.setCourierName(d.getCourier().getName());
                    dto.setClientName(d.getClientName());
                    dto.setClientPhoneNumber(d.getClientPhoneNumber());
                    dto.setClientAddress(d.getClientAddress());
                    dto.setInStoreItems(itemDtoList);
                    dto.setTotalPrice(d.getTotalPrice());
                    return dto;
                })
                .toList();
    }

    public List<DeliveryDto> findAllByBranchIdForCourierDto(int courierId) {
        return findAllByBranchIdForCourier(courierId)
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

    public List<DeliveryDto> findAllByBranchIdForMyCourierDtoToday(int courierId) {
        return deliveryRepo.findAllByBranchIdForMyCourierDtoToday(courierId)
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
                    dto.setClientPhoneNumber(d.getClientPhoneNumber());
                    dto.setClientAddress(d.getClientAddress());
                    dto.setInStoreItems(itemDtoList);
                    dto.setTotalPrice(d.getTotalPrice());
                    return dto;
                })
                .toList();
    }
}
