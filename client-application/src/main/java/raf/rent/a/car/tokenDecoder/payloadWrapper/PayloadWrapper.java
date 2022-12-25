package raf.rent.a.car.tokenDecoder.payloadWrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayloadWrapper {

    private Long id;
    private String role;
    private String email;
    private String firstname;
    private String lastname;
}
