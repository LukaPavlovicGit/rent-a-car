package raf.rentacar.userservice.dto;

public class MessageTransferDto {
    private String type;
    private String firstName;
    private String lastname;
    private String link;
    private String car;
    private String email;
    private String rezStart;

    public MessageTransferDto() { }

    public MessageTransferDto(String type, String firstName, String lastname, String link, String email){
        this.type = type;
        this.firstName = firstName;
        this.lastname = lastname;
        this.link = link;
        this.email = email;
    }
    public MessageTransferDto(String type, String firstName, String lastname, String link, String car, String email, String rezStart) {
        this.type = type;
        this.firstName = firstName;
        this.lastname = lastname;
        this.link = link;
        this.car = car;
        this.email = email;
        this.rezStart = rezStart;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRezStart() {
        return rezStart;
    }

    public void setRezStart(String rezStart) {
        this.rezStart = rezStart;
    }
}
