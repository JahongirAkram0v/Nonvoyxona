package uz.nonvoyxona.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nonvoyxona.app.model.Courier;
import uz.nonvoyxona.app.model.Delivery;
import uz.nonvoyxona.app.service.CourierService;
import uz.nonvoyxona.app.service.DeliveryService;

import java.util.Optional;

@RestController
@RequestMapping("/courier")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;
    private final SendController send;
    private final DeliveryService deliveryService;

    @MessageMapping("update.delivery")
    public void updateDeliveryStatus(@Header Integer courierId, @Payload Integer deliveryId) {

        Optional<Courier> optionalCourier = courierService.findById(courierId);
        if (optionalCourier.isEmpty()) {
            send.send("Courier not found", "/courier/" + courierId + "error/");
            return;
        }
        Courier courier = optionalCourier.get();
        Optional<Delivery> optionalDelivery = deliveryService.findById(deliveryId);
        if (optionalDelivery.isEmpty()) {
            send.send("Delivery not found", "/courier/" + courierId + "error/");
            return;
        }
        Delivery delivery = optionalDelivery.get();
        if (delivery.getCourier() != null) {
            send.send("Delivery already assigned", "/courier/" + courierId + "error/");
            return;
        }

        delivery.setCourier(courier);
        courier.getDeliveries().add(delivery);
        courierService.save(courier);

    }
}
