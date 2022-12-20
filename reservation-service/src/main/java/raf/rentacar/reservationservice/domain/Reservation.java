package raf.rentacar.reservationservice.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String userMail;
    private Long companyId;
    private Long vehicleId;
    //yyyy-MM-dd
    private Date start;
    //yyyy-MM-dd
    private Date end;
    private Double totalPrice;
    private boolean reminderSent;
    private boolean canceled;

    public Reservation(){

    }

    public Reservation(Long vehicleId, Date start, Date end) {
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
    }

    public Reservation(Long vehicleId, Date start, Date end, Double totalPrice) {
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
        this.totalPrice = totalPrice;
    }

    public Reservation(Long userId, String userMail, Long companyId, Long vehicleId, Date start, Date end, Double totalPrice) {
        this.userId = userId;
        this.userMail = userMail;
        this.companyId = companyId;
        this.vehicleId = vehicleId;
        this.start = start;
        this.end = end;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
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

    public boolean isReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(boolean reminderSent) {
        this.reminderSent = reminderSent;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
