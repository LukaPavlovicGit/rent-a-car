package raf.rentacar.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jdk.dynalink.Operation;
import org.bouncycastle.operator.OperatorException;
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
import raf.rentacar.userservice.exception.OperationNotAllowed;
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

    public Page<UserDto> getUsers(Pageable pageable){
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
                client.getFirstName(),
                client.getLastName(),
                "http://localhost:8000/api/users/account-activation/"+client.getId(),
                client.getEmail()
        );

        jmsTemplate.convertAndSend("account_activation_queue", messageHelper.createTextMessage(messageTransferDto));

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
                manager.getFirstName(),
                manager.getLastName(),
                "http://localhost:8000/api/users/account-activation/"+manager.getId(),
                manager.getEmail()
        );

        jmsTemplate.convertAndSend("account_activation_queue", messageHelper.createTextMessage(messageTransferDto));

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
        claims.put("firstname", user.getFirstName());
        claims.put("lastname", user.getLastName());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    public UserDto updateAdmin(String authorization, UpdateAdminDto updateAdminDto){

        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = claims.get("id", Integer.class).longValue();

        User admin = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));

        if(updateAdminDto.getUsername() != null)
            admin.setUsername(updateAdminDto.getUsername());
        if(updateAdminDto.getFirstName() != null)
            admin.setFirstName(updateAdminDto.getFirstName());
        if(updateAdminDto.getLastName() != null)
            admin.setLastName(updateAdminDto.getLastName());
        if(updateAdminDto.getPhoneNumber() != null)
            admin.setPhoneNumber(updateAdminDto.getPhoneNumber());
        if(updateAdminDto.getBirthdate() != null)
            admin.setBirthdate(updateAdminDto.getBirthdate());
        userRepository.save(admin);

        return mapper.userToUserDto(admin);
    }

    public UserDto updateManager(String authorization, UpdateManagerDto updateManagerDto){

        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = claims.get("id", Integer.class).longValue();

        User manager = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));

        if(updateManagerDto.getUsername() != null)
            manager.setUsername(updateManagerDto.getUsername());
        if(updateManagerDto.getFirstName() != null)
            manager.setFirstName(updateManagerDto.getFirstName());
        if(updateManagerDto.getLastName() != null)
            manager.setLastName(updateManagerDto.getLastName());
        if(updateManagerDto.getPhoneNumber() != null)
            manager.setPhoneNumber(updateManagerDto.getPhoneNumber());
        if(updateManagerDto.getBirthdate() != null)
            manager.setBirthdate(updateManagerDto.getBirthdate());
        userRepository.save(manager);

        return mapper.userToUserDto(manager);
    }

    public UserDto updateClient(String authorization, UpdateClientDto updateClientDto){

        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = claims.get("id", Integer.class).longValue();

        User client = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));

        if(updateClientDto.getUsername() != null)
            client.setUsername(updateClientDto.getUsername());
        if(updateClientDto.getFirstName() != null)
            client.setFirstName(updateClientDto.getFirstName());
        if(updateClientDto.getLastName() != null)
            client.setLastName(updateClientDto.getLastName());
        if(updateClientDto.getPhoneNumber() != null)
            client.setPhoneNumber(updateClientDto.getPhoneNumber());
        if(updateClientDto.getBirthdate() != null)
            client.setBirthdate(updateClientDto.getBirthdate());
        if(updateClientDto.getPassport() != null)
            client.setPassport(updateClientDto.getPassport());
        userRepository.save(client);

        return mapper.userToUserDto(client);
    }
    public Void changePassword(String authorization, PasswordDto passwordDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long id = claims.get("id", Integer.class).longValue();
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));

        if(!passwordDto.getOldPassword().equals(user.getPassword()))
            throw new OperationNotAllowed("Incorrect password");

        user.setPassword(passwordDto.getNewPassword());
        userRepository.save(user);

        MessageTransferDto messageTransferDto = new MessageTransferDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );

        jmsTemplate.convertAndSend("password_change_queue", messageHelper.createTextMessage(messageTransferDto));
        return null;
    }
    public UserDto deleteUser(CredentialsDto credentialsDto){
        Optional<User> optionalUser = userRepository.findUserByUsernameAndPassword(credentialsDto.getUsername(), credentialsDto.getPassword());
        if(!optionalUser.isPresent())
            throw new NotFoundException("Incorrect credentials!");
        User user = optionalUser.get();
        userRepository.delete(user);
        return mapper.userToUserDto(user);
    }

    public Void accountActivation(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
        user.setActivated(true);
        userRepository.save(user);
        return null;
    }

    public UserDto banUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
        user.setForbidden(true);
        userRepository.save(user);
        return mapper.userToUserDto(user);
    }

    public UserDto removeBanOnUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
        user.setForbidden(false);
        userRepository.save(user);
        return mapper.userToUserDto(user);
    }

    public UserDto incrementTotalDays(Long id, Integer numberOfDays) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
        user.setTotalDays(user.getTotalDays() + numberOfDays);
        if(user.getTotalDays() >= user.getRank().getUpperBound())
            assignRankToUser(user);
        userRepository.save(user);
        return mapper.userToUserDto(user);
    }

    public UserDto decrementTotalDays(Long id, Integer numberOfDays) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
        if(user.getTotalDays() == 0)
            return mapper.userToUserDto(user);
        user.setTotalDays(user.getTotalDays() - numberOfDays);
        if(user.getTotalDays() < 0)
            user.setTotalDays(0);
        if(user.getTotalDays() < user.getRank().getLowerBound())
            assignRankToUser(user);
        userRepository.save(user);
        return mapper.userToUserDto(user);
    }

    private void assignRankToUser(User user){
        for(Rank rank : rankRepository.findAll()){
            if(user.getTotalDays() >= rank.getLowerBound() && user.getTotalDays() < rank.getUpperBound()){
                user.setRank(rank);
                return;
            }
        }
    }

    public DiscountDto getDiscount(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
        return new DiscountDto(user.getRank().getDiscount());
    }
}