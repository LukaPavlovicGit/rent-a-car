package raf.rentacar.reservationservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rentacar.reservationservice.dto.CompanyDto;
import raf.rentacar.reservationservice.dto.ReservationDto;
import raf.rentacar.reservationservice.dto.VehicleDto;
import raf.rentacar.reservationservice.secutiry.CheckSecurity;
import raf.rentacar.reservationservice.service.ReservationService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<ReservationDto>> getReservations(@RequestHeader("Authorization") String authorization,
                                                             @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(reservationService.getReservations(pageable), HttpStatus.OK);
    }

    @GetMapping()
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<Page<ReservationDto>> getReservationsByCompany(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(reservationService.getReservationsByCompany(authorization), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<ReservationDto> getReservation(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable Long id) {
        return new ResponseEntity<>(reservationService.getReservation(authorization, id), HttpStatus.OK);
    }
    @PostMapping
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<ReservationDto> createReservation(@RequestHeader("Authorization") String authorization,
                                                            @RequestBody ReservationDto reservationDto) {
        return new ResponseEntity<>(reservationService.createReservation(authorization, reservationDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<ReservationDto> cancelReservation(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable Long id) {
        return new ResponseEntity<>(reservationService.cancelReservation(authorization, id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<ReservationDto> updateReservation(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<ReservationDto> deleteReservation(@RequestHeader("Authorization") String authorization,
                                                        @PathVariable Long id) {
        return new ResponseEntity<>(reservationService.deleteReservation(authorization, id), HttpStatus.OK);
    }
}
