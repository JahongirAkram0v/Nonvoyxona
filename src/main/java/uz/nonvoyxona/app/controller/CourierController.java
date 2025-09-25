package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import uz.nonvoyxona.app.model.Courier;
import uz.nonvoyxona.app.model.Delivery;
import uz.nonvoyxona.app.model.dto.request.DeliveryUpdateDTO;
import uz.nonvoyxona.app.model.state.DeliveryStatus;
import uz.nonvoyxona.app.service.CourierService;
import uz.nonvoyxona.app.service.DeliveryService;

import java.util.Optional;

@RestController
@MessageMapping("/courier")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;
    private final SendController send;
    private final DeliveryService deliveryService;

    @MessageMapping("/get/delivery")
    public void getDelivery(@Payload int courierId) {
        send.send(deliveryService.findAllByBranchIdForCourierDto(courierId),
                "/queue/"+courierId+"/delivery");
    }

    @MessageMapping("/get/myDelivery")
    public void getMyDelivery(@Payload int courierId) {
        send.send(deliveryService.findAllByBranchIdForMyCourierDtoToday(courierId),
                "/queue/"+courierId+"/MyDelivery");
    }

    @MessageMapping("update/delivery")
    public void updateDeliveryStatus(@Payload DeliveryUpdateDTO deliveryUpdateDTO) {

        Optional<Courier> optionalCourier = courierService.findById(deliveryUpdateDTO.getId());
        if (optionalCourier.isEmpty()) {
            send.send("Courier not found", "/topic/admin/error");
            return;
        }
        Courier courier = optionalCourier.get();
        Optional<Delivery> optionalDelivery = deliveryService.findById(deliveryUpdateDTO.getDeliveryId());
        if (optionalDelivery.isEmpty()) {
            send.send("Delivery not found", "/queue/" + deliveryUpdateDTO.getId() + "error");
            return;
        }
        Delivery delivery = optionalDelivery.get();

        if (delivery.getCourier() == null){
            delivery.setCourier(courier);
            courier.getDeliveries().add(delivery);
            courierService.save(courier);
        }

        if (delivery.getCourier().getId() != courier.getId()) {
            send.send("Delivery already assigned",
                    "/queue/" + deliveryUpdateDTO.getId() + "/error");
            return;
        }

        switch (delivery.getDeliveryStatus()) {
            case READY -> {
                delivery.setDeliveryStatus(DeliveryStatus.IN_DELIVERY);
                deliveryService.save(delivery);
            }
            case IN_DELIVERY -> {
                delivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
                deliveryService.save(delivery);
            }
            default -> {
                send.send("Invalid status transition",
                        "/queue/" + deliveryUpdateDTO.getId() + "/error");
            }
        }

        // hammaga yuborishim kerak
        send.send(deliveryService.findAllByBranchIdForBranchDto(delivery.getBranch().getId()),
                "/queue/"+delivery.getBranch().getId()+"/delivery");

        send.send(deliveryService.findAllByBranchIdForCourierDto(courier.getId()),
                "/queue/"+courier.getId()+"/delivery");

        send.send(deliveryService.findAllByBranchIdForMyCourierDtoToday(courier.getId()),
                "/queue/"+courier.getId()+"/myDelivery");

    }
}
