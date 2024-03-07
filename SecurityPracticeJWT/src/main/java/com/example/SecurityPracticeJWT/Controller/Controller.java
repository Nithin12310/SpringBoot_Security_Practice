package com.example.SecurityPracticeJWT.Controller;

import com.example.SecurityPracticeJWT.DTO.LoginRequestDTO;
import com.example.SecurityPracticeJWT.DTO.LoginResponseDTO;
import com.example.SecurityPracticeJWT.Model.Employee;
import com.example.SecurityPracticeJWT.Repository.EmployeeRepository;
import com.example.SecurityPracticeJWT.Service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }


    @GetMapping("/admin")
    public String admin(){
        return "Hello Admin";
    }

    @GetMapping("/user")
    public String user(){
        return "Hello User";
    }

    @PostMapping("/register")
    public String register(@RequestBody Employee employee){
//        System.out.println("Controller");
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
        return "Register Sucessfully";
    }

  @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
//      Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmailid(),loginRequestDTO.getPassword()));
//      if(authentication.isAuthenticated()){
//          return jwtService.generateToken(loginRequestDTO.getEmailid());
//      }
//      else{
//          System.out.println("not valid");
//          throw new UsernameNotFoundException("Invalid user request");
//      }

      try {
          Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmailid(),loginRequestDTO.getPassword()));
          var token=jwtService.generateToken(loginRequestDTO.getEmailid());
          return ResponseEntity.ok().body(new LoginResponseDTO("Token", HttpStatus.OK,token));

      }
      catch (Exception UserNotFoundException){
          System.out.println("error");
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO("Token",HttpStatus.NOT_FOUND,null));
      }

  }


}
