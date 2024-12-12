package com.alice.examensarbete_backend.config.security.JwtConfig;

import com.alice.examensarbete_backend.config.customUser.CustomUserDetailsService;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService customUserDetailsService;

  @Autowired
  public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
    this.jwtUtil = jwtUtil;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  )
          throws ServletException, IOException {
    System.out.println("---JwtAuthenticationFilter---");
    System.out.println("--------------------START--------------------");
    System.out.println("EXTRACTING FROM REQUEST");
    String token = extractJwtFromRequest(request);
    System.out.println("TOKEN: " + token);
    System.out.println("--------------------END--------------------");

    if (token != null && jwtUtil.validateJwtToken(token)) {
      String username = jwtUtil.getUsernameFromJwtToken(token);
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities()
      );

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    filterChain.doFilter(request, response);
  }


  private String extractJwtFromRequest(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header != null && header.startsWith("Bearer ")) {
      return header.substring(7);
    }
    return null;
  }
}
