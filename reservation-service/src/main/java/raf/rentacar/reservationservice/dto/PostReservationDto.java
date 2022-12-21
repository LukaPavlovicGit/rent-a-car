package raf.rentacar.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReservationDto {

    private Long vehicleId;
    //yyyy-MM-dd
    private Date start;
    //yyyy-MM-dd
    private Date end;
}
