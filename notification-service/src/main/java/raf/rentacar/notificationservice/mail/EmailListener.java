package raf.rentacar.notificationservice.mail;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import raf.rentacar.notificationservice.domain.Email;
import raf.rentacar.notificationservice.domain.SentEmail;
import raf.rentacar.notificationservice.dto.MessageTransferDto;
import raf.rentacar.notificationservice.excpetion.NotFoundException;
import raf.rentacar.notificationservice.repository.EmailRepository;
import raf.rentacar.notificationservice.repository.SentEmailRepository;

import javax.jms.JMSException;
import javax.jms.Message;
import java.sql.Date;

@Component
@Transactional
public class EmailListener {

    private MessageHelper messageHelper;
    private EmailService emailService;
    private EmailRepository emailRepository;
    private SentEmailRepository sentEmailRepository;

    public EmailListener(MessageHelper messageHelper, EmailService emailService, EmailRepository emailRepository, SentEmailRepository sentEmailRepository) {
        this.messageHelper = messageHelper;
        this.emailService = emailService;
        this.emailRepository = emailRepository;
        this.sentEmailRepository = sentEmailRepository;
    }

    @JmsListener(destination = "${destination.account.activation}", concurrency = "5-10")
    public void sendActivationEmail(Message message) throws JMSException {

        MessageTransferDto messageTransferDto = messageHelper.getMessage(message, MessageTransferDto.class);

        Email email = emailRepository.findNotificationByType("account_activation")
                .orElseThrow(() -> new NotFoundException("Email with a type: account_activation doesn't exist!"));

        String messageToSend = email.getText();
        messageToSend = messageToSend.replaceAll("%firstname", messageTransferDto.getFirstName());
        messageToSend = messageToSend.replaceAll("%lastname", messageTransferDto.getLastname());
        messageToSend = messageToSend.replaceAll("%link", messageTransferDto.getLink());

        String destination = messageTransferDto.getDestinationEmail();
        String subject = email.getSubject();

        emailService.sendEmail(destination, subject, messageToSend);

        sentEmailRepository.save(new SentEmail("account_activation", destination, subject, messageToSend, new Date(System.currentTimeMillis())));
    }
    @JmsListener(destination = "${destination.password.change}", concurrency = "5-10")
    public void sendPasswordChangeEmail(Message message) throws JMSException {

        MessageTransferDto messageTransferDto = messageHelper.getMessage(message, MessageTransferDto.class);

        Email email = emailRepository.findNotificationByType("change_password")
                .orElseThrow(() -> new NotFoundException("Email with a type: change_password doesn't exist!"));

        String messageToSend = email.getText();
        messageToSend = messageToSend.replaceAll("%firstname", messageTransferDto.getFirstName());
        messageToSend = messageToSend.replaceAll("%lastname", messageTransferDto.getLastname());

        String destination = messageTransferDto.getDestinationEmail();
        String subject = email.getSubject();

        emailService.sendEmail(destination, subject, messageToSend);

        sentEmailRepository.save(new SentEmail("change_password", destination, subject, messageToSend, new Date(System.currentTimeMillis())));
    }
    @JmsListener(destination = "${destination.reservation}", concurrency = "5-10")
    public void sendReservationEmail(Message message) throws JMSException {

        MessageTransferDto messageTransferDto = messageHelper.getMessage(message, MessageTransferDto.class);

        Email email = emailRepository.findNotificationByType("reservation_successful")
                .orElseThrow(() -> new NotFoundException("Email with a type: reservation_successful doesn't exist!"));

        String messageToSend = email.getText();
        messageToSend = messageToSend.replaceAll("%firstname", messageTransferDto.getFirstName());
        messageToSend = messageToSend.replaceAll("%lastname", messageTransferDto.getLastname());
        messageToSend = messageToSend.replaceAll("%car", messageTransferDto.getCar());

        String destination = messageTransferDto.getDestinationEmail();
        String subject = email.getSubject();

        emailService.sendEmail(destination, subject, messageToSend);

        sentEmailRepository.save(new SentEmail("reservation_successful", destination, subject, messageToSend, new Date(System.currentTimeMillis())));
    }
    @JmsListener(destination = "${destination.reservation.cancellation}", concurrency = "5-10")
    public void sendReservationCancellation(Message message) throws JMSException {

        MessageTransferDto messageTransferDto = messageHelper.getMessage(message, MessageTransferDto.class);

        Email email = emailRepository.findNotificationByType("reservation_cancellation")
                .orElseThrow(() -> new NotFoundException("Email with a type: reservation_cancellation doesn't exist!"));

        String messageToSend = email.getText();
        messageToSend = messageToSend.replaceAll("%firstname", messageTransferDto.getFirstName());
        messageToSend = messageToSend.replaceAll("%lastname", messageTransferDto.getLastname());
        messageToSend = messageToSend.replaceAll("%link", messageTransferDto.getCar());

        String destination = messageTransferDto.getDestinationEmail();
        String subject = email.getSubject();

        emailService.sendEmail(destination, subject, messageToSend);

        sentEmailRepository.save(new SentEmail("reservation_cancellation", destination, subject, messageToSend, new Date(System.currentTimeMillis())));
    }
    @JmsListener(destination = "${destination.reservation.reminder}", concurrency = "5-10")
    public void sendReservationReminder(Message message) throws JMSException {

        MessageTransferDto messageTransferDto = messageHelper.getMessage(message, MessageTransferDto.class);

        Email email = emailRepository.findNotificationByType("reservation_reminder")
                .orElseThrow(() -> new NotFoundException("Email with a type: reservation_reminder doesn't exist!"));

        String messageToSend = email.getText();
        messageToSend = messageToSend.replaceAll("%firstname", messageTransferDto.getFirstName());
        messageToSend = messageToSend.replaceAll("%lastname", messageTransferDto.getLastname());
        messageToSend = messageToSend.replaceAll("%link", messageTransferDto.getCar());
        messageToSend = messageToSend.replaceAll("%rezStart", messageTransferDto.getRezStart());

        String destination = messageTransferDto.getDestinationEmail();
        String subject = email.getSubject();

        emailService.sendEmail(destination, subject, messageToSend);

        sentEmailRepository.save(new SentEmail("reservation_reminder", destination, subject, messageToSend, new Date(System.currentTimeMillis())));
    }

}
