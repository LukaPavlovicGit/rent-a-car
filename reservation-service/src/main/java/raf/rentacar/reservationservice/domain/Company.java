package raf.rentacar.reservationservice.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long manager_id;
    private String name;
    private String city;
    private String description;
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Vehicle> vehicles;

    public Company(){

    }

    public Company(Long manager_id, String name, String description, Set<Vehicle> vehicles) {
        this.manager_id = manager_id;
        this.name = name;
        this.description = description;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public Long getManager_id() {
        return manager_id;
    }

    public void setManager_id(Long manager_id) {
        this.manager_id = manager_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
