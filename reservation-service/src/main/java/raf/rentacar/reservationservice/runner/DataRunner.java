package raf.rentacar.reservationservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Vehicle;
import raf.rentacar.reservationservice.repository.CompanyRepository;
import raf.rentacar.reservationservice.repository.VehicleRepository;

@Profile({"default"})
@Component
public class DataRunner implements CommandLineRunner {

    private CompanyRepository companyRepository;
    private VehicleRepository vehicleRepository;

    public DataRunner(CompanyRepository companyRepository, VehicleRepository vehicleRepository) {
        this.companyRepository = companyRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Company company1 = new Company(Long.valueOf(2), "Rent A Car 1", "Cacak", "Opis1");
        Company company2 = new Company(Long.valueOf(3), "Rent A Car 2", "Beograd", "Opis2");

        companyRepository.save(company1);
        companyRepository.save(company2);

        Vehicle v1 = new Vehicle("KABRIOLET", "MERCEDES", 1000.00, company1);
        Vehicle v2 = new Vehicle("KABRIOLET", "BMW", 800.00, company1);
        Vehicle v3 = new Vehicle("LIMUNZINA", "TOYOTA", 500.00, company1);
        Vehicle v4 = new Vehicle("LIMUNZINA", "AUDI", 600.00, company1);
        Vehicle v5 = new Vehicle("KUPE", "OPEL", 300.00, company1);

        vehicleRepository.save(v1);
        vehicleRepository.save(v2);
        vehicleRepository.save(v3);
        vehicleRepository.save(v4);
        vehicleRepository.save(v5);

        Vehicle v6 = new Vehicle("KABRIOLET", "PEUGEOT", 700.00, company2);
        Vehicle v7 = new Vehicle("KABRIOLET", "RENO", 500.00, company2);
        Vehicle v8 = new Vehicle("LIMUNZINA", "FORD", 500.00, company2);
        Vehicle v9 = new Vehicle("LIMUNZINA", "MAZDA", 600.00, company2);
        Vehicle v10 = new Vehicle("KUPE", "BMW", 300.00, company2);

        vehicleRepository.save(v6);
        vehicleRepository.save(v7);
        vehicleRepository.save(v8);
        vehicleRepository.save(v9);
        vehicleRepository.save(v10);
    }
}
