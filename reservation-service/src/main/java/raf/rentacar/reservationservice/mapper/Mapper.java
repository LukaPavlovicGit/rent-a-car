package raf.rentacar.reservationservice.mapper;

import org.springframework.stereotype.Component;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Reservation;
import raf.rentacar.reservationservice.domain.Review;
import raf.rentacar.reservationservice.domain.Vehicle;
import raf.rentacar.reservationservice.dto.*;

@Component
public class Mapper {

    public CompanyDto companyToCompanyDto(Company company){
        return new CompanyDto(company.getId(),company.getManagerId(),company.getName(),company.getCity(),company.getDescription());
    }
    public Company companyDtoToCompany(CompanyDto companyDto){
        return new Company(companyDto.getManagerId(),companyDto.getName(),companyDto.getCity(),companyDto.getDescription());
    }
    public VehicleDto vehicleToVehicleDto(Vehicle vehicle){
        return new VehicleDto(vehicle.getId(),vehicle.getType(),vehicle.getName(),vehicle.getPrice());
    }
    public Vehicle vehicleDtoToVehicle(VehicleDto vehicleDto){
        return new Vehicle(vehicleDto.getType(),vehicleDto.getName(),vehicleDto.getPrice());
    }
    public GetReservationDto reservationToReservationDto(Reservation reservation){
        return new GetReservationDto(reservation.getId(),reservation.getUserId(),reservation.getUserMail(),reservation.getCompanyId(),reservation.getVehicleId(),
                reservation.getStart(),reservation.getEnd(),reservation.getTotalPrice(),reservation.isCanceled());
    }
    public Reservation reservationDtoToReservation(PostReservationDto reservationDto){
        return new Reservation(reservationDto.getVehicleId(),reservationDto.getStart(),reservationDto.getEnd());
    }
    public GetReviewDto reviewToReviewDto(Review review){
        return new GetReviewDto(review.getRate(),review.getComment(),review.getCompany().getName(),review.getCompany().getCity());
    }
    public Review reviewDtoToReview(PostReviewDto postReviewDto){
        return new Review(postReviewDto.getRate(),postReviewDto.getComment());
    }
}