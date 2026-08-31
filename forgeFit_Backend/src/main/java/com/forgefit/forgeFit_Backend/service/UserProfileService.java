package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.UserProfileRequest;
import com.forgefit.forgeFit_Backend.dto.UserProfileResponse;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.UserProfile;
import com.forgefit.forgeFit_Backend.repository.UserProfileRepository;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileResponse createProfile(String email, @Valid UserProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = UserProfile.builder()
                .user(user)
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .heightCm(request.getHeightCm())
                .currentWeightKg(request.getCurrentWeightKg())
                .activityLevel(request.getActivityLevel())
                .build();

        UserProfile savedProfile = userProfileRepository.save(profile);
        return mapToResponse(savedProfile);
    }

    public UserProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        UserProfile profile = userProfileRepository
                .findByUser_UserId(user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Profile not found")
                );

        return mapToResponse(profile);
    }

    public UserProfileResponse updateProfile(
            String email,
            UserProfileRequest request
    ){
        User user = userRepository.findByEmail(email)
                        .orElseThrow(()-> new RuntimeException("User not found"));

        UserProfile profile = userProfileRepository
                .findByUser_UserId(user.getUserId())
                        .orElseThrow(()->
                                new RuntimeException("Profile not found"));

        profile.setGender(request.getGender());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setHeightCm(request.getHeightCm());
        profile.setCurrentWeightKg(request.getCurrentWeightKg());
        profile.setActivityLevel(request.getActivityLevel());

        UserProfile updatedUserProfile = userProfileRepository.save(profile);

        return mapToResponse(updatedUserProfile);
    }

    private UserProfileResponse mapToResponse(UserProfile profile) {

        User user = profile.getUser();

        return UserProfileResponse.builder()
                .id(profile.getId())
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .gender(profile.getGender())
                .dateOfBirth(profile.getDateOfBirth())
                .heightCm(profile.getHeightCm())
                .currentWeightKg(profile.getCurrentWeightKg())
                .activityLevel(profile.getActivityLevel())
                .build();
    }
}