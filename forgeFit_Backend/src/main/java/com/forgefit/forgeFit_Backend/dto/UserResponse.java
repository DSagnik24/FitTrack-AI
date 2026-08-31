package com.forgefit.forgeFit_Backend.dto;

import com.forgefit.forgeFit_Backend.entity.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
