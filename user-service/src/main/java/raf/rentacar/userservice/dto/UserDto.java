package raf.rentacar.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raf.rentacar.userservice.domain.Rank;
import raf.rentacar.userservice.domain.Role;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date birthdate;
    private Role role;
    private Boolean activated;

    //manager
    private String companyName;
    private Date hireDate;

    //user
    private String passport;
    private Integer totalDays;
    private Boolean forbidden = false;
    private Rank rank;


}
