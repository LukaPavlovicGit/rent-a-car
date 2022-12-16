package raf.rentacar.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.rentacar.userservice.domain.Rank;
import raf.rentacar.userservice.domain.Role;
import raf.rentacar.userservice.domain.User;
import raf.rentacar.userservice.dto.*;
import raf.rentacar.userservice.exception.AccessForbidden;
import raf.rentacar.userservice.exception.NotFoundException;
import raf.rentacar.userservice.exception.UserCreationException;
import raf.rentacar.userservice.mapper.Mapper;
import raf.rentacar.userservice.messageHelper.MessageHelper;
import raf.rentacar.userservice.repository.RankRepository;
import raf.rentacar.userservice.repository.RoleRepository;
import raf.rentacar.userservice.repository.UserRepository;
import raf.rentacar.userservice.secutiry.tokenService.TokenService;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RankRepository rankRepository;
    private TokenService tokenService;
    private Mapper mapper;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, RankRepository rankRepository,
                       TokenService tokenService, Mapper mapper, JmsTemplate jmsTemplate, MessageHelper messageHelper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.rankRepository = rankRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
    }

    public Page<UserDto> getAll(Pageable pageable){
        return userRepository.findAll(pageable).map(mapper::userToUserDto);
    }

    public UserDto getUser(Long id){
        return userRepository.findById(id).map(mapper::userToUserDto).orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found!", id)));
    }

    public ClientDto createClient(ClientDto clientDto){

        Optional<User> user = userRepository.findUserByUsername(clientDto.getUsername());
        if(user.isPresent())
            throw new UserCreationException(String.format("User with username: %s already exist!", clientDto.getUsername()));

        user = userRepository.findUserByEmail(clientDto.getEmail());
        if(user.isPresent())
            throw new UserCreationException(String.format("User with email: %s already exist!", clientDto.getEmail()));

        User client = mapper.clientDtoToUser(clientDto);
        Role role = roleRepository.findRoleByName("ROLE_CLIENT").orElseThrow(() -> new NotFoundException("Role with role_name: ROLE_CLIENT not found!"));
        Rank rank = rankRepository.findRankByName("SILVER").orElseThrow(() -> new NotFoundException("Rank with rank_name: SILVER not found!"));

        client.setRole(role);
        client.setRank(rank);
        userRepository.save(client);

        MessageTransferDto messageTransferDto = new MessageTransferDto(
                "account_activation",
                client.getFirstName(),
                client.getLastName(),
                "http://localhost:8000/api/users/account-activation/"+client.getId(),
                client.getEmail()
        );

        jmsTemplate.convertAndSend("send_email_queue", messageHelper.createTextMessage(messageTransferDto));

        return clientDto;
    }

    public ManagerDto createManager(ManagerDto managerDto){

        Optional<User> user = userRepository.findUserByUsername(managerDto.getUsername());
        if(user.isPresent())
            throw new UserCreationException(String.format("User with username: %s already exist!", managerDto.getUsername()));

        user = userRepository.findUserByEmail(managerDto.getEmail());
        if(user.isPresent())
            throw new UserCreationException(String.format("User with email: %s already exist!", managerDto.getEmail()));

        User manager = mapper.managerDtoToUser(managerDto);
        Role role = roleRepository.findRoleByName("ROLE_MANAGER").orElseThrow(() -> new NotFoundException("Role with role_name: ROLE_CLIENT not found!"));

        manager.setRole(role);
        userRepository.save(manager);

        MessageTransferDto messageTransferDto = new MessageTransferDto(
                "account_activation",
                manager.getFirstName(),
                manager.getLastName(),
                "http://localhost:8000/api/users/account-activation/"+manager.getId(),
                manager.getEmail()
        );

        jmsTemplate.convertAndSend("send_email_queue", messageHelper.createTextMessage(messageTransferDto));

        return managerDto;
    }

    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException("Incorrect credentials!"));

        if(user.isForbidden())
            throw new AccessForbidden("Access forbidden!");

        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        claims.put("email", user.getEmail());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }


}
