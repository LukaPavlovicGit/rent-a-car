package raf.rentacar.reservationservice.emailScheduler;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import raf.rentacar.reservationservice.domain.Reservation;
import raf.rentacar.reservationservice.dto.DiscountDto;
import raf.rentacar.reservationservice.dto.MessageTransferDto;
import raf.rentacar.reservationservice.dto.UserDto;
import raf.rentacar.reservationservice.messageHelper.MessageHelper;
import raf.rentacar.reservationservice.repository.ReservationRepository;
import raf.rentacar.reservationservice.repository.ReviewRepository;
import raf.rentacar.reservationservice.service.ReservationService;

import java.time.LocalDate;
import java.time.Period;

@Component
@EnableScheduling
public class EmailScheduler {

    private ReservationService reservationService;
    private ReservationRepository reservationRepository;
    private RestTemplate userServiceRestTemplate;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private final ReviewRepository reviewRepository;

    public EmailScheduler(ReservationService reservationService, ReservationRepository reservationRepository,
                          RestTemplate userServiceRestTemplate, JmsTemplate jmsTemplate, MessageHelper messageHelper,
                          ReviewRepository reviewRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.userServiceRestTemplate = userServiceRestTemplate;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.reviewRepository = reviewRepository;
    }

    @Scheduled(cron = "0 */10 * ? * *") // za slanje svakih 10 minuta
    public void sendReminderEmails(){

        for(Reservation reservation : reservationRepository.findAll()){

            if(reservation.isReminderSent())
                continue;

            if (Period.between(LocalDate.now(), reservation.getStart().toLocalDate()).getDays() <= 2) {

                ResponseEntity<UserDto> response =
                        userServiceRestTemplate.exchange(
                                "/" + reservation.getUserId(),
                                HttpMethod.GET,
                                null,
                                UserDto.class
                        );
                UserDto userDto = response.getBody();

                MessageTransferDto messageTransferDto = new MessageTransferDto(
                        userDto.getFirstName(),
                        userDto.getLastName(),
                        String.valueOf(reservation.getVehicleId()),
                        userDto.getEmail(),
                        reservation.getStart().toString()
                );
                jmsTemplate.convertAndSend("reservation_reminder_queue", messageHelper.createTextMessage(messageTransferDto));

                reservation.setReminderSent(true);
                reservationRepository.save(reservation);
            }
        }
    }
}
