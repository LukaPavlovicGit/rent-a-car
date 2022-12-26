package raf.rentacar.reservationservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rentacar.reservationservice.dto.CompanyAverageRate;
import raf.rentacar.reservationservice.dto.GetReviewDto;
import raf.rentacar.reservationservice.dto.PostReviewDto;
import raf.rentacar.reservationservice.dto.ReviewsFilterDto;
import raf.rentacar.reservationservice.secutiry.CheckSecurity;
import raf.rentacar.reservationservice.service.ReviewService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT"})
    public ResponseEntity<Page<GetReviewDto>> getReviews(@RequestHeader("Authorization") String authorization,
                                                                @ApiIgnore Pageable pageable){
        return new ResponseEntity<>(reviewService.getReviews(pageable), HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<Page<GetReviewDto>> getCompanyReviews(@RequestHeader("Authorization") String authorization,
                                                                @PathVariable Long companyId){
        return new ResponseEntity<>(reviewService.getCompanyReviews(companyId), HttpStatus.OK);
    }

    @GetMapping("/reviews-by")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<Page<GetReviewDto>> getReviewsBy(@RequestHeader("Authorization") String authorization,
                                                           @RequestBody ReviewsFilterDto reviewsFilterDto){
        return new ResponseEntity<>(reviewService.getReviewsBy(reviewsFilterDto), HttpStatus.OK);
    }

    @GetMapping("/top-rated-companies")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<Page<CompanyAverageRate>> getTopRatedCompanies(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(reviewService.getTopRatedCompanies(), HttpStatus.OK);
    }

    @PostMapping("/{companyId}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<GetReviewDto> createCompanyReview(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable Long companyId,
                                                            @RequestBody PostReviewDto postReviewDto){
        return new ResponseEntity<>(reviewService.createCompanyReview(authorization, companyId, postReviewDto), HttpStatus.OK);
    }
    @PutMapping("/{reviewId}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<GetReviewDto> updateCompanyReview(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable Long reviewId,
                                                            @RequestBody PostReviewDto postReviewDto){
        return new ResponseEntity<>(reviewService.updateCompanyReview(authorization, reviewId, postReviewDto), HttpStatus.OK);
    }
    @DeleteMapping("/{reviewId}")
    @CheckSecurity(roles = {"ROLE_MANAGER", "ROLE_CLIENT"})
    public ResponseEntity<GetReviewDto> deleteCompanyReview(@RequestHeader("Authorization") String authorization,
                                                            @PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.deleteCompanyReview(authorization, reviewId), HttpStatus.OK);
    }
}
