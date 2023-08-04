package lt.monikos.meepley.controller;

import lt.monikos.meepley.entity.Token;
import lt.monikos.meepley.requestModels.ReviewRequest;
import lt.monikos.meepley.service.ReviewService;
import lt.monikos.meepley.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController (ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/secure/user/game")
    public Boolean reviewGameByUser(@RequestHeader(value="Authorization") String token,
                                    @RequestParam Long gameId) throws Exception {
        Token extracted = ExtractJWT.payloadJWTExtraction(token);

        if (extracted.getSub() == null) {
            throw new Exception("User email is missing");
        }
        return reviewService.userReviewListed(extracted.getSub(), gameId);
    }


    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token,
                           @RequestBody ReviewRequest reviewRequest) throws Exception  {
        Token extracted = ExtractJWT.payloadJWTExtraction(token);

        if(extracted.getSub() == null) {
            throw new Exception("User email is missing");
        }

        System.out.println(extracted.getSub());
        reviewService.postReview(extracted.getSub() , reviewRequest);
    }
}
