package uz.nonvoyxona.app.model.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ProductionDto {

    private int id;
    private String bakerName;
    private String productName;
    private int quantity;
    private LocalDateTime productionDateTime;
}
