package raf.rent.a.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SentEmailDto {

    private String type;
    private String destinationEmail;
    private String subject;
    private String message;
    private Date dateSent;
}
