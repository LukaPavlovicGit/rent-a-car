package raf.rentacar.reservationservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.rentacar.reservationservice.domain.Company;
import raf.rentacar.reservationservice.domain.Review;
import raf.rentacar.reservationservice.dto.CompanyAverageRate;
import raf.rentacar.reservationservice.dto.GetReviewDto;
import raf.rentacar.reservationservice.dto.PostReviewDto;
import raf.rentacar.reservationservice.dto.ReviewsFilterDto;
import raf.rentacar.reservationservice.exception.NotFoundException;
import raf.rentacar.reservationservice.exception.UnauthorizedOperation;
import raf.rentacar.reservationservice.mapper.Mapper;
import raf.rentacar.reservationservice.repository.CompanyRepository;
import raf.rentacar.reservationservice.repository.ReservationRepository;
import raf.rentacar.reservationservice.repository.ReviewRepository;
import raf.rentacar.reservationservice.repository.VehicleRepository;
import raf.rentacar.reservationservice.secutiry.tokenService.TokenService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;
    private CompanyRepository companyRepository;
    private TokenService tokenService;
    private Mapper mapper;
    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;

    public ReviewService(ReviewRepository reviewRepository, CompanyRepository companyRepository, TokenService tokenService, Mapper mapper,
                         ReservationRepository reservationRepository,
                         VehicleRepository vehicleRepository) {
        this.reviewRepository = reviewRepository;
        this.companyRepository = companyRepository;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Page<GetReviewDto> getCompanyReviews(Long companyId){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id: %d not found!", companyId)));
        List<GetReviewDto> reviewList = reviewRepository.findAllByCompany(company).stream().map(mapper::reviewToReviewDto).collect(Collectors.toList());
        return new PageImpl<>(reviewList);
    }

    public Page<GetReviewDto> getReviewsBy(ReviewsFilterDto filter){ // criteria: ByCompany || ByCity
        List<GetReviewDto> reviewList = reviewRepository.findAll().stream().map(mapper::reviewToReviewDto).collect(Collectors.toList());
        reviewList = reviewList.stream().filter( review -> {
            String companyName = filter.getCompanyName();
            String city = filter.getCity();
            if(companyName != null && city != null && companyName.equals(review.getCompanyName()) && city.equals(review.getCity()))
                return true;
            else if(companyName != null && companyName.equals(review.getCompanyName()))
               return true;
            else if(city != null && city.equals(review.getCompanyName()))
                return true;
            return false;

        }).collect(Collectors.toList());

        return new PageImpl<>(reviewList);
    }

    public Page<CompanyAverageRate> getTopRatedCompanies(){
        List<Review> reviewList = reviewRepository.findAll();
        Map<Company, Integer> sumMap = new HashMap<>();
        Map<Company, Integer> cntMap = new HashMap<>();

        for(Review review : reviewList){

            Integer sum = review.getRate();
            Integer cnt = 1;
            if(sumMap.get(review.getCompany()) != null){
                sum += sumMap.get(review.getCompany());
                cnt += cntMap.get(review.getCompany());
            }
            sumMap.put(review.getCompany(), sum);
            cntMap.put(review.getCompany(), cnt);
        }

        List<CompanyAverageRate> companyAverageRates = new ArrayList<>();
        for(Company company : sumMap.keySet()){
            BigDecimal totalSum = new BigDecimal(sumMap.get(company)).setScale(2);
            BigDecimal totalCnt = new BigDecimal(cntMap.get(company)).setScale(2);
            BigDecimal companyAverageRate = totalSum.divide(totalCnt);

            companyAverageRates.add(new CompanyAverageRate(
                    company.getName(),
                    companyAverageRate.doubleValue(),
                    totalCnt.intValue())
            );
        }

        Collections.sort(companyAverageRates, (c1, c2) -> {
            if(c2.getAverageRate() - c1.getAverageRate() > 0)
                return 1;
            return -1;
        });

        return new PageImpl<>(companyAverageRates);
    }

    public GetReviewDto createCompanyReview(String authorization, Long companyId, PostReviewDto reviewDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long userId = claims.get("id", Integer.class).longValue();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id: %d not found!", companyId)));
        Review review = mapper.reviewDtoToReview(reviewDto);
        review.setUserId(userId);
        review.setCompany(company);
        reviewRepository.save(review);
        return mapper.reviewToReviewDto(review);
    }

    public GetReviewDto updateCompanyReview(String authorization, Long reviewId, PostReviewDto reviewDto){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long userId = claims.get("id", Integer.class).longValue();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id: %d not found!", reviewId)));
        if(review.getUserId() != userId)
            throw new UnauthorizedOperation(String.format("The user with id: %d doesn't own review with id: %d!", userId, review.getId()));

        if(reviewDto.getRate() != null)
            review.setRate(reviewDto.getRate());
        if(reviewDto.getComment() != null && !reviewDto.getComment().trim().isEmpty())
            review.setComment(reviewDto.getComment());
        reviewRepository.save(review);
        return mapper.reviewToReviewDto(review);
    }

    public GetReviewDto deleteCompanyReview(String authorization, Long reviewId){
        Claims claims = tokenService.parseToken(authorization.split(" ")[1]);
        Long userId = claims.get("id", Integer.class).longValue();
        String role = claims.get("role", String.class);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id: %d not found!", reviewId)));

        if(role.equals("ROLE_CLIENT") && userId != review.getUserId())
            throw new UnauthorizedOperation(String.format("The user with id: %d doesn't own review with id: %d!", userId, review.getId()));

        reviewRepository.delete(review);
        return mapper.reviewToReviewDto(review);
    }
}
