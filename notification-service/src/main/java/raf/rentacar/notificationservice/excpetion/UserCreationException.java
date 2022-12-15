package raf.rentacar.notificationservice.excpetion;

import org.springframework.http.HttpStatus;

public class UserCreationException extends CustomException {

    public UserCreationException(String message) {
        super(message, ErrorCode.OPERATION_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
