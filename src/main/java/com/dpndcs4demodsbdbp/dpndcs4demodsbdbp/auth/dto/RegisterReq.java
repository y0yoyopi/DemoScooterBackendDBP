package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp.auth.dto;


import lombok.Data;

@Data
public class RegisterReq {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Boolean isStaff = false;
}
