package raf.rentacar.userservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import raf.rentacar.userservice.dto.*;
import raf.rentacar.userservice.secutiry.CheckSecurity;
import raf.rentacar.userservice.secutiry.SecurityAspect;
import raf.rentacar.userservice.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<Page<UserDto>> getUsers(@RequestHeader("authorization") String authorization,
                                                  @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(userService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER, ROLE_USER"})
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("/create-client")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(userService.createClient(clientDto), HttpStatus.CREATED);
    }

    @PostMapping("/create-manager")
    public ResponseEntity<ManagerDto> createManager(@RequestBody ManagerDto managerDto) {
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @PutMapping("/update-admin")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<UserDto> updateAdmin(@RequestHeader("authorization") String authorization,
                                               @RequestBody UserDto userDto){
        //Long clientId = securityAspect.getUserId(authorization);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/update-manager")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<ManagerDto> updateManager(@RequestHeader("authorization") String authorization,
                                                   @RequestBody ManagerDto managerDto){
        //Long clientId = securityAspect.getUserId(authorization);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/update-client")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<ClientDto> updateClient(@RequestHeader("authorization") String authorization,
                                                  @RequestBody ClientDto clientDto){
        //Long clientId = securityAspect.getUserId(authorization);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER, ROLE_USER"})
    public ResponseEntity<ClientDto> changePassword(@RequestHeader("authorization") String authorization){
        //Long clientId = securityAspect.getUserId(authorization);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER, ROLE_USER"})
    public ResponseEntity<Object> deleteUser(@RequestHeader("authorization") String authorization){
        //Long clientId = securityAspect.getUserId(authorization);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/account-activation")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER, ROLE_USER"})
    public ResponseEntity<Object> accountActivation(@RequestHeader("authorization") String authorization){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/ban-user/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Object> banUser(@RequestHeader("authorization") String authorization,
                                          @PathVariable("id") Long id){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/remove-ban-user/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Object> removeBanUser(@RequestHeader("authorization") String authorization,
                                                @PathVariable("id") Long id){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}