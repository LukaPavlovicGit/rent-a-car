package raf.rent.a.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAverageRate {

    private String companyName;
    private Double averageRate;
    private Integer numberOfReviews;
}
