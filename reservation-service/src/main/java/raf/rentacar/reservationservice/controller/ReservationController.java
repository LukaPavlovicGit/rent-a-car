package raf.rentacar.reservationservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rentacar.reservationservice.dto.*;
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
    public ResponseEntity<Page<GetReservationDto>> getReservations(@RequestHeader("Authorization") String authorization,
                                                                   @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(reservationService.getReservations(pageable), HttpStatus.OK);
    }

    @GetMapping("/by-company")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<Page<GetReservationDto>> getReservationsByCompany(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(reservationService.getReservationsByCompany(authorization), HttpStatus.OK);
    }

    @GetMapping("/by-client")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<Page<GetReservationDto>> getReservationsByClient(@RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(reservationService.getReservationsByCompany(authorization), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<GetReservationDto> getReservation(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable Long id) {
        return new ResponseEntity<>(reservationService.getReservation(authorization, id), HttpStatus.OK);
    }
    @PostMapping
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<GetReservationDto> createReservation(@RequestHeader("Authorization") String authorization,
                                                               @RequestBody PostReservationDto reservationDto) {
        return new ResponseEntity<>(reservationService.createReservation(authorization, reservationDto), HttpStatus.OK);
    }

    @PutMapping("/cancel-reservation/{id}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<GetReservationDto> cancelReservation(@RequestHeader("Authorization") String authorization,
                                                               @PathVariable Long id) {
        return new ResponseEntity<>(reservationService.cancelReservation(authorization, id), HttpStatus.OK);
    }

    @PutMapping("/update-reservation/{id}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<GetReservationDto> updateReservation(@RequestHeader("Authorization") String authorization,
                                                               @PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<GetReservationDto> deleteReservation(@RequestHeader("Authorization") String authorization,
                                                               @PathVariable Long id) {
        return new ResponseEntity<>(reservationService.deleteReservation(authorization, id), HttpStatus.OK);
    }
}
