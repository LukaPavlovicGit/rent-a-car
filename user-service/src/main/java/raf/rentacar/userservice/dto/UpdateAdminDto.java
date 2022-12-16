package raf.rentacar.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.rentacar.userservice.domain.Role;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdminDto {

    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date birthdate;
}
