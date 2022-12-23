package raf.rentacar.reservationservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class DataRunner implements CommandLineRunner {



    @Override
    public void run(String... args) throws Exception {


    }
}
