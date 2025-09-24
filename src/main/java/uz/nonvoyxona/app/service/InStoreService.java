package uz.nonvoyxona.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nonvoyxona.app.model.InStore;
import uz.nonvoyxona.app.model.dto.response.InStoreDto;
import uz.nonvoyxona.app.model.dto.response.ItemDto;
import uz.nonvoyxona.app.repository.InStoreRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InStoreService {

    private final InStoreRepo inStoreRepo;


    public List<InStore> findAllByBranchIdToday(int branchId) {
        return inStoreRepo.findAllByBranchIdToday(branchId);
    }

    public List<InStoreDto> findAllByBranchIdTodayDto(int branchId) {
        return findAllByBranchIdToday(branchId)
                .stream()
                .map(inStore -> {
                    List<ItemDto> itemDtoList = inStore.getInStoreItems()
                            .stream()
                            .map(i -> {
                                return new ItemDto(i.getId(), i.getName(), i.getPrice(), i.getQuantity());
                            })
                            .toList();
                    InStoreDto inStoreDto = new InStoreDto();
                    inStoreDto.setId(inStore.getId());
                    inStoreDto.setInStoreItems(itemDtoList);
                    inStoreDto.setTotalPrice(inStore.getTotalPrice());
                    return inStoreDto;
                })
                .toList();
    }
}
