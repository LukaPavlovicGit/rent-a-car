package raf.rentacar.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetReviewDto {
    private Integer rate;
    private String comment;
    private String companyName;
    private String city;
}
