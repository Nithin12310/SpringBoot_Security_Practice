package com.example.SecurityPracticeJWT.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String message;
    private HttpStatus httpStatus;
    private Object object;
}
