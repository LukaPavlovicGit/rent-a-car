package raf.rentacar.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientDto {

    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthdate;
    private String passport;

}