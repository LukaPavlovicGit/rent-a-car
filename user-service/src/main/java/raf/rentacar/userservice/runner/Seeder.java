package raf.rentacar.userservice.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import raf.rentacar.userservice.domain.Role;
import raf.rentacar.userservice.repository.RoleRepository;

@Profile({"default"})
@Component
public class Seeder implements CommandLineRunner {

    private RoleRepository roleRepository;

    public Seeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //Insert roles
        Role roleClient = new Role("ROLE_CLIENT");
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleManager = new Role("ROLE_MANAGER");
        roleRepository.save(roleClient);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleManager);

    }
}
