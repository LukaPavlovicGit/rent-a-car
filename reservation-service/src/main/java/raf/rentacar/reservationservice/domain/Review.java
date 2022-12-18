package raf.rentacar.reservationservice.domain;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rate;
    private String comment;
    private Integer vehicleId;

    public Review() {

    }

    public Review(Integer rate, String comment, Integer vehicleId) {
        this.rate = rate;
        this.comment = comment;
        this.vehicleId = vehicleId;
    }

    public Long getId() {
        return id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getVehicle() {
        return vehicleId;
    }

    public void setVehicle(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }
}
