package com.example.SecurityPracticeJWT.Filter;

import com.example.SecurityPracticeJWT.Configuration.UserInfoDetailService;
import com.example.SecurityPracticeJWT.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserInfoDetailService userInfoDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader= request.getHeader("Authorization");
        String token= null;
        String username=null;
        if(authHeader!=null && authHeader.startsWith("Bearer")){
            token=authHeader.substring(7);
            username= jwtService.extractUsername(token);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                  UserDetails userDetails=userInfoDetailService.loadUserByUsername(username);
                  if(jwtService.validateToken(token,userDetails)){
                      UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                  }
        }
        filterChain.doFilter(request,response);
    }
}
