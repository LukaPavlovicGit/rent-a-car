package raf.rentacar.reservationservice.domain;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rate;
    private String comment;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "veicle_id", nullable = false)
    private Vehicle vehicle;

    public Review() {

    }

    public Review(Integer rate, String comment, Vehicle vehicle) {
        this.rate = rate;
        this.comment = comment;
        this.vehicle = vehicle;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
