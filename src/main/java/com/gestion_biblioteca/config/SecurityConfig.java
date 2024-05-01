package com.gestion_biblioteca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gestion_biblioteca.jwt.Jwt;
import com.gestion_biblioteca.jwt.JwtTokenValidator;
import com.gestion_biblioteca.services.impl.JwtUserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private Jwt jwt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        .csrf(csrf -> csrf.disable())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(http ->{
            //endpoints publicos
            http.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
            //endpoints publicos
            http.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();
            //endpoints privados de libros
            http.requestMatchers(HttpMethod.GET,"/book/get","/book/get/**","/book/available").hasAnyRole("ADMIN","LIBRARIAN","USER");
            http.requestMatchers(HttpMethod.POST,"/book/create").hasAnyRole("ADMIN","LIBRARIAN");
            http.requestMatchers(HttpMethod.PUT,"/book/update/**").hasAnyRole("ADMIN","LIBRARIAN");
            http.requestMatchers(HttpMethod.DELETE,"/book/delete/**").hasAnyRole("ADMIN","LIBRARIAN");
            //endpoints privados de usuarios
            http.requestMatchers(HttpMethod.GET,"/user/get","/user/get/**").hasRole("ADMIN");
            http.requestMatchers(HttpMethod.DELETE,"/user/delete/**").hasAnyRole("ADMIN");
            //endpoints privados de prestamos
            http.requestMatchers(HttpMethod.GET,"/loan/approval/**","/loan/state").hasAnyRole("ADMIN","LIBRARIAN");
            http.requestMatchers(HttpMethod.POST,"/loan/request").hasAnyRole("ADMIN","LIBRARIAN","USER");
            http.requestMatchers(HttpMethod.PUT,"/loan/return/**").hasAnyRole("ADMIN","LIBRARIAN");
            http.requestMatchers(HttpMethod.DELETE,"/loan/cancellation/**").hasAnyRole("ADMIN","LIBRARIAN");
        
            http.anyRequest().denyAll();
        })
        .addFilterBefore(new JwtTokenValidator(jwt),BasicAuthenticationFilter.class)
        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(JwtUserDetailServiceImpl userDetailServiceImpl){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailServiceImpl);
        return provider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
