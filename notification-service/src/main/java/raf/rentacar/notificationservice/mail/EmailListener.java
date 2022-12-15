package raf.rentacar.notificationservice.mail;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import raf.rentacar.notificationservice.domain.Email;
import raf.rentacar.notificationservice.dto.MessageTransferDto;
import raf.rentacar.notificationservice.excpetion.NotFoundException;
import raf.rentacar.notificationservice.repository.EmailRepository;
import raf.rentacar.notificationservice.repository.SentEmailRepository;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Date;
import java.util.Map;

@Component
@Transactional
public class EmailListener {

    private static final String notFoundNotification = "Notification with given type doesn't exist";

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

    @JmsListener(destination = "${destination.sendEmail}", concurrency = "5-10")
    public void sendNotification(Message message) throws JMSException {
        MessageTransferDto messageTransferDto = messageHelper.getMessage(message, MessageTransferDto.class);
        Email email = emailRepository.findNotificationByType(messageTransferDto.getType())
                .orElseThrow(() -> new NotFoundException(String.format("Email with a type: %s doesn't exist!", messageTransferDto.getType())));

//        String messageToSend = notification.getMessage();
//        for (Map.Entry<String,String> entry : messageTransferDto.getParameters().entrySet()) {
//            messageToSend = messageToSend.replace(entry.getKey(), entry.getValue());
//        }
//
//        emailService.sendEmail(messageTransferDto.getEmail(), notification.getSubject(), messageToSend);
//        createArchivedNotification(messageTransferDto.getType(), messageTransferDto.getEmail(), notification.getSubject(), messageToSend);
    }

//    public void createArchivedNotification(String type, String email, String subject, String message) {
//        ArchivedNotification archivedNotification = new ArchivedNotification();
//
//        archivedNotification.setType(type);
//        archivedNotification.setEmail(email);
//        archivedNotification.setSubject(subject);
//        archivedNotification.setMessage(message);
//        archivedNotification.setCreated(new Date());
//
//        archivedNotificationRepository.save(archivedNotification);
//    }
}
