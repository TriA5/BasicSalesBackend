package ontap.example.ontap.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cấu hình phân quyền cho endpoint
        http.authorizeHttpRequests(
            config -> config
                .requestMatchers(HttpMethod.GET, Endpoints.PUBLIC_GET).permitAll()
                .requestMatchers(HttpMethod.POST, Endpoints.PUBLIC_POST).permitAll()
                .requestMatchers(HttpMethod.PUT, Endpoints.PUBLIC_PUT).permitAll()
                .requestMatchers(HttpMethod.DELETE, Endpoints.PUBLIC_DELETE).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
        );
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
