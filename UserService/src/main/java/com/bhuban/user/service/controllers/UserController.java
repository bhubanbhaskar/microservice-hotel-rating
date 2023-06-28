package com.bhuban.user.service.controllers;

import com.bhuban.user.service.entities.User;
import com.bhuban.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
   public ResponseEntity<User> createUser(@RequestBody User user){
       User saveUser = userService.saveUser(user);
       return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
   }

   int retryCount = 1;
   //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @GetMapping("/{userId}")
   // @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> GetSingleUser( @PathVariable String userId){
        logger.info("Get Single User Handler : UserController");
        logger.info("Retry count: {}", retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> GetUsers(){
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception exception){
        exception.printStackTrace();
     logger.info(" Fallback is executed because service is down {} :", exception.getMessage());
    User user = User.builder()
            .email("dummy@gmail.com")
            .name("Dummy")
            .about("This user is created dummy because some service is down")
            .userId("141234")
            .build();
     return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
