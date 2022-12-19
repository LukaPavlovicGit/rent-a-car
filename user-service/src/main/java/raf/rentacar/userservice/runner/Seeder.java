package raf.rentacar.userservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.rentacar.userservice.domain.Rank;
import raf.rentacar.userservice.domain.Role;
import raf.rentacar.userservice.domain.User;
import raf.rentacar.userservice.repository.RankRepository;
import raf.rentacar.userservice.repository.RoleRepository;
import raf.rentacar.userservice.repository.UserRepository;

import java.sql.Date;

@Profile({"default"})
@Component
public class Seeder implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private RankRepository rankRepository;

    public Seeder(RoleRepository roleRepository, UserRepository userRepository, RankRepository rankRepository){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.rankRepository = rankRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //Insert roles
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleManager = new Role("ROLE_MANAGER");
        Role roleClient = new Role("ROLE_CLIENT");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleManager);
        roleRepository.save(roleClient);

        //Insert ranks
        Rank silver = new Rank("SILVER", 0, 2, 5);
        Rank gold = new Rank("GOLD", 2, 5, 10);
        Rank platinum = new Rank("PLATINUM",5, Integer.MAX_VALUE, 20);
        rankRepository.save(silver);
        rankRepository.save(gold);
        rankRepository.save(platinum);

        //Insert admin
        User admin = new User("admin", "admin", "Luka", "Pavlovic", "admin@gmail.com","0641234567", Date.valueOf("1990-10-10"));
        admin.setRole(roleAdmin);
        admin.setActivated(true);

        User manager = new User("m1", "m1", "m1", "m1", "m1@gmail.com", "064111", Date.valueOf("1990-10-10"), "111111");
        manager.setRole(roleManager);
        manager.setRank(silver);
        manager.setActivated(true);

        User client = new User("c1", "c1", "c1", "c1", "c1@gmail.com", "064111", Date.valueOf("1990-10-10"), "111111");
        client.setRole(roleClient);
        client.setRank(silver);
        client.setActivated(true);

        userRepository.save(admin);
        userRepository.save(manager);
        userRepository.save(client);
    }
}
