package ontap.example.ontap.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ontap.example.ontap.JWT.JwtService;
import ontap.example.ontap.security.JwtResponse;
import ontap.example.ontap.userdetail.UserSecurityService;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserSecurityService userSecurityService;

    @PostMapping("/extractExpiration")
    public String extractExpiration(@RequestBody JwtResponse jwtResponse) {
        return jwtService.extractExpiration(jwtResponse.getJwtToken()).toString();
    }

    @PostMapping("/validateToken")
    public boolean validateToken(@RequestBody JwtResponse jwtResponse) {
        String username = jwtService.extractUsername(jwtResponse.getJwtToken());
        UserDetails userDetails = userSecurityService.loadUserByUsername(username);
        return jwtService.validateToken(jwtResponse.getJwtToken(), userDetails);
    }

    @PostMapping("/isTokenExpired")
    public boolean isTokenExpired(@RequestBody JwtResponse jwtResponse) {
        return jwtService.isTokenExpired(jwtResponse.getJwtToken());
    }

    @PostMapping("/extractAllClaims")
    public Map<String, Object> extractAllClaims(@RequestBody JwtResponse jwtResponse) {
        return jwtService.extractAllClaimsAsMap(jwtResponse.getJwtToken());
    }
}