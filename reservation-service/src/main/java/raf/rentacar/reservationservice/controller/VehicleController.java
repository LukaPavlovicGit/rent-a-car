package raf.rentacar.reservationservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rentacar.reservationservice.dto.CompanyDto;
import raf.rentacar.reservationservice.dto.VehicleDto;
import raf.rentacar.reservationservice.secutiry.CheckSecurity;
import raf.rentacar.reservationservice.service.VehicleService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_CLIENT"})
    public ResponseEntity<Page<VehicleDto>> getVehicles(@RequestHeader("Authorization") String authorization,
                                                        @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(vehicleService.getVehicles(pageable), HttpStatus.OK);
    }
    @GetMapping("/by-company")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<Page<VehicleDto>> getVehiclesByCompany(@RequestHeader("Authorization") String authorization,
                                                                   @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(vehicleService.getVehiclesByCompany(authorization, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<VehicleDto> getVehicle(@RequestHeader("Authorization") String authorization,
                                                 @PathVariable("id") Long id){
        return new ResponseEntity<>(vehicleService.getVehicle(authorization, id), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<VehicleDto> createVehicle(@RequestHeader("Authorization") String authorization,
                                                    @RequestBody VehicleDto vehicleDto){
        return new ResponseEntity<>(vehicleService.createVehicle(authorization, vehicleDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<VehicleDto> updateVehicle(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") Long id,
                                                    @RequestBody VehicleDto vehicleDto){
        return new ResponseEntity<>(vehicleService.updateVehicle(authorization, id, vehicleDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<VehicleDto> deleteVehicle(@RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") Long id){
        return new ResponseEntity<>(vehicleService.deleteVehicle(authorization, id), HttpStatus.OK);
    }
}
