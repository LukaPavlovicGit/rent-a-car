package raf.rentacar.notificationservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.rentacar.notificationservice.domain.Email;
import raf.rentacar.notificationservice.repository.EmailRepository;

@Profile({"default"})
@Component
public class DataRunner implements CommandLineRunner {

    private EmailRepository emailRepository;

    public DataRunner(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Email e1 = new Email("account_activation",
                "account_activation",
                "Hello %name %lastname, click the link to activate your account: %link!");
        Email e2 = new Email("change_password",
                "change_password",
                "Hello %name %lastname, your token is: %link!");
        Email e3 = new Email("reservation_successful",
                "reservation_successful",
                "Hello %name %lastname, a reservation for %car was a success!");
        Email e4 = new Email("reservation_cancellation",
                "reservation_cancellation",
                "Hello %name %lastname, a reservation for %car was canceled successfully!");
        Email e5 = new Email("reservation_reminder",
                "reservation_reminder",
                "Hello %name %lastname, your reservation for %car is on %rezStart!");

        emailRepository.save(e1);
        emailRepository.save(e2);
        emailRepository.save(e3);
        emailRepository.save(e4);
        emailRepository.save(e5);
    }
}
