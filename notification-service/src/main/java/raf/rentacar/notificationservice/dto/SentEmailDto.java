package raf.rentacar.notificationservice.dto;

import java.sql.Date;

public class SentEmailDto {

    private Long id;
    private String type;
    private String message;
    private String destinationEmail;
    private Date dateSent;

    public SentEmailDto() { }

    public SentEmailDto(String type, String message, String destinationEmail, Date dateSent) {
        this.type = type;
        this.message = message;
        this.destinationEmail = destinationEmail;
        this.dateSent = dateSent;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDestinationEmail() {
        return destinationEmail;
    }

    public void setDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}
