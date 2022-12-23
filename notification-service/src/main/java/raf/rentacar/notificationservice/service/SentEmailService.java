package raf.rentacar.notificationservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.rentacar.notificationservice.domain.SentEmail;
import raf.rentacar.notificationservice.dto.SentEmailDto;
import raf.rentacar.notificationservice.dto.SentEmailFilterDto;
import raf.rentacar.notificationservice.mapper.Mapper;
import raf.rentacar.notificationservice.repository.SentEmailRepository;
import raf.rentacar.notificationservice.secutiry.tokenService.TokenService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SentEmailService {

    private SentEmailRepository sentEmailRepository;
    private TokenService tokenService;
    private Mapper mapper;

    public SentEmailService(SentEmailRepository sentEmailRepository, TokenService tokenService, Mapper mapper) {
        this.sentEmailRepository = sentEmailRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public Page<SentEmailDto> getSentEmails(Pageable pageable, SentEmailFilterDto filterDto) {
        List<SentEmailDto> sentEmailDtoList = sentEmailRepository.findAll(pageable)
                .map(mapper::sentEmailToSentEmailDto)
                .stream()
                .filter(sentEmailDto -> {
                   if(filterDto.getType() != null && !filterDto.getType().trim().isEmpty() && !filterDto.getType().equals(sentEmailDto.getType()))
                       return false;
                   if(filterDto.getEmail() != null && !filterDto.getEmail().trim().isEmpty() && !filterDto.getEmail().equals(sentEmailDto.getDestinationEmail()))
                       return false;
                   if(filterDto.getLowerBound() != null && filterDto.getUpperBound() != null
                           && (!sentEmailDto.getDateSent().after(filterDto.getLowerBound()) || !sentEmailDto.getDateSent().before(filterDto.getUpperBound())))
                       return false;
                   if(filterDto.getLowerBound() != null && !sentEmailDto.getDateSent().after(filterDto.getLowerBound()))
                       return false;
                    if(filterDto.getUpperBound() != null && !sentEmailDto.getDateSent().before(filterDto.getUpperBound()))
                       return false;

                   return true;
                }).collect(Collectors.toList());

        return new PageImpl<>(sentEmailDtoList);
    }

    public Page<SentEmailDto> getSentEmailsByEmail(String authorization, SentEmailFilterDto filterDto) {
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        String email = claims.get("email", String.class);
        List<SentEmailDto> sentEmailDtoList = sentEmailRepository.findAllByDestinationEmail(email)
                .stream()
                .map(mapper::sentEmailToSentEmailDto)
                .filter(sentEmailDto -> {
                    if(filterDto.getType() != null && !filterDto.getType().trim().isEmpty() && !filterDto.getType().equals(sentEmailDto.getType()))
                        return false;
                    if(filterDto.getEmail() != null && !filterDto.getEmail().trim().isEmpty() && !filterDto.getEmail().equals(sentEmailDto.getDestinationEmail()))
                        return false;
                    if(filterDto.getLowerBound() != null && filterDto.getUpperBound() != null
                            && (!sentEmailDto.getDateSent().after(filterDto.getLowerBound()) || !sentEmailDto.getDateSent().before(filterDto.getUpperBound())))
                        return false;
                    if(filterDto.getLowerBound() != null && !sentEmailDto.getDateSent().after(filterDto.getLowerBound()))
                        return false;
                    if(filterDto.getUpperBound() != null && !sentEmailDto.getDateSent().before(filterDto.getUpperBound()))
                        return false;

                    return true;
                }).collect(Collectors.toList());

        return new PageImpl<>(sentEmailDtoList);
    }
}
