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
public class UpdateManagerDto {

    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthdate;
}
