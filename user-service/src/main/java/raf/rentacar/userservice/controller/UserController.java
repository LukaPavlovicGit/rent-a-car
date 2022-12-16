package raf.rentacar.userservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rentacar.userservice.dto.*;
import raf.rentacar.userservice.secutiry.CheckSecurity;
import raf.rentacar.userservice.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER"})
    public ResponseEntity<Page<UserDto>> getUsers(@RequestHeader("Authorization") String authorization,
                                                  @ApiIgnore Pageable pageable) {
        return new ResponseEntity<>(userService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT"})
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("/create-client")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(userService.createClient(clientDto), HttpStatus.CREATED);
    }

    @PostMapping("/create-manager")
    public ResponseEntity<ManagerDto> createManager(@RequestBody ManagerDto managerDto) {
        return new ResponseEntity<>(userService.createManager(managerDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @PutMapping("/update-admin")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<UserDto> updateAdmin(@RequestHeader("Authorization") String authorization,
                                                      @RequestBody UpdateAdminDto updateAdminDto){
        return new ResponseEntity<>(userService.updateAdmin(authorization, updateAdminDto), HttpStatus.OK);
    }

    @PutMapping("/update-manager")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<UserDto> updateManager(@RequestHeader("Authorization") String authorization,
                                                          @RequestBody UpdateManagerDto updateManagerDto){
        return new ResponseEntity<>(userService.updateManager(authorization, updateManagerDto), HttpStatus.OK);
    }

    @PutMapping("/update-client")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<UserDto> updateClient(@RequestHeader("Authorization") String authorization,
                                                        @RequestBody UpdateClientDto updateClientDto){
        return new ResponseEntity<>(userService.updateClient(authorization, updateClientDto), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT"})
    public ResponseEntity<Void> changePassword(@RequestHeader("Authorization") String authorization,
                                                @RequestBody PasswordDto passwordDto){
        return new ResponseEntity<>(userService.changePassword(authorization, passwordDto), HttpStatus.OK);
    }

    @DeleteMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT"})
    public ResponseEntity<Object> deleteUser(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping("/account-activation/{id}")
    public ResponseEntity<Void> accountActivation(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.accountActivation(id), HttpStatus.OK);
    }

    @PostMapping("/ban-user/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Object> banUser(@RequestHeader("Authorization") String authorization,
                                          @PathVariable("id") Long id){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/remove-ban-user/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Object> removeBanUser(@RequestHeader("Authorization") String authorization,
                                                @PathVariable("id") Long id){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}