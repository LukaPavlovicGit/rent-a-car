package raf.rentacar.notificationservice.mapper;

import org.springframework.stereotype.Component;
import raf.rentacar.notificationservice.domain.SentEmail;
import raf.rentacar.notificationservice.dto.SentEmailDto;

@Component
public class Mapper {

    public SentEmailDto sentEmailToSentEmailDto(SentEmail sentEmail){
        return new SentEmailDto(sentEmail.getType(),sentEmail.getDestinationEmail(),
                sentEmail.getSubject(),sentEmail.getMessage(),sentEmail.getDateSent());
    }
}
/*

    private String type;
    private String destinationEmail;
    private String subject;
    private String message;
    private Date dateSent;
 */