package raf.rentacar.notificationservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rentacar.notificationservice.dto.SentEmailDto;
import raf.rentacar.notificationservice.dto.SentEmailFilterDto;
import raf.rentacar.notificationservice.secutiry.CheckSecurity;
import raf.rentacar.notificationservice.service.SentEmailService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/sent-email")
public class SentEmailController {

    private SentEmailService sentEmailService;

    public SentEmailController(SentEmailService sentEmailService) {
        this.sentEmailService = sentEmailService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<SentEmailDto>> getSentEmails(@RequestHeader("Authorization") String authorization,
                                                            @ApiIgnore Pageable pageable,
                                                            @RequestBody SentEmailFilterDto filterDto){
        return new ResponseEntity<>(sentEmailService.getSentEmails(pageable, filterDto), HttpStatus.OK);
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_MANAGER", "ROLE_CLIENT"})
    public ResponseEntity<Page<SentEmailDto>> getSentEmailsByEmail(@RequestHeader("Authorization") String authorization,
                                                                   @RequestBody SentEmailFilterDto filterDto){
        return new ResponseEntity<>(sentEmailService.getSentEmailsByEmail(authorization, filterDto), HttpStatus.OK);
    }

}
