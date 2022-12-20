package raf.rentacar.reservationservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidArguments extends CustomException {

    public InvalidArguments(String message) {
        super(message, ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}