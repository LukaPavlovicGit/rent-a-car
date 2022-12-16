package raf.rentacar.notificationservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class SentEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String destinationEmail;
    private String subject;
    private String message;
    private Date dateSent;

    public SentEmail() { }

    public SentEmail(String type, String subject, String message, String destinationEmail, Date dateSent) {
        this.type = type;
        this.subject = subject;
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

    public String getDestinationEmail() {
        return destinationEmail;
    }

    public void setDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}
