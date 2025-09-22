package uz.nonvoyxona.app.model.dto.response;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ProductionDto {

    private int id;
    private String bakerName;
    private String productName;
    private int quantity;
    private LocalDateTime productionDateTime;
}
