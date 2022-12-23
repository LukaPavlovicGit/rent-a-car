package raf.rentacar.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageTransferDto {

    private String firstName;
    private String lastname;
    private String link;
    private String car;
    private String destinationEmail;
    private String rezStart;

    // account_activation
    public MessageTransferDto(String firstName, String lastname, String link, String destinationEmail){
        this.firstName = firstName;
        this.lastname = lastname;
        this.link = link;
        this.destinationEmail = destinationEmail;
    }

    // change_password
    public MessageTransferDto(String firstName, String lastname, String destinationEmail) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.destinationEmail = destinationEmail;
    }

    // reservation_successful
    public MessageTransferDto(String firstName, String lastname, String car, String destinationEmail, boolean x){
        this.firstName = firstName;
        this.lastname = lastname;
        this.car = car;
        this.destinationEmail = destinationEmail;
    }

    // reservation_cancellation
    public MessageTransferDto(String firstName, String lastname, String car, String destinationEmail, Integer x){
        this.firstName = firstName;
        this.lastname = lastname;
        this.car = car;
        this.destinationEmail = destinationEmail;
    }

    // reservation_reminder
    public MessageTransferDto(String firstName, String lastname, String car, String destinationEmail, String rezStart) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.car = car;
        this.destinationEmail = destinationEmail;
        this.rezStart = rezStart;
    }
}
