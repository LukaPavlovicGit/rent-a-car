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
public class AvailableVehiclesFilterDto {

    private String companyName;
    private String city;
    //yyyy-MM-dd
    private Date start;
    //yyyy-MM-dd
    private Date end;

}
