package raf.rentacar.reservationservice.dto;

import org.springframework.http.ResponseEntity;

public class ResponseDto {

    private ResponseEntity responseEntity;

    public ResponseEntity getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntity(ResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }
}
