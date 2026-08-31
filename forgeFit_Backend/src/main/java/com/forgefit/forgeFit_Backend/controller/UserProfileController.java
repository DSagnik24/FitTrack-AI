package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.UserProfileRequest;
import com.forgefit.forgeFit_Backend.dto.UserProfileResponse;
import com.forgefit.forgeFit_Backend.service.UserProfileService;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileResponse> createProfile(
            Authentication authentication,
            @Valid @RequestBody UserProfileRequest request
    ) {

        UserProfileResponse profile =
                userProfileService.createProfile(authentication.getName(),
                        request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profile);
    }

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication){
        return ResponseEntity.ok(
                userProfileService.getProfile(authentication.getName())
        );
    }

    @PutMapping
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UserProfileRequest request
    ){
        UserProfileResponse profile =
                userProfileService.updateProfile(authentication.getName(), request);
        return ResponseEntity.ok(profile);
    }
}