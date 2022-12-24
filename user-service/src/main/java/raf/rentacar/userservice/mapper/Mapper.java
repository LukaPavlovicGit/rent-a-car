package raf.rentacar.userservice.mapper;

import org.springframework.stereotype.Component;
import raf.rentacar.userservice.domain.Rank;
import raf.rentacar.userservice.domain.Role;
import raf.rentacar.userservice.domain.User;
import raf.rentacar.userservice.dto.ClientDto;
import raf.rentacar.userservice.dto.ManagerDto;
import raf.rentacar.userservice.dto.UserDto;

import javax.persistence.ManyToOne;
import java.sql.Date;

@Component
public class Mapper {

    public UserDto userToUserDto(User user){
        return new UserDto(user.getId(),user.getUsername(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getEmail(),
                user.getPhoneNumber(),user.getBirthdate(),user.getRole(),user.isActivated(),user.getCompanyName(),
                user.getHireDate(),user.getPassport(),user.getTotalDays(),user.isForbidden());
    }

    public User userDtoToUser(UserDto userDto){
        return null;
    }

    public User clientDtoToUser(ClientDto clientDto){
        return new User(clientDto.getUsername(),clientDto.getUsername(),clientDto.getFirstName(),clientDto.getLastName(),
                clientDto.getEmail(),clientDto.getPhoneNumber(),clientDto.getBirthdate(),clientDto.getPassport());
    }

    public User managerDtoToUser(ManagerDto managerDto){
        return new User(managerDto.getUsername(),managerDto.getPassword(),managerDto.getFirstName(),managerDto.getLastName(),
                managerDto.getEmail(),managerDto.getPhoneNumber(),managerDto.getBirthdate(),managerDto.getCompanyName(),managerDto.getHireDate());
    }

}