package raf.rentacar.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SentEmailFilterDto {

    private String type;
    private String email;
    private Date lowerBound;
    private Date upperBound;
}
