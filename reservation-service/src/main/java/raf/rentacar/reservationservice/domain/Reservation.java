package raf.rentacar.reservationservice.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer vehicleId;
    private Date start;
    private Date end;
    private Double totalPrice;
    private Boolean sentReminder;

    public Reservation(){

    }

    public Reservation(Integer vehicleId, Date start, Date end, Double totalPrice, Boolean sentReminder) {
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
        this.totalPrice = totalPrice;
        this.sentReminder = sentReminder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getSentReminder() {
        return sentReminder;
    }

    public void setSentReminder(Boolean sentReminder) {
        this.sentReminder = sentReminder;
    }
}
