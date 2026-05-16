package com.auth.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String fullName;
    private String email;
    private String phone;
    private String profilePhoto;
    private String jobTitle;
    private String location;
}
