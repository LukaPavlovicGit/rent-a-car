package raf.rentacar.reservationservice.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedOperation extends CustomException {

    public UnauthorizedOperation(String message) {
        super(message, ErrorCode.OPERATION_NOT_ALLOWED, HttpStatus.UNAUTHORIZED);
    }
}