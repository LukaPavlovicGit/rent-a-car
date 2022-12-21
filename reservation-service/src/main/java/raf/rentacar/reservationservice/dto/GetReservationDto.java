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
public class GetReservationDto {
    private Long id;
    private Long userId;
    private String userMail;
    private Long companyId;
    private Long vehicleId;
    //yyyy-MM-dd
    private Date start;
    //yyyy-MM-dd
    private Date end;
    private Double totalPrice;
    private boolean canceled;

    public GetReservationDto(Long vehicleId, Date start, Date end) {
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
    }

    public GetReservationDto(String userMail, Long vehicleId, Date start, Date end) {
        this.userMail = userMail;
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
    }

    public GetReservationDto(Long userId, String userMail, Long companyId, Long vehicleId, Date start, Date end) {
        this.userId = userId;
        this.userMail = userMail;
        this.companyId = companyId;
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
    }
}
