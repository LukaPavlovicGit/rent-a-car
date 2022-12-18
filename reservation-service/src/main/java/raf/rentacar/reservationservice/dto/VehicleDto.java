package raf.rentacar.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private Long id;
    private String type;
    private String name;
    private Double price;

    public VehicleDto(String type, String name, Double price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }
}
