package raf.rentacar.notificationservice.dto;

public class MessageTransferDto {

    private String firstName;
    private String lastname;
    private String link;
    private String car;
    private String destinationEmail;
    private String rezStart;

    public MessageTransferDto() { }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getDestinationEmail() {
        return destinationEmail;
    }

    public void setDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
    }

    public String getRezStart() {
        return rezStart;
    }

    public void setRezStart(String rezStart) {
        this.rezStart = rezStart;
    }
}
