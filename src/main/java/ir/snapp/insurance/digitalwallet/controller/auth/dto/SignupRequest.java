package ir.snapp.insurance.digitalwallet.controller.auth.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
}
