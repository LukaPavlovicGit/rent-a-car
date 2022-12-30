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
    private Long id;
    private Integer rate;
    private String comment;
    private String companyName;
    private String city;

    public GetReviewDto(Integer rate, String comment, String companyName, String city) {
        this.rate = rate;
        this.comment = comment;
        this.companyName = companyName;
        this.city = city;
    }
}
