package com.alice.examensarbete_backend.config.security;

import com.alice.examensarbete_backend.config.security.JwtConfig.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  @Autowired
  public SecurityConfig(JwtAuthFilter jwtAuthFilter) {

    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/api/**", "/user/createDefaultUser", "/database/fetchAndSaveAuthors", "/database/getAllAuthorsFromDatabase", "/database/getOneAuthorFromDatabase/**", "/database/getAllBooksFromDatabase", "/database/getOneBookFromDatabase/**", "/database/booksByAuthor/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/user/getWantToRead", "/user/getCurrentlyReading", "/user/getFavoriteBooks").authenticated()

                    .requestMatchers(HttpMethod.POST, "/user/login", "/user/register", "/database/addBooksToDatabase", "/database/addAuthor/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/user/addBookToWantToRead/**", "/user/addBookToCurrentlyReading/**", "/user/addBookToFavorites/**").authenticated()

                    .requestMatchers(HttpMethod.PUT, "/database/updateBookCovers").permitAll()

                    .requestMatchers(HttpMethod.DELETE, "/user/removeBookFromWantToRead/**", "/user/removeBookFromCurrentlyReading/**", "/user/removeBookFromFavorites/**").authenticated()


                    .anyRequest()
                    .authenticated())
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
