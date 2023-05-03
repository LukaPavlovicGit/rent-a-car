package raf.rent.a.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReviewDto {

    private Long companyId;
    private Integer rate;
    private String comment;

    public PostReviewDto(Integer rate, String comment) {
        this.rate = rate;
        this.comment = comment;
    }
}
