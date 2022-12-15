package raf.rentacar.notificationservice.dto;

import java.sql.Date;

public class SentEmailDto {

    private Long id;
    private String type;
    private String text;
    private String email;
    private Date dateSent;

    public SentEmailDto() { }

    public SentEmailDto(String type, String text, String email, Date dateSent) {
        this.type = type;
        this.text = text;
        this.email = email;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}
